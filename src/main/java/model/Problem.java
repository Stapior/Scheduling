package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class Problem {

    private List<Machine> machines;
    private List<Task> tasks;

    public Problem() {
        machines = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            machines.add(new Machine());
        }
    }

    public Task getTask(int id) {
        return tasks.stream().filter((task -> task.getId() == id)).findFirst().orElse(null);
    }

    public long getDelay() {
        long result = 0;
        for (Machine machine : machines) {
            result += machine.getDelay();
        }
        return result;
    }

    public boolean isProblemResolved() {
        for (Task task : tasks) {
            int count = 0;
            for (Machine machine : machines) {
                if (machine.getScheduledTasks().stream().anyMatch(scheduledTask -> scheduledTask.getTask().getId() == task.getId())) {
                    count++;
                }
            }

            if (count != 1) {
                return false;
            }
        }
        return true;
    }
}
