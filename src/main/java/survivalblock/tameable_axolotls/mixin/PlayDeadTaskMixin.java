package survivalblock.tameable_axolotls.mixin;

import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.entity.ai.brain.task.PlayDeadTask;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.AxolotlEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Debug(export = true)
@Mixin(value = PlayDeadTask.class, priority = 1500)
public abstract class PlayDeadTaskMixin extends MultiTickTask<AxolotlEntity> {

    public PlayDeadTaskMixin(Map<MemoryModuleType<?>, MemoryModuleState> requiredMemoryState) {
        super(requiredMemoryState);
    }

    @Inject(method="run(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/passive/AxolotlEntity;J)V", at = @At("TAIL"))
    private void addStrength(ServerWorld serverWorld, AxolotlEntity axolotlEntity, long l, CallbackInfo ci){
        axolotlEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 600, 1));
        axolotlEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 600, 0));
    }
}
