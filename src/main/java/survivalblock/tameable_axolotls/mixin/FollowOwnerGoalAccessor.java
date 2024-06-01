package survivalblock.tameable_axolotls.mixin;

import net.minecraft.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(FollowOwnerGoal.class)
public interface FollowOwnerGoalAccessor {

    @Accessor("tameable")
    TameableEntity tameable_axolotls$getTameable();

    @Accessor("world")
    WorldView tameable_axolotls$getWorld();

    @Accessor("leavesAllowed")
    boolean tameable_axolotls$getLeavesAllowed();
}
