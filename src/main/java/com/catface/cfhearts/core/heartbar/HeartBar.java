package com.catface.cfhearts.core.heartbar;

import com.catface.cfhearts.core.hearts.EnumHearts;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

import java.util.ArrayList;
import java.util.List;

public class HeartBar implements IHeartBar {

    private int maxHealth = 20;
    private List<EnumHearts> customHearts = new ArrayList<>();
    private boolean worm;
    private int animLength;
    private int animTick;

    @CapabilityInject(IHeartBar.class)
    public static final Capability<IHeartBar> HEART_BAR_CAPABILITY = null;

    @Override
    public void setMaxHealth(int health) {
        this.maxHealth = health;
    }

    @Override
    public int getMaxHealth() {
        return this.maxHealth;
    }

    @Override
    public void addCustomHeart(EnumHearts heart) {
        this.customHearts.add(heart);
    }

    @Override
    public List<EnumHearts> getCustomHeartList() {
        return this.customHearts;
    }

    @Override
    public void setCustomHeartList(List<EnumHearts> heartList) {
        this.customHearts = heartList;
    }

    @Override
    public boolean hasCustomHearts() {
        return !this.customHearts.isEmpty();
    }

    @Override
    public void setWorm(boolean worm) {
        if(this.worm != worm){
            animTick = 0;
        }
        this.worm = worm;
    }

    @Override
    public boolean hasWorm() {
        return this.worm;
    }

    @Override
    public int getAnimationLength() {
        return animLength;
    }

    @Override
    public void setAnimationLength(int tick) {
        animLength = tick;
    }

    @Override
    public int getAnimationTick() {
        return animTick;
    }

    @Override
    public void setAnimationTick(int ticks) {
        this.animTick = 0;
    }

    @Override
    public void tickAnimation() {
        animTick++;
        if(animTick >= animLength){
            wormMunch();
            animTick = 0;
        }
    }

    @Override
    public void wormMunch() {
        this.setMaxHealth(this.getMaxHealth()-1);
    }

    public static IHeartBar getHeartBar(EntityPlayer player) {
        if(player.hasCapability(HeartBar.HEART_BAR_CAPABILITY,null)){
            return player.getCapability(HeartBar.HEART_BAR_CAPABILITY, null);
        } else {
            return null;
        }

    }
}
