package survivalblock.tameable_axolotls.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(FollowOwnerGoal.class)
public interface FollowOwnerGoalAccessor {

    @Accessor
    TameableEntity getTameable();

    @Accessor
    WorldView getWorld();

    @Accessor
    boolean getLeavesAllowed();
}
