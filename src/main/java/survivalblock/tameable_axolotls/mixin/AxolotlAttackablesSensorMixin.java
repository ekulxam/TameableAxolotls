package survivalblock.tameable_axolotls.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.sensor.AxolotlAttackablesSensor;
import net.minecraft.entity.passive.AxolotlEntity;
import net.minecraft.entity.passive.TameableEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AxolotlAttackablesSensor.class)
public class AxolotlAttackablesSensorMixin {

    @Inject(method = "canHunt", at = @At(value = "RETURN"), cancellable = true)
    public void allowAttackingAll(LivingEntity entity, LivingEntity target, CallbackInfoReturnable<Boolean> cir) {
        if(entity instanceof AxolotlEntity axolotl && !axolotl.getWorld().isClient()){
            if(((Object) axolotl) instanceof TameableEntity tameable && tameable.isTamed() && tameable.getOwner() != null){
                if(tameable.getOwner().getAttacking() != null){
                    cir.setReturnValue(tameable.getOwner().getAttacking() == target);
                } else if(tameable.getOwner().getAttacker() != null){
                    cir.setReturnValue(tameable.getOwner().getAttacker() == target);
                }
            }
        }
    }

}
