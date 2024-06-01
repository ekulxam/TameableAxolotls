package survivalblock.tameable_axolotls.pathfinder;

import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.entity.ai.pathing.LandPathNodeMaker;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.util.math.BlockPos;
import survivalblock.tameable_axolotls.mixin.FollowOwnerGoalAccessor;

public class AmphibiousFollowOwnerGoal extends FollowOwnerGoal {
    public AmphibiousFollowOwnerGoal(TameableEntity tameable, double speed, float minDistance, float maxDistance, boolean leavesAllowed) {
        super(tameable, speed, minDistance, maxDistance, leavesAllowed);
    }

    @Override
    protected boolean canTeleportTo(BlockPos pos) {
        PathNodeType pathNodeType = LandPathNodeMaker.getLandNodeType(((FollowOwnerGoalAccessor) this).tameable_axolotls$getWorld(), pos.mutableCopy());
        if (pathNodeType != PathNodeType.WATER) {
            return false;
        }
        BlockState blockState = ((FollowOwnerGoalAccessor) this).tameable_axolotls$getWorld().getBlockState(pos.down());
        if (!((FollowOwnerGoalAccessor) this).tameable_axolotls$getLeavesAllowed() && blockState.getBlock() instanceof LeavesBlock) {
            return false;
        }
        BlockPos blockPos = pos.subtract(((FollowOwnerGoalAccessor) this).tameable_axolotls$getTameable().getBlockPos());
        return ((FollowOwnerGoalAccessor) this).tameable_axolotls$getWorld().isSpaceEmpty(((FollowOwnerGoalAccessor) this).tameable_axolotls$getTameable(), ((FollowOwnerGoalAccessor) this).tameable_axolotls$getTameable().getBoundingBox().offset(blockPos));
    }
}
