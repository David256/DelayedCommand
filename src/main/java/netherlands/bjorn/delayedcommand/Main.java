package netherlands.bjorn.delayedcommand;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    @Override
    public void onEnable(){
        this.getCommand("dcmd").setExecutor(new CommandDCMD(this));
    }

    @Override
    public void onDisable(){

    }
}