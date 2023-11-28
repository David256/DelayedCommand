package netherlands.bjorn.delayedcommand.scheduler;

import org.checkerframework.checker.nullness.qual.NonNull;

import javax.annotation.Nullable;
import java.util.List;

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
    public static CommandScheduler parse(List<String> rawCmds) {
        return new CommandScheduler();
    }
}
