package com.catface.cfhearts.command;

import com.catface.cfhearts.CFHearts;
import com.catface.cfhearts.core.hearts.EnumHearts;
import com.catface.cfhearts.core.heartbar.HeartBar;
import com.catface.cfhearts.core.heartbar.IHeartBar;
import com.catface.cfhearts.network.packets.PacketSyncHeartBar;
import net.minecraft.command.*;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;
import java.util.*;


public class CommandHeartBar extends CommandBase {

    @Override
    public String getName() {
        return "heartbar";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/heartbar <targets> <get|set|add|remove> [key value pairs...]";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException{
        if (args.length < 2)
            throw new WrongUsageException(getUsage(sender));

        Collection<EntityPlayerMP> targets = getPlayers(server, sender, args[0]);
        String subCommand = args[1].toLowerCase(Locale.ROOT);

        switch (subCommand) {
            case "get":
                for (EntityPlayerMP player : targets) {
                    IHeartBar bar = player.getCapability(HeartBar.HEART_BAR_CAPABILITY, null);
                    if (bar != null) {
                        String heartStr = bar.getCustomHeartList().toString();
                        sender.sendMessage(new TextComponentString(String.format(
                                "[%s] MaxHealth: %d, Worm: %s, AnimTick: %d, AnimLength: %d, Hearts: %s",
                                player.getName(), bar.getMaxHealth(), bar.hasWorm(), bar.getAnimationTick(),
                                bar.getAnimationLength(), heartStr
                        )));
                    }
                }
                break;

            case "set":
                if (args.length < 4)
                    throw new WrongUsageException("/heartbar <target> set <key> <value> [...]");

                for (EntityPlayerMP player : targets) {
                    IHeartBar heartBar = player.getCapability(HeartBar.HEART_BAR_CAPABILITY, null);
                    if (heartBar == null) continue;

                    for (int i = 2; i < args.length - 1; i += 2) {
                        String key = args[i].toLowerCase();
                        String value = args[i + 1];

                        switch (key) {
                            case "health":
                            case "maxhealth":
                                heartBar.setMaxHealth(parseInt(value));
                                player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(parseInt(value));
                                if(player.getHealth()>heartBar.getMaxHealth()){
                                    player.setHealth(heartBar.getMaxHealth());
                                }
                                break;
                            case "worm":
                                heartBar.setWorm(parseBoolean(value));
                                break;
                            case "animtick":
                                heartBar.setAnimationTick(parseInt(value));
                                break;
                            case "animlength":
                                heartBar.setAnimationLength(parseInt(value));
                                break;
                            default:
                                throw new WrongUsageException("Unknown key: " + key);
                        }
                    }

                    CFHearts.packetHandler.network.sendTo(new PacketSyncHeartBar(heartBar), player);
                    notifyCommandListener(sender, this, "HeartBar updated for %s", player.getName());
                }
                break;

            case "add":
            case "remove":
                if (args.length < 3)
                    throw new WrongUsageException("/heartbar <target> " + subCommand + " <heart...>");

                List<String> heartNames = Arrays.asList(Arrays.copyOfRange(args, 2, args.length));

                for (EntityPlayerMP player : targets) {
                    IHeartBar heartBar = player.getCapability(HeartBar.HEART_BAR_CAPABILITY, null);
                    if (heartBar == null) continue;

                    List<EnumHearts> currentHearts = heartBar.getCustomHeartList();
                    for (String name : heartNames) {
                        try {
                            EnumHearts heart = EnumHearts.findHeart(name.toUpperCase(Locale.ROOT));
                            if(heart == null) {
                                throw new CommandException("Invalid heart type: " + name);
                            }

                            if (subCommand.equals("add") && !currentHearts.contains(heart)) {
                                currentHearts.add(heart);
                            } else if (subCommand.equals("remove")) {
                                currentHearts.remove(heart);
                            }

                        } catch (IllegalArgumentException e) {
                            throw new CommandException("Invalid heart type: " + name);
                        }
                    }

                    heartBar.setCustomHeartList(currentHearts);
                    CFHearts.packetHandler.network.sendTo(new PacketSyncHeartBar(heartBar), player);
                    notifyCommandListener(sender, this, "Heart list updated for %s", player.getName());
                }
                break;

            default:
                throw new WrongUsageException("Unknown subcommand: " + subCommand);
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
        }
        if (args.length == 2) {
            return getListOfStringsMatchingLastWord(args, Arrays.asList("get", "set", "add", "remove"));
        }
        if (args.length >= 3 && args[1].equalsIgnoreCase("set")) {
            return getListOfStringsMatchingLastWord(args, Arrays.asList("health", "worm", "animtick", "animlength"));
        }
        if (args.length >= 3 && (args[1].equalsIgnoreCase("add") || args[1].equalsIgnoreCase("remove"))) {
            List<String> heartTypes = new ArrayList<>();
            for (EnumHearts heart : EnumHearts.values()) {
                heartTypes.add(heart.name());
            }
            return getListOfStringsMatchingLastWord(args, heartTypes);
        }
        return Collections.emptyList();
    }
}