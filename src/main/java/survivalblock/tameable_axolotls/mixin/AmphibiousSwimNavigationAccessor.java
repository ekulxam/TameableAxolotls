package survivalblock.tameable_axolotls.mixin;

import net.minecraft.entity.ai.pathing.AmphibiousSwimNavigation;
import net.minecraft.entity.ai.pathing.PathNodeNavigator;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(AmphibiousSwimNavigation.class)
public interface AmphibiousSwimNavigationAccessor {
    @Invoker("canPathDirectlyThrough")
    boolean tameableAxolotls$canPathDirectlyThrough(Vec3d origin, Vec3d target);

    @Invoker("isAtValidPosition")
    boolean tameableAxolotls$isAtValidPosition();

    @Invoker("getPos")
    Vec3d tameableAxolotls$getPos();

    @Invoker("adjustTargetY")
    double tameableAxolotls$adjustTargetY(Vec3d pos);
}
