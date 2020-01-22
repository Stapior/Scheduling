package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
public class ScheduledTask {
    private Task task;
    private long startTime;
    private long completionTime;
    private long delay;

    public ScheduledTask() {
        startTime = 0;
        task = new Task();
    }

    public ScheduledTask(Task task, long startTime) {
        this.task = task;
        this.startTime = startTime;
        completionTime = (startTime + task.getDuration());
        delay =  Math.max(getCompletionTime() - task.getEndTime(), 0);
    }

    public long getDelay() {
        return delay;
    }

    public long getCompletionTime() {
        return completionTime;
    }
}
