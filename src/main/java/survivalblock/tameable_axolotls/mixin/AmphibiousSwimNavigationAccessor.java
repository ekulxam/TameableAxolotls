package survivalblock.tameable_axolotls.mixin;

import net.minecraft.entity.ai.pathing.AmphibiousSwimNavigation;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(AmphibiousSwimNavigation.class)
public interface AmphibiousSwimNavigationAccessor {
    @Invoker("canPathDirectlyThrough")
    boolean tameable_axolotls$canPathDirectlyThrough(Vec3d origin, Vec3d target);

    @Invoker("isAtValidPosition")
    boolean tameable_axolotls$isAtValidPosition();

    @Invoker("getPos")
    Vec3d tameable_axolotls$getPos();

    @Invoker("adjustTargetY")
    double tameable_axolotls$adjustTargetY(Vec3d pos);
}
