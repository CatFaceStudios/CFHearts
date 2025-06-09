package com.catface.cfhearts.core.hearts.entity;

import catface.roleplayassist.common.data.PresetContainer;
import catface.roleplayassist.common.data.RPPlayerData;
import catface.roleplayassist.common.enums.EnumPackets;
import catface.roleplayassist.entities.EntityRPNpc;
import catface.roleplayassist.network.server.Server;
import catface.roleplayassist.network.server.ServerPresetController;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class HeartGold extends HeartArrow{
    public HeartGold(World worldIn) {
        super(worldIn);
        //this.maxTime = 5;
    }

    @Override
    public void onActivation() {

    }

    @Override
    public void onAbilityUpdate() {
        super.onAbilityUpdate();
        if(this.world instanceof WorldServer){
            ((WorldServer)this.world).spawnParticle(EnumParticleTypes.BLOCK_DUST,false,this.posX,this.posY,this.posZ,5,this.width/2,this.height/2,this.width/2,0.1, Block.getStateId(Blocks.GOLD_BLOCK.getDefaultState()));
        }
    }

    @Override
    public void onEnd() {

    }

    @Override
    public void onImpact(RayTraceResult result) {
        if(result.entityHit != null){
            Entity ent = result.entityHit;
            if(ent.world instanceof WorldServer) {
                ((WorldServer) ent.world).spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, true,ent.posX,ent.posY+ent.height/2,ent.posZ,100,0.25,0.25,0.25,0.1);
            }
            if(ent instanceof EntityPlayer){
                EntityPlayer player = (EntityPlayer) ent;
                String goldKey = player.getDisplayNameString()+"/gold";
                String invisKey = "misc/invis";
                if(!player.world.isRemote){
                    PresetContainer preset = ServerPresetController.getPreset(goldKey);
                    PresetContainer preset2 = ServerPresetController.getPreset(invisKey);
                    RPPlayerData data = RPPlayerData.get(player);
                    if (preset == null) {
                        player.sendMessage(new TextComponentString("cannot find preset "+goldKey));
                        return;
                    }

                    if (preset2 == null) {
                        player.sendMessage(new TextComponentString("cannot find preset "+invisKey));
                        return;
                    }
                    data.readFromNBT(preset2.writeToNBT());
                    Server.sendAssociatedData(player, EnumPackets.SEND_PLAYER_PRESET_DATA, player.getUniqueID(),
                            preset2.writeToNBT());

                    EntityRPNpc npc = new EntityRPNpc(this.world);
                    npc.rpNpcData.readFromNBT(preset.writeToNBT());
                    npc.setPosition(player.posX,player.posY,player.posZ);
                    npc.getDataManager().set(EntityRPNpc.SYNCED_NPC_DATA,npc.rpNpcData.writeToNBT());
                    npc.getDataManager().setDirty(EntityRPNpc.SYNCED_NPC_DATA);
                    this.world.spawnEntity(npc);
                    npc.syncNpcDataToClient();
                }
            } else {
                ent.world.setBlockState(ent.getPosition(),Blocks.GOLD_BLOCK.getDefaultState(),3);
                ent.setDead();
            }


        }
        this.setDead();
    }
}
