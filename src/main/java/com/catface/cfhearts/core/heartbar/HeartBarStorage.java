package com.catface.cfhearts.core.heartbar;

import com.catface.cfhearts.core.hearts.EnumHearts;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.capabilities.Capability;

import java.util.List;

public class HeartBarStorage implements Capability.IStorage<IHeartBar> {

    @Override
    public NBTTagCompound writeNBT(Capability<IHeartBar> capability, IHeartBar instance, net.minecraft.util.EnumFacing side) {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("MaxHealth", instance.getMaxHealth());

        NBTTagList heartList = new NBTTagList();
        for (EnumHearts heart : instance.getCustomHeartList()) {
            heartList.appendTag(new NBTTagCompound() {{
                setString("Heart", heart.name());
            }});
        }

        tag.setTag("CustomHearts", heartList);

        tag.setBoolean("Worm", instance.hasWorm());
        tag.setInteger("AnimLength", instance.getAnimationLength());
        tag.setInteger("AnimTick", instance.getAnimationTick());
        return tag;
    }

    @Override
    public void readNBT(Capability<IHeartBar> capability, IHeartBar instance, EnumFacing side, NBTBase nbt) {
        if(nbt instanceof NBTTagCompound){
            NBTTagCompound compound = (NBTTagCompound) nbt;
            instance.setMaxHealth(compound.getInteger("MaxHealth"));

            List<EnumHearts> heartList = instance.getCustomHeartList();
            heartList.clear();

            NBTTagList hearts = compound.getTagList("CustomHearts", Constants.NBT.TAG_COMPOUND);
            for (int i = 0; i < hearts.tagCount(); i++) {
                NBTTagCompound heartTag = hearts.getCompoundTagAt(i);
                String name = heartTag.getString("Heart");
                EnumHearts heart = EnumHearts.findHeart(name);
                if(heart != null) {
                    heartList.add(heart);
                }
            }

            instance.setWorm(compound.getBoolean("Worm"));
            instance.setAnimationLength(compound.getInteger("AnimLength"));
            instance.setAnimationTick(compound.getInteger("AnimTick"));
        }

    }

}
