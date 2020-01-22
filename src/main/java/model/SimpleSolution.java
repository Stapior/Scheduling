package model;



import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SimpleSolution {
    List<Task> tasks;
    long delay;

    public void countDelay(){
        Problem problem =  new Problem();
        for (Task task: tasks) {
            problem.getLessLoadedMachine().addTask(task);
        }
        delay = problem.getDelay();
    }
}
