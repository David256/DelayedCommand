package netherlands.bjorn.delayedcommand;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import static org.bukkit.Bukkit.getServer;

public class TaskRepeat extends BukkitRunnable {
    private final JavaPlugin plugin;
    private String cmd;
    private int repeat;

    public TaskRepeat(JavaPlugin plugin, int repeat, String cmd){
        this.plugin = plugin;
        if (repeat < 1) {
            throw new IllegalArgumentException("Repeat must be greater than 1");
        } else {
            this.repeat = repeat;
        }
        this.cmd = cmd;
    }

    @Override
    public void run(){
        if (repeat > 0) {
            plugin.getServer().dispatchCommand(getServer().getConsoleSender(), cmd);
            repeat--;
        } else {
            this.cancel();
        }
    }
}
