package com.bawnorton.tameable.mixin;

import com.bawnorton.tameable.MappingUtil;
import com.bawnorton.tameable.ReflectionUtil;
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

@Mixin(AxolotlEntity.class)
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
}
