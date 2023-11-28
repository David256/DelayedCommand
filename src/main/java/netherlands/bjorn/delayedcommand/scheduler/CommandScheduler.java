package netherlands.bjorn.delayedcommand.scheduler;

import netherlands.bjorn.delayedcommand.Main;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.StringJoiner;

public class CommandScheduler {
    private int id;
    private int times;
    private int seconds;
    // NOTE: in the future this will be an array
    @Nullable
    private String cmd;
    private boolean cancelled;

    public CommandScheduler() {
        this.id = 0;
        this.times = 0;
        this.seconds = 0;
        this.cmd = null;
        this.cancelled = false;
    }

    public CommandScheduler(int times, int seconds, String cmd) {
        this();
        this.times = times;
        this.seconds = seconds;
        this.cmd = cmd;
    }

    public CommandScheduler(int times, int seconds, String cmd, boolean cancelled) {
        this(times, seconds, cmd);
        this.cancelled = cancelled;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    @Nullable
    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @NonNull
    public static CommandScheduler parse(List<String> args) throws CommandSchedulerException {
        if (args == null) {
            throw new CommandSchedulerException("no argument passed", (String) null);
        }
        if (args.isEmpty()) {
            throw new CommandSchedulerException("no argument passed", String.join(" ", args));
        }

        int offset = 0;

        String subcommand = args.get(offset);
        offset++;

        int seconds = 0;
        int times = 0;
        String cmd = null;

        try {
            switch (subcommand) {
                case "once", "repeat", "infinite" -> {
                    // Get the times
                    if (subcommand.equals("once")) {
                        times = 1;
                    } else if (subcommand.equals("repeat")) {
                        // For that format, the fist arg must be the times
                        String stringTimes = args.get(offset);
                        offset++;

                        times = Integer.parseInt(stringTimes);
                    } else {
                        // Only for the infinite subcommand
                        times = -1;
                    }

                    // Get the seconds
                    String stringSeconds = args.get(offset);
                    offset++;
                    seconds = Integer.parseInt(stringSeconds);

                    // Get the command to run
                    StringJoiner cmdParts = new StringJoiner(" ");
                    while (offset < args.size()) {
                        cmdParts.add(args.get(offset));
                        offset++;
                    }

                    // Process the command parts and join it
                    cmd = cmdParts.toString();

                    if (cmd.startsWith("/")) {
                        cmd = cmd.substring(1);
                    }
                }
                case "cancel" -> {
                    String stringId = args.get(offset);
                    int id = Integer.parseInt(stringId);

                    CommandScheduler scheduler = new CommandScheduler();
                    scheduler.setId(id);
                    scheduler.setCancelled(true);
                    return scheduler;
                }
                default -> {
                    throw new CommandSchedulerException("Unknown subcommand: " + subcommand, args);
                }
            }
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            throw new CommandSchedulerException(
                    "Command is very short or missing some arguments",
                    args
            );
        }

        return new CommandScheduler(times, seconds, cmd);
    }
}
