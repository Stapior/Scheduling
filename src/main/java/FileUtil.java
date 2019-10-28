import lombok.extern.slf4j.Slf4j;
import model.Machine;
import model.Problem;
import model.Solution;
import model.Task;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
public class FileUtil {

    public static void saveTasks(List<Task> tasks) throws IOException {
        int n = tasks.size();
        PrintWriter writer = new PrintWriter(String.format("instances/in132319_%d.txt", n), StandardCharsets.UTF_8.toString());
        writer.println(n);
        tasks.forEach(task -> writer.println(task.toFileFormat()));
        writer.close();
        log.info("Wygenerowano plik in132319_{}.txt", n);
    }

    public static void saveSolution(Problem problem) throws FileNotFoundException, UnsupportedEncodingException {
        long delay = problem.getDelay();
        int n = problem.getTasks().size();
        PrintWriter writer = new PrintWriter(String.format("results/out132319_%d.txt", n), StandardCharsets.UTF_8.toString());
        writer.println(delay);
        problem.getMachines().forEach(machine -> writer.println(machine.getTasksSave()));
        writer.close();
        log.info("Saved result in file out132319_{}.txt", n);
    }

    public static Solution readSolution(String solutionFileName, String instanceFileName) {
        Solution solution = new Solution();
        Problem problem = new Problem();
        try {
            problem.setTasks(FileUtil.readInstance(instanceFileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        solution.setProblem(problem);
        List<String> lines = Collections.emptyList();
        try {
            lines = Files.readAllLines(Paths.get(solutionFileName), StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("Cannot read file: ", e);
        }
        int delay = Integer.parseInt(lines.get(0));
        lines.remove(0);
        solution.setDelay(delay);
        int i = 0;
        for (String line : lines) {
            Machine machine = problem.getMachines().get(i);
            String[] ids = line.split(" ");
            for (String id : ids) {
                machine.addTask(problem.getTask(Integer.parseInt(id)));
            }
            i++;
        }
        return solution;
    }

    public static List<Task> readInstance(String fileName) throws Exception {
        List<Task> tasks = new ArrayList<>();
        List<String> lines = Collections.emptyList();
        try {
            lines = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("Cannot read file: ", e);
        }
        int n = Integer.parseInt(lines.get(0));

        lines.remove(0);
        lines.forEach((line) -> {
            String[] params = line.split(" ");
            if (params.length == 3) {
                int currentId = tasks.size() + 1;
                tasks.add(Task.builder()
                        .duration(Integer.parseInt(params[0]))
                        .startTime(Integer.parseInt(params[1]))
                        .endTime(Integer.parseInt(params[2]))
                        .id(currentId)
                        .build());
            }
        });
        if (tasks.size() != n) {
            log.error("Invalid file: {}", fileName);
            throw new Exception("Invalid file");
        }
        return tasks;
    }
}
