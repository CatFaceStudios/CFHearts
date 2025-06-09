package com.catface.cfhearts.core.hearts.item.bow;

import catface.roleplayassist.common.data.PresetContainer;
import catface.roleplayassist.common.data.RPPlayerData;
import catface.roleplayassist.common.enums.EnumPackets;
import catface.roleplayassist.network.server.Server;
import catface.roleplayassist.network.server.ServerPresetController;
import com.catface.cfhearts.CFHearts;
import com.catface.cfhearts.registries.ItemLoader;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class HeartBowItem extends ItemBow {

    public HeartBowItem(String name){
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        ItemLoader.ITEM_LIST.add(this);
        this.setCreativeTab(CFHearts.tabHearts);
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
        super.onPlayerStoppedUsing(stack, worldIn, entityLiving, timeLeft);
        if(entityLiving instanceof EntityPlayer && !worldIn.isRemote){
            EntityPlayer player = (EntityPlayer) entityLiving;
            RPPlayerData data = RPPlayerData.get(player);
            String key = player.getDisplayNameString()+"/default";
            PresetContainer preset = ServerPresetController.getPreset(key);
            if(preset == null){
                player.sendMessage(new TextComponentString("cannot find preset "+key));
                return;
            }
            data.readFromNBT(preset.writeToNBT());
            Server.sendAssociatedData(player, EnumPackets.SEND_PLAYER_PRESET_DATA, player.getUniqueID(),
                    preset.writeToNBT());
        }
    }



    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        if(!world.isRemote) {
            RPPlayerData data = RPPlayerData.get(player);
            String key = player.getDisplayNameString() + "/toga";
            PresetContainer preset = ServerPresetController.getPreset(key);
            if (preset == null) {
                player.sendMessage(new TextComponentString("cannot find preset "+key));
                return super.onItemUseFirst(player, world, pos, side, hitX, hitY, hitZ, hand);
            }
            data.readFromNBT(preset.writeToNBT());
            Server.sendAssociatedData(player, EnumPackets.SEND_PLAYER_PRESET_DATA, player.getUniqueID(),
                    preset.writeToNBT());
        }
        return super.onItemUseFirst(player, world, pos, side, hitX, hitY, hitZ, hand);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ActionResult<ItemStack> ret = super.onItemRightClick(worldIn, playerIn, handIn);
        if(ret.getType() == EnumActionResult.SUCCESS && !worldIn.isRemote){
            RPPlayerData data = RPPlayerData.get(playerIn);
            String key = playerIn.getDisplayNameString() + "/toga";
            PresetContainer preset = ServerPresetController.getPreset(key);
            if (preset == null) {
                playerIn.sendMessage(new TextComponentString("cannot find preset "+key));
                return ret;
            }
            data.readFromNBT(preset.writeToNBT());
            Server.sendAssociatedData(playerIn, EnumPackets.SEND_PLAYER_PRESET_DATA, playerIn.getUniqueID(),
                    preset.writeToNBT());

        }
        return ret;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        //tooltip.add("Steal Hearts Mod");
        tooltip.add("Steal Hearts on Hit!");
    }

}
