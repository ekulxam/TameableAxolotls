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
        PathNodeType pathNodeType = LandPathNodeMaker.getLandNodeType(((FollowOwnerGoalAccessor) this).getTameable(), pos.mutableCopy());
        if (pathNodeType != PathNodeType.WATER) {
            return false;
        }
        BlockState blockState = ((FollowOwnerGoalAccessor) this).getWorld().getBlockState(pos.down());
        if (!((FollowOwnerGoalAccessor) this).getLeavesAllowed() && blockState.getBlock() instanceof LeavesBlock) {
            return false;
        }
        BlockPos blockPos = pos.subtract(((FollowOwnerGoalAccessor) this).getTameable().getBlockPos());
        return ((FollowOwnerGoalAccessor) this).getWorld().isSpaceEmpty(((FollowOwnerGoalAccessor) this).getTameable(), ((FollowOwnerGoalAccessor) this).getTameable().getBoundingBox().offset(blockPos));
    }
}
