package survivalblock.tameable_axolotls.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.sensor.AxolotlAttackablesSensor;
import net.minecraft.entity.passive.AxolotlEntity;
import net.minecraft.entity.passive.TameableEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = AxolotlAttackablesSensor.class, priority = 1500)
public class AxolotlAttackablesSensorMixin {

    @ModifyReturnValue(method = "canHunt", at = @At(value = "TAIL"))
    public boolean allowAttackingAll(boolean original, LivingEntity entity, LivingEntity target) {
        if (!(entity instanceof AxolotlEntity axolotl) || axolotl.getWorld().isClient()) {
            return original;
        }
        if (!(((Object) axolotl) instanceof TameableEntity tameable) || !tameable.isTamed() || tameable.getOwner() == null) {
            return original;
        }
        if(tameable.getOwner().getAttacking() != null){
            return tameable.getOwner().getAttacking().equals(target);
        } else if(tameable.getOwner().getAttacker() != null){
            return tameable.getOwner().getAttacker().equals(target);
        } else {
            return original;
        }
    }
}