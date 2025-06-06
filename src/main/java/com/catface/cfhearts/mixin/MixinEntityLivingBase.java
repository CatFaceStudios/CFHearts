package com.catface.cfhearts.mixin;

import com.catface.cfhearts.core.heartbar.HeartBar;
import com.catface.cfhearts.core.heartbar.IHeartBar;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityLivingBase.class)
public abstract class MixinEntityLivingBase {

    @Inject(method = "getMaxHealth",at=@At(value="RETURN"),cancellable = false)
    void getMaxHealth(CallbackInfoReturnable<Float> cir){
        EntityLivingBase ent = (EntityLivingBase)(Object) this;
        if(ent.hasCapability(HeartBar.HEART_BAR_CAPABILITY,null)){
            IHeartBar bar = ent.getCapability(HeartBar.HEART_BAR_CAPABILITY,null);
            cir.setReturnValue((float) bar.getMaxHealth());
        }
    }
}
