package com.catface.cfhearts.core.heartbar;

import com.catface.cfhearts.core.hearts.EnumHearts;
import java.util.List;

public interface IHeartBar {

    public void setMaxHealth(int health);
    public int getMaxHealth();

    public void addCustomHeart(EnumHearts heart);
    public List<EnumHearts> getCustomHeartList();
    public void setCustomHeartList(List<EnumHearts> hearList);
    public boolean hasCustomHearts();

    public void setWorm(boolean worm);
    public boolean hasWorm();

    int getAnimationLength();
    void setAnimationLength(int ticks);

    int getAnimationTick();
    void setAnimationTick(int ticks);

    public void tickAnimation();
    public void wormMunch();
}
