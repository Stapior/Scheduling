package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.*;

@Data
@AllArgsConstructor
public class Machine {
    @NonNull
    List<ScheduledTask> scheduledTasks;

    public Machine() {
        this.scheduledTasks = new LinkedList<>();
    }

    public long getTaskCompletionTime() {
        if (Objects.isNull(scheduledTasks) || scheduledTasks.isEmpty()) {
            return 0;
        }
        return scheduledTasks.get(0).getCompletionTime();
    }

    public long getDelay() {
        long result = 0;
        for (ScheduledTask task : scheduledTasks) {
            result += task.getDelay();
        }
        return result;
    }

    public String getTasksSave() {
        StringBuilder resultBuilder = new StringBuilder();
        Collections.reverse(scheduledTasks);
        scheduledTasks.forEach((task -> {
            resultBuilder.append(task.getTask().getId());
            resultBuilder.append(" ");
        }));
        return resultBuilder.toString();
    }

    public void addTask(Task task) {
        scheduledTasks.add(0,new ScheduledTask(task, Math.max(task.getStartTime(), getTaskCompletionTime())));
    }
}
