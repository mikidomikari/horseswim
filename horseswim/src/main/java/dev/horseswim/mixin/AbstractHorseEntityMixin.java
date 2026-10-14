package dev.horseswim.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CamelEntity;
import net.minecraft.entity.passive.LlamaEntity;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractHorseEntity.class)
public abstract class AbstractHorseEntityMixin extends AnimalEntity {
    protected AbstractHorseEntityMixin(EntityType<? extends AnimalEntity> type, World world) {
        super(type, world);
    }

    @Inject(method = "tickMovement", at = @At("HEAD"))
    private void horseswim$floatUp(CallbackInfo ci) {
        // llamas and camels already handle water their own way, leave them be
        if (!this.isTouchingWater() || (Object) this instanceof LlamaEntity || (Object) this instanceof CamelEntity) return;
        // same nudge swimUpward does, just without needing a jump input.
        // runs on both sides on purpose so the riding client doesn't desync
        if (this.getFluidHeight(FluidTags.WATER) > this.getHeight() * 0.4) {
            this.setVelocity(this.getVelocity().add(0.0, 0.03, 0.0));
        }
    }
}
