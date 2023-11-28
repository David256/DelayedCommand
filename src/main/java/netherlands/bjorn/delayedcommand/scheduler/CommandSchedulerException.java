package netherlands.bjorn.delayedcommand.scheduler;

import javax.annotation.Nullable;
import java.util.List;

public class CommandSchedulerException extends Exception {
    @Nullable
    private final String rawCmd;

    public CommandSchedulerException(String reason, @Nullable List<String> rawCmds) {
        super(reason);
        this.rawCmd = rawCmds != null ? String.join(" ", rawCmds) : null;
    }

    public CommandSchedulerException(String reason, @Nullable String rawCmd) {
        super(reason);
        this.rawCmd = rawCmd;
    }

    @Nullable
    public String getRawCmd() { return this.rawCmd; }
}
