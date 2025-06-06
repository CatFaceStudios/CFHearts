package com.catface.cfhearts.network.packets;

import com.catface.cfhearts.core.hearts.EnumHearts;
import com.catface.cfhearts.core.heartbar.HeartBar;
import com.catface.cfhearts.core.heartbar.IHeartBar;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.ArrayList;
import java.util.List;

public class PacketSyncHeartBar implements IMessage {

    private int maxHealth;
    private List<EnumHearts> customHearts;
    private boolean worm;
    private int animLength;
    private int animTick;

    public PacketSyncHeartBar() {
    }

    public PacketSyncHeartBar(IHeartBar heartBar) {
        this.maxHealth = heartBar.getMaxHealth();
        this.customHearts = new ArrayList<>(heartBar.getCustomHeartList());
        this.worm = heartBar.hasWorm();
        this.animLength = heartBar.getAnimationLength();
        this.animTick = heartBar.getAnimationTick();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(maxHealth);

        buf.writeInt(customHearts.size());
        for (EnumHearts heart : customHearts) {
            buf.writeInt(heart.ordinal());
        }

        buf.writeBoolean(worm);
        buf.writeInt(animLength);
        buf.writeInt(animTick);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        maxHealth = buf.readInt();

        int count = buf.readInt();
        customHearts = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int ordinal = buf.readInt();
            if (ordinal >= 0 && ordinal < EnumHearts.values().length) {
                customHearts.add(EnumHearts.values()[ordinal]);
            }
        }

        worm = buf.readBoolean();
        animLength = buf.readInt();
        animTick = buf.readInt();
    }

    public static class HandlerClient implements IMessageHandler<PacketSyncHeartBar, IMessage> {
        @Override
        public IMessage onMessage(PacketSyncHeartBar message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                EntityPlayer player = Minecraft.getMinecraft().player;
                IHeartBar heartBar = player.getCapability(HeartBar.HEART_BAR_CAPABILITY, null);
                if (heartBar != null) {
                    heartBar.setMaxHealth(message.maxHealth);
                    player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(message.maxHealth);
                    if(player.getHealth()>message.maxHealth){
                        player.setHealth(message.maxHealth);
                    }
                    heartBar.setCustomHeartList(message.customHearts);
                    heartBar.setWorm(message.worm);
                    heartBar.setAnimationLength(message.animLength);
                    heartBar.setAnimationTick(message.animTick);
                }
            });
            return null;
        }
    }

    public static class HandlerServer implements IMessageHandler<PacketSyncHeartBar, IMessage> {
        @Override
        public IMessage onMessage(PacketSyncHeartBar message, MessageContext ctx) {
            ctx.getServerHandler().player.getServerWorld().addScheduledTask(() -> {
                EntityPlayer player = ctx.getServerHandler().player;
                IHeartBar heartBar = player.getCapability(HeartBar.HEART_BAR_CAPABILITY, null);
                if (heartBar != null) {
                    heartBar.setMaxHealth(message.maxHealth);
                    player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(message.maxHealth);
                    if(player.getHealth()>message.maxHealth){
                        player.setHealth(message.maxHealth);
                    }
                    heartBar.setCustomHeartList(message.customHearts);
                    heartBar.setWorm(message.worm);
                    heartBar.setAnimationLength(message.animLength);
                    heartBar.setAnimationTick(message.animTick);
                }
            });
            return null;
        }
    }
}
