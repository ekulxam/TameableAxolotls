package survivalblock.tameable_axolotls.pathfinder;

import net.minecraft.entity.ai.pathing.*;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import survivalblock.tameable_axolotls.mixin.AmphibiousSwimNavigationAccessor;
import survivalblock.tameable_axolotls.mixin.EntityNavigationAccessor;

public class AmphibiousSwimNavigationWrapper extends MobNavigation {


    public AmphibiousSwimNavigationWrapper(AmphibiousSwimNavigation delegate) {
        super(((EntityNavigationAccessor) delegate).getEntity(), ((EntityNavigationAccessor) delegate).getWorld());
        this.delegate = delegate;
    }

    AmphibiousSwimNavigation delegate;

    @Override
    protected PathNodeNavigator createPathNodeNavigator(int range) {
        return ((AmphibiousSwimNavigationAccessor) delegate).tameableAxolotls$createPathNodeNavigator(range);
    }

    @Override
    protected boolean isAtValidPosition() {
        return ((AmphibiousSwimNavigationAccessor) delegate).tameableAxolotls$isAtValidPosition();
    }

    @Override
    protected Vec3d getPos() {
        return ((AmphibiousSwimNavigationAccessor) delegate).tameableAxolotls$getPos();
    }

    @Override
    protected double adjustTargetY(Vec3d pos) {
        return ((AmphibiousSwimNavigationAccessor) delegate).tameableAxolotls$adjustTargetY(pos);
    }

    @Override
    protected boolean canPathDirectlyThrough(Vec3d origin, Vec3d target) {
        return ((AmphibiousSwimNavigationAccessor) delegate).tameableAxolotls$canPathDirectlyThrough(origin, target);
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
