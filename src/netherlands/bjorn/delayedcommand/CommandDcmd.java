package netherlands.bjorn.delayedcommand;

import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.Timer;
import java.util.TimerTask;

import static org.bukkit.Bukkit.getServer;

public class CommandDcmd implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String seconds, String[] args) {
        Timer timer = new Timer();
        StringBuilder dcmd = new StringBuilder();
        try {
            int secs = Integer.parseInt(args[0]);

            for(int i=1; i < args.length; i++){
                if(args[i].startsWith("/")){
                    args[i] = args[i].substring(1);
                }
                dcmd.append(args[i] + ' ');
            }

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    getServer().dispatchCommand(getServer().getConsoleSender(), dcmd.toString());
                    //getServer().getConsoleSender().sendMessage(args[1]);
                }
            }, secs*1000);
        }
        catch (NumberFormatException e){
            if (sender instanceof Player) {
                Player player = (Player) sender;

                player.sendMessage( "Syntax error: Your first argument must be a number. Eg: /dcmd 10 time set day" );
                return true;
            } else if (sender instanceof  BlockCommandSender){
                return false;
            }
        }
        catch (Exception e){
            if (sender instanceof Player) {
                Player player = (Player) sender;

                player.sendMessage( "Unknown error!. Please contact the developer of this plugin. See your server console for details." );
                getServer().getConsoleSender().sendMessage(e.toString());
                return true;
            } else if (sender instanceof  BlockCommandSender){
                return false;
            }
        }

        // If the player (or console) uses our command correct, we can return true
        return true;
    }
}
