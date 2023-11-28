package netherlands.bjorn.delayedcommand.scheduler;

import javax.annotation.Nullable;

public class CommandSchedulerException extends Exception {
    @Nullable
    private final String rawCmd;
    public CommandSchedulerException(String reason, @Nullable String rawCmd) {
        super(reason);
        this.rawCmd = rawCmd;
    }

    @Nullable
    public String getRawCmd() { return this.rawCmd; }
}
