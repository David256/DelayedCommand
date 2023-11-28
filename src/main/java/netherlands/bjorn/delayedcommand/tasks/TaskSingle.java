package netherlands.bjorn.delayedcommand.tasks;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import static org.bukkit.Bukkit.getServer;

public class TaskSingle extends BukkitRunnable {
    private final JavaPlugin plugin;
    private String cmd;

    public TaskSingle(JavaPlugin plugin, String cmd){
        this.plugin = plugin;
        this.cmd = cmd;
    }

    @Override
    public void run(){
        plugin.getServer().dispatchCommand(getServer().getConsoleSender(), cmd);
        this.cancel();
    }
}
