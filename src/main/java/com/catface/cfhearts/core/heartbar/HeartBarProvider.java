package com.catface.cfhearts.core.heartbar;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class HeartBarProvider  implements ICapabilitySerializable<NBTTagCompound> {



    private final IHeartBar instance = new HeartBar();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == HeartBar.HEART_BAR_CAPABILITY;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return capability == HeartBar.HEART_BAR_CAPABILITY ? HeartBar.HEART_BAR_CAPABILITY.cast(instance) : null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return (NBTTagCompound) HeartBar.HEART_BAR_CAPABILITY.getStorage().writeNBT(HeartBar.HEART_BAR_CAPABILITY, instance, null);
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        HeartBar.HEART_BAR_CAPABILITY.getStorage().readNBT(HeartBar.HEART_BAR_CAPABILITY, instance, null, nbt);
    }
}
