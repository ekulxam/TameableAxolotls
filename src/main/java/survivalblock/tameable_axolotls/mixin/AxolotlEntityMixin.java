package survivalblock.tameable_axolotls.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.ai.goal.AttackWithOwnerGoal;
import net.minecraft.entity.ai.goal.TrackOwnerAttackerGoal;
import net.minecraft.entity.ai.pathing.AmphibiousSwimNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import survivalblock.tameable_axolotls.MappingUtil;
import survivalblock.tameable_axolotls.ReflectionUtil;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.AxolotlEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import survivalblock.tameable_axolotls.pathfinder.AmphibiousFollowOwnerGoal;
import survivalblock.tameable_axolotls.pathfinder.AmphibiousSwimNavigationWrapper;

@Debug(export = true)
@Mixin(value = AxolotlEntity.class, priority = 1500)
public abstract class AxolotlEntityMixin extends AnimalEntity {
    @Shadow protected abstract void eat(PlayerEntity player, Hand hand, ItemStack stack);

    protected AxolotlEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @WrapOperation(method = "interactMob", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/AnimalEntity;interactMob(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResult;"))
    public ActionResult interactMob(AxolotlEntity instance, PlayerEntity player, Hand hand, Operation<ActionResult> original) {
        ItemStack itemStack = player.getStackInHand(hand);
        Item item = itemStack.getItem();

        if (this.getWorld().isClient()) {
            boolean consume = tameable$isOwner(player) || tameable$isTamed() || (itemStack.isIn(ItemTags.AXOLOTL_TEMPT_ITEMS) && !tameable$isTamed());
            return consume ? ActionResult.CONSUME : ActionResult.PASS;
        }

        if (itemStack.isIn(ItemTags.AXOLOTL_TEMPT_ITEMS)) {
            eat(player, hand, itemStack);

            if (!tameable$isTamed()) {
                if (this.random.nextInt(3) == 0) {
                    tameable$setOwner(player);
                    this.navigation.stop();
                    this.setTarget(null);
                    this.getWorld().sendEntityStatus(this, EntityStatuses.ADD_POSITIVE_PLAYER_REACTION_PARTICLES);
                } else {
                    this.getWorld().sendEntityStatus(this, EntityStatuses.ADD_NEGATIVE_PLAYER_REACTION_PARTICLES);
                }
                return ActionResult.SUCCESS;
            } else if(this.isBreedingItem(itemStack) && this.getHealth() < this.getMaxHealth()) {
                this.heal(item.getFoodComponent().getHunger());
                return ActionResult.SUCCESS;
            }

            return original.call(instance, player, hand);
        }

        return original.call(instance, player, hand);
    }

    @Unique
    private boolean tameable$isOwner(PlayerEntity player) {
        return tameable$invoke(
                "method_6171",
                "(%s)Z".formatted(MappingUtil.definitionToIntermediaryBytecodeName(LivingEntity.class)),
                boolean.class,
                new Class[]{LivingEntity.class},
                player
        );
    }

    @Unique
    private boolean tameable$isTamed() {
        return tameable$invoke("method_6181", "()Z", boolean.class);
    }

    @Unique
    private void tameable$setOwner(PlayerEntity player) {
        tameable$invoke(
                "method_6170",
                "(%s)V".formatted(MappingUtil.definitionToIntermediaryBytecodeName(PlayerEntity.class)),
                new Class[]{PlayerEntity.class},
                player
        );
    }

    @Unique
    private <T> T tameable$invoke(String methodName, String desc, Class<T> returnType, Class<?>[] parameterTypes, Object... args) {
        return ReflectionUtil.invoke(this, "net.minecraft.class_1321", methodName, desc, returnType, parameterTypes, args);
    }

    @Unique
    private <T> T tameable$invoke(String methodName, String desc, Class<T> returnType) {
        return tameable$invoke(methodName, desc, returnType, new Class[0]);
    }

    @Unique
    private void tameable$invoke(String methodName, String desc, Class<?>[] parameterTypes, Object... args) {
        tameable$invoke(methodName, desc, void.class, parameterTypes, args);
    }

    @Override
    @SuppressWarnings({"cast", "DataFlowIssue"})
    protected void initGoals() {
        this.goalSelector.add(1, new AmphibiousFollowOwnerGoal(((TameableEntity)(Object) this), 0.75, 15.0f, 2.5f, false));
        this.targetSelector.add(1, new TrackOwnerAttackerGoal((TameableEntity)(Object) this));
        this.targetSelector.add(2, new AttackWithOwnerGoal((TameableEntity)(Object) this));
    }

    @ModifyReturnValue(method = "createNavigation", at = @At("RETURN"))
    private EntityNavigation wrapNavigation(EntityNavigation original) {
        return new AmphibiousSwimNavigationWrapper((AmphibiousSwimNavigation) original);
    }

    @Inject(method = "createChild", at = @At("TAIL"))
    private void addOwnerTagToChild(ServerWorld world, PassiveEntity entity, CallbackInfoReturnable<PassiveEntity> cir) {
        PassiveEntity child = cir.getReturnValue();
        if(entity instanceof AxolotlEntity axolotl && !axolotl.getWorld().isClient() && child instanceof AxolotlEntity axolotlChild){
            if(((Object) axolotl) instanceof TameableEntity tameable && tameable.isTamed() && tameable.getOwner() != null){
                if(((Object) axolotlChild) instanceof TameableEntity tameable1){
                    tameable1.setOwnerUuid(tameable.getOwnerUuid());
                }
            }
        }
    }

    @SuppressWarnings("DataFlowIssue")
    @Inject(method = "copyDataToStack", at = @At(value = "TAIL"))
    public void addOwnerToBucketNbt(ItemStack stack, CallbackInfo ci) {
        if(((TameableEntity) (Object) this).getOwnerUuid() != null) stack.getOrCreateNbt().putUuid("Owner", ((TameableEntity) (Object) this).getOwnerUuid());
    }

    @SuppressWarnings("DataFlowIssue")
    @Inject(method = "copyDataFromNbt", at = @At(value = "TAIL"))
    public void addOwnerToBucketDeploy(NbtCompound nbt, CallbackInfo ci) {
        if(nbt.containsUuid("Owner")) {
            ((TameableEntity) (Object) this).setOwnerUuid(nbt.getUuid("Owner"));
            ((TameableEntity) (Object) this).setTamed(true);
        } else {
            ((TameableEntity) (Object) this).setTamed(false);
        }
    }
}
