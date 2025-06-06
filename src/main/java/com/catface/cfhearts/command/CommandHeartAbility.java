package com.catface.cfhearts.command;

import com.catface.cfhearts.core.heartbar.HeartBar;
import com.catface.cfhearts.core.heartbar.IHeartBar;
import com.catface.cfhearts.core.hearts.EnumHearts;
import com.catface.cfhearts.core.hearts.entity.HeartBase;
import net.minecraft.command.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import java.util.Collections;
import java.util.List;

public class CommandHeartAbility extends CommandBase {
    @Override
    public String getName() {
        return "heart";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/heart <heart> [force]";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length < 1) {
            throw new WrongUsageException(getUsage(sender));
        }

        String heartId = args[0].toUpperCase();
        boolean force = args.length > 1 && args[1].equalsIgnoreCase("force");

        EntityPlayer player = getCommandSenderAsPlayer(sender);
        IHeartBar heartBar = player.getCapability(HeartBar.HEART_BAR_CAPABILITY, null);
        if (heartBar == null) {
            sender.sendMessage(new TextComponentString("HeartBar capability not found."));
            return;
        }

        EnumHearts heart = null;
        try {
            heart = EnumHearts.valueOf(heartId);
        } catch (IllegalArgumentException e) {
            throw new CommandException("Unknown heart: " + heartId);
        }

        if (!force && !heartBar.getCustomHeartList().contains(heart)) {
            sender.sendMessage(new TextComponentString("You don't have this heart."));
            return;
        }

        HeartBase.spawnHeart(player.world,heart,player);
        sender.sendMessage(new TextComponentString("Summoned "+heart));

    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0; // Allow all players to use
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender,
                                          String[] args, BlockPos targetPos) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, EnumHearts.names());
        } else if (args.length == 2) {
            return getListOfStringsMatchingLastWord(args, "force");
        }
        return Collections.emptyList();
    }
}
