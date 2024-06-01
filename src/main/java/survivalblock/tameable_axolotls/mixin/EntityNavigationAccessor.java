package survivalblock.tameable_axolotls.mixin;

import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EntityNavigation.class)
public interface EntityNavigationAccessor {

    @Accessor("world")
    World tameable_axolotls$getWorld();

    @Accessor("entity")
    MobEntity tameable_axolotls$getEntity();
}
