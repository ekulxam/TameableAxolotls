package survivalblock.tameable_axolotls.pathfinder;

import net.minecraft.entity.ai.pathing.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import survivalblock.tameable_axolotls.mixin.AmphibiousSwimNavigationAccessor;
import survivalblock.tameable_axolotls.mixin.EntityNavigationAccessor;

public class AmphibiousSwimNavigationWrapper extends MobNavigation {

    public AmphibiousSwimNavigationWrapper(AmphibiousSwimNavigation delegate) {
        super(((EntityNavigationAccessor) delegate).tameable_axolotls$getEntity(), ((EntityNavigationAccessor) delegate).tameable_axolotls$getWorld());
        this.delegate = delegate;
    }

    AmphibiousSwimNavigation delegate;

    @Override
    protected PathNodeNavigator createPathNodeNavigator(int range) {
        this.nodeMaker = new AmphibiousPathNodeMaker(false);
        this.nodeMaker.setCanEnterOpenDoors(true);
        return new PathNodeNavigator(this.nodeMaker, range);
    }

    @Override
    protected boolean isAtValidPosition() {
        return ((AmphibiousSwimNavigationAccessor) delegate).tameable_axolotls$isAtValidPosition();
    }

    @Override
    protected Vec3d getPos() {
        return ((AmphibiousSwimNavigationAccessor) delegate).tameable_axolotls$getPos();
    }

    @Override
    protected double adjustTargetY(Vec3d pos) {
        return ((AmphibiousSwimNavigationAccessor) delegate).tameable_axolotls$adjustTargetY(pos);
    }

    @Override
    protected boolean canPathDirectlyThrough(Vec3d origin, Vec3d target) {
        return ((AmphibiousSwimNavigationAccessor) delegate).tameable_axolotls$canPathDirectlyThrough(origin, target);
    }

    @Override
    public boolean isValidPosition(BlockPos pos) {
        return delegate.isValidPosition(pos);
    }

    @Override
    public void setCanSwim(boolean canSwim) {
        delegate.setCanSwim(canSwim);
    }
}
