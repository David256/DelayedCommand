package netherlands.bjorn.delayedcommand.scheduler;

import netherlands.bjorn.delayedcommand.scheduler.CommandScheduler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CommandSchedulerTest {

    @DisplayName("call once")
    @Test
    void testCallOnce() throws CommandSchedulerException {
        CommandScheduler scheduler = CommandScheduler.parse(
                List.of("once 10 time set day".split(" "))
        );

        assertEquals(scheduler.getTimes(), 1);
        assertEquals(scheduler.getSeconds(), 10);
        assertEquals(scheduler.getTicks(), 200);
        assertEquals(scheduler.getCmd(), "time set day");
        assertFalse(scheduler.isCancelled());
    }

    @DisplayName("call repeat")
    @Test
    void testCallRepeat() throws CommandSchedulerException {
        CommandScheduler scheduler = CommandScheduler.parse(
                List.of("repeat 3 10 /time set night".split(" "))
        );

        assertEquals(scheduler.getTimes(), 3);
        assertEquals(scheduler.getSeconds(), 10);
        assertEquals(scheduler.getTicks(), 200);
        assertEquals(scheduler.getCmd(), "time set night");
        assertFalse(scheduler.isCancelled());
    }

    @DisplayName("call infinite")
    @Test
    void testCallInfinite() throws CommandSchedulerException {
        CommandScheduler scheduler = CommandScheduler.parse(
                List.of("infinite 60 weather rain".split(" "))
        );

        assertEquals(scheduler.getTimes(), -1);
        assertEquals(scheduler.getSeconds(), 60);
        assertEquals(scheduler.getTicks(), 1200);
        assertEquals(scheduler.getCmd(), "weather rain");
        assertFalse(scheduler.isCancelled());
    }

    @DisplayName("call cancel")
    @Test
    void testCallCancel() throws CommandSchedulerException {
        CommandScheduler scheduler = CommandScheduler.parse(
                List.of("cancel 25".split(" "))
        );

        assertEquals(scheduler.getTimes(), 0);
        assertEquals(scheduler.getSeconds(), 0);
        assertEquals(scheduler.getTicks(), 0);
        assertNull(scheduler.getCmd());
        assertEquals(scheduler.getId(), 25);
    }
}