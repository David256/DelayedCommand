package netherlands.bjorn.delayedcommand;

import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;


import java.util.Objects;

import static org.bukkit.Bukkit.getServer;

public class CommandDcmd implements CommandExecutor {

    private final Main plugin;

    public CommandDcmd(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String subcmd, String[] args) {

        if(args.length == 0){
            return false;
        }

        String subcommand = args[0].toLowerCase();
        StringBuilder dcmd = new StringBuilder();
        int secs;
        int repeat;
        int loop;

        try {
            try {
                if (Objects.equals("once", new String(subcommand)) || Objects.equals("infinite", new String(subcommand))) {
                    secs = Integer.parseInt(args[1]);
                    loop = 2;
                    repeat = 0;
                } else if (Objects.equals("repeat", new String(subcommand))) {
                    secs = Integer.parseInt(args[2]);
                    loop = 3;
                    repeat = Integer.parseInt(args[1]);
                } else if (Objects.equals("cancel", new String(subcommand))) {
                    secs = Integer.parseInt(args[1]);
                    loop = 0;
                    repeat = 0;
                } else {
                    secs = 0;
                    loop = 0;
                    repeat = 0;
                }
                int tics = secs * 20;

                // Get the command
                if (loop > 0) {
                    for (int i = loop; i < args.length; i++) {
                        if (args[i].startsWith("/")) {
                            args[i] = args[i].substring(1);
                        }
                        dcmd.append(args[i] + ' ');
                    }
                }

                if (Objects.equals("once", new String(subcommand))) {
                    BukkitTask task = new TaskSingle(this.plugin, dcmd.toString()).runTaskLater(this.plugin, tics);
                    int taskId = task.getTaskId();

                    if (sender instanceof Player) {
                        Player player = (Player) sender;

                        player.sendMessage("Cancel this command with /dcmd cancel " + taskId);
                    }
                    return true;
                } else if (Objects.equals("repeat", new String(subcommand))) {
                    BukkitTask task = new TaskRepeat(this.plugin, repeat, dcmd.toString()).runTaskTimer(this.plugin, tics, tics);
                    int taskId = task.getTaskId();

                    if (sender instanceof Player) {
                        Player player = (Player) sender;

                        player.sendMessage("Cancel this command with /dcmd cancel " + taskId);
                    }
                    return true;
                } else if (Objects.equals("infinite", new String(subcommand))) {
                    BukkitTask task = new TaskInfinite(this.plugin, dcmd.toString()).runTaskTimer(this.plugin, tics, tics);
                    int taskId = task.getTaskId();

                    if (sender instanceof Player) {
                        Player player = (Player) sender;

                        player.sendMessage("Cancel this command with /dcmd cancel " + taskId);
                    }
                    return true;
                } else if (Objects.equals("cancel", new String(subcommand))) {
                    plugin.getServer().getScheduler().cancelTask(secs);

                    if (sender instanceof Player) {
                        Player player = (Player) sender;
                        player.sendMessage("Command with id " + secs + " succesfully canceled.");

                        return true;
                    } else {
                        return true;
                    }
                } else {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }
        }
        catch (Exception e) {
            if (sender instanceof Player) {
                Player player = (Player) sender;

                player.sendMessage("Unknown error!. Please contact the developer of this plugin. See your server console for details.");
                getServer().getConsoleSender().sendMessage(e.toString());
                return true;
            } else if (sender instanceof BlockCommandSender) {
                return false;
            }
        }

        // If the player (or console) uses our command correct, we can return true
        return true;
    }
}
