package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Comparator;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Machine {
    @NonNull
    List<ScheduledTask> scheduledTasks;

    public long getTaskCompletionTime() {
        return scheduledTasks.stream().max(Comparator.comparingLong(ScheduledTask::getCompletionTime)).orElse(new ScheduledTask()).getCompletionTime();
    }

    public void addTask(Task task) {
        scheduledTasks.add(new ScheduledTask(task, Math.min(task.getStartTime(), getTaskCompletionTime())));
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
        scheduledTasks.forEach((task -> {
            resultBuilder.append(task.getTask().getId());
            resultBuilder.append(" ");
        }));
        return resultBuilder.toString();
    }
}
