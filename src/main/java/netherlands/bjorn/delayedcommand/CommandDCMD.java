package netherlands.bjorn.delayedcommand;

import netherlands.bjorn.delayedcommand.scheduler.CommandScheduler;
import netherlands.bjorn.delayedcommand.scheduler.CommandSchedulerException;
import netherlands.bjorn.delayedcommand.tasks.TaskInfinite;
import netherlands.bjorn.delayedcommand.tasks.TaskRepeat;
import netherlands.bjorn.delayedcommand.tasks.TaskSingle;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.scheduler.BukkitTask;
import org.checkerframework.checker.nullness.qual.NonNull;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class CommandDCMD implements CommandExecutor, TabCompleter {
    public static String COMMAND_NAME = "dcmd";
    private final Main plugin;
    private int lastTaskId;

    public CommandDCMD(Main plugin) {
        this.plugin = plugin;
        this.lastTaskId = 0;
    }

    public void autoRegister() {
        PluginCommand pluginCommand = Objects.requireNonNull(plugin.getCommand(CommandDCMD.COMMAND_NAME));

        pluginCommand.setExecutor(this);
        pluginCommand.setTabCompleter(this);
    }

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, String[] args) {
        Logger logger = plugin.getLogger();

        if(args.length == 0){
            return false;
        }

        // Parse the command
        CommandScheduler scheduler = null;
        try {
            scheduler = CommandScheduler.parse(List.of(args));
        } catch (CommandSchedulerException e) {
            sender.sendMessage(ChatColor.RED + e.getMessage());
            plugin.getLogger().warning("Bad command: " + e.getRawCmd());
            return false;
        }

        logger.info("scheduler: " + scheduler);

        if (scheduler.isCancelled()) {
            logger.info("will cancel task with id: " + scheduler.getId());

            Bukkit.getScheduler().cancelTask(scheduler.getId());
            lastTaskId = 0;

            sender.sendMessage(ChatColor.AQUA + "Command with id " + ChatColor.YELLOW + scheduler.getId() + ChatColor.AQUA + " successfully canceled.");
            return true;
        }

        // Call the task runner
        BukkitTask task = null;
        int times = scheduler.getTimes();
        int ticks = scheduler.getTicks();
        String cmd = scheduler.getCmd();
        if (times == 1) {
            task = new TaskSingle(this.plugin, cmd).runTaskLater(plugin, ticks);
        } else if (times == -1) {
            task = new TaskInfinite(this.plugin, cmd).runTaskTimer(plugin, ticks, ticks);
        } else {
            task = new TaskRepeat(this.plugin, times, cmd).runTaskTimer(plugin, ticks, ticks);
        }

        sender.sendMessage(ChatColor.GOLD + "Cancel this command with /dcmd cancel " + task.getTaskId());
        lastTaskId = task.getTaskId();
        return true;
    }

    @Override
    public List<String> onTabComplete(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, String[] args) {
        List<String> hints = new ArrayList<>();

        if (args.length == 1) {
            hints.add("once");
            hints.add("repeat");
            hints.add("infinite");
            hints.add("cancel");
        } else if (args.length == 2) {
            if (args[0].equals("cancel")) {
                hints.add("<id>");
                if (lastTaskId > 0) {
                    hints.add(Integer.toString(lastTaskId));
                }
            } else if (args[0].equals("repeat")) {
                hints.add("<times>");
            } else {
                hints.add("<seconds>");
            }
        } else if (args.length == 3) {
            if (args[0].equals("repeat")) {
                hints.add("<seconds>");
            } else if (!args[0].equals("cancel")) {
                hints.add("<commands>");
            }
        } else if (args.length == 4 && args[0].equals("repeat")) {
            hints.add("<commands>");
        }

        return hints;
    }
}
