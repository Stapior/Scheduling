import lombok.extern.slf4j.Slf4j;
import model.Task;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
public class Generator {
    public static void main(String[] args) {
        log.info("GENEROWANIE INSTANCJII");
        Generator generator = new Generator();
        for (int n = 50; n <= 500; n += 50) {
            List<Task> tasks = generator.generateTasks(n);
            try {
                FileUtil.saveTasks(tasks);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        generator.readInstances();
    }

    private void readInstances() {
        File instances = new File("instances");
        for (String filename : instances.list()) {
            List<Task> result = null;
            try {
                result = FileUtil.readInstance("instances/" + filename);
            } catch (Exception e) {
                log.error("Cannot read file {}", filename);
            }
            log.info("Readed file: {}", result);
        }
    }


    private List<Task> generateTasks(int n) {
        log.info("Generowanie instancji {} zadań", n);
        List<Task> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Task task = new Task();
            task.setDuration(getRandomTime());
            task.setId(i + 1);
            result.add(task);
        }
        int avg = (int) Math.abs(result.stream().mapToInt(Task::getDuration).average().getAsDouble());
        int sum = result.stream().mapToInt(Task::getDuration).sum();
        int maxStartTime = (sum / 4) - ((n / 50)) * avg;
        if (maxStartTime < 0) {
            maxStartTime = 1;
        }
        int finalMaxStartTime = maxStartTime;
        result.forEach(task -> {
            int startTime = getRandom(0, finalMaxStartTime);
            task.setStartTime(startTime);
            int taskMinEndTime = task.getStartTime() + task.getDuration();
            task.setEndTime(taskMinEndTime + getRandom(0, task.getDuration()));
        });

        return result;
    }


    private int getRandomTime() {
        return getRandom(1, 20);
    }

    private int getRandom(int start, int stop) {
        Random r = new Random();
        return r.nextInt((start + stop) + 1) + start;
    }

}
