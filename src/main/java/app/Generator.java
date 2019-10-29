package app;

import lombok.extern.slf4j.Slf4j;
import model.Task;
import utils.FileUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
public class Generator {
    public static void main(String[] args) {
        log.info("GENEROWANIE INSTANCJII");
        generateAndSaveInstances();
    }

    private static void generateAndSaveInstances() {
        for (int n = 50; n <= 500; n += 50) {
            List<Task> tasks = generateTasks(n);
            try {
                FileUtil.saveTasks(tasks);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private static List<Task> generateTasks(int n) {
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


    private static int getRandomTime() {
        return getRandom(1, 20);
    }

    private static int getRandom(int start, int stop) {
        Random r = new Random();
        return r.nextInt((start + stop) + 1) + start;
    }

}
