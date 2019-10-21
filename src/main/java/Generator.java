import lombok.extern.slf4j.Slf4j;
import model.Task;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
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
                generator.saveTasks(tasks);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private int schedule(List<Task> tasks) {
        tasks.sort(Comparator.comparingInt(Task::getStartTime));
        List<Task> m1 = new ArrayList<>();
        List<Task> m2 = new ArrayList<>();
        List<Task> m3 = new ArrayList<>();
        List<Task> m4 = new ArrayList<>();
        return 0;
    }



    private List<Task> generateTasks(int n) {
        log.info("Generowanie instancji {} zadań", n);
        List<Task> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Task task = new Task();
            task.setDuration(getRandomTime());
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

    private void saveTasks(List<Task> tasks) throws IOException {
        int n = tasks.size();
        PrintWriter writer = new PrintWriter(String.format("instances/instances_%d.txt", n), StandardCharsets.UTF_8.toString());
        writer.println(n);
        tasks.forEach(task -> writer.println(task.toFileFormat()));
        writer.close();
        log.info("Wygenerowano plik instances_{}.txt", n);
    }

    private int getRandomTime() {
        return getRandom(1, 20);
    }

    private int getRandom(int start, int stop) {
        Random r = new Random();
        return r.nextInt((start + stop) + 1) + start;
    }

}
