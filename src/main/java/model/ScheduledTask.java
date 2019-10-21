package model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScheduledTask {
    private Task task;
    private long startTime;

    public ScheduledTask() {
        startTime = 0;
        task = new Task();
    }

    public long getDelay() {
        return Math.max(getCompletionTime() - task.getEndTime(), 0);
    }

    public long getCompletionTime() {
        return (startTime + task.getDuration());
    }
}
