package netherlands.bjorn.delayedcommand;

import netherlands.bjorn.delayedcommand.scheduler.CommandScheduler;
import netherlands.bjorn.delayedcommand.scheduler.CommandSchedulerException;
import netherlands.bjorn.delayedcommand.tasks.TaskInfinite;
import netherlands.bjorn.delayedcommand.tasks.TaskRepeat;
import netherlands.bjorn.delayedcommand.tasks.TaskSingle;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitTask;
import org.checkerframework.checker.nullness.qual.NonNull;


import java.util.List;
import java.util.logging.Logger;

import static org.bukkit.Bukkit.getServer;

public class CommandDCMD implements CommandExecutor {

    private final Main plugin;

    public CommandDCMD(Main plugin) {
        this.plugin = plugin;
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
            sender.sendMessage(e.getMessage());
            plugin.getLogger().warning("Bad command: " + e.getRawCmd());
            return false;
        }

        logger.info("scheduler: " + scheduler);

        if (scheduler.isCancelled()) {
            logger.info("will cancel task with id: " + scheduler.getId());

            Bukkit.getScheduler().cancelTask(scheduler.getId());

            sender.sendMessage("Command with id " + scheduler.getId() + " successfully canceled.");
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

        sender.sendMessage("Cancel this command with /dcmd cancel " + task.getTaskId());
        return true;
    }
}
