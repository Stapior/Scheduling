package model;


import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@Builder
public class SimpleSolution {
    List<Task> tasks;
    long delay;

    public void countDelay() {
        Integer[] arr = {0, 0, 0, 0};
        List<Integer> machines = new ArrayList<>();
        int dly=0;
        Collections.addAll(machines, arr);
        for (Task task : tasks) {
            Integer min = Collections.min(machines);
            int i = machines.indexOf(min);
            if(min < task.getStartTime()){
                min = task.getStartTime() + task.getDuration();
            }else {
                min += task.getDuration();
            }
            int d = min - task.getEndTime();
            if(d >0) dly+=d;
            machines.set(i, min);
        }
        delay = dly;
    }
}
