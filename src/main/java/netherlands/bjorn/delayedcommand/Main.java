package netherlands.bjorn.delayedcommand;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    @Override
    public void onEnable(){
        CommandDCMD dcmd = new CommandDCMD(this);

        dcmd.autoRegister();
    }

    @Override
    public void onDisable(){

    }
}