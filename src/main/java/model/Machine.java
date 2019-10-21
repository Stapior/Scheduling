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
}
