package resolvers;

import model.Machine;
import model.Problem;
import model.Task;

import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class SimpleResolver implements ProblemResolver {

    @Override
    public Problem resolveProblem(Problem problem) {
        List<Task> tasks = new ArrayList<>(problem.getTasks());
//        tasks.sort(Comparator.comparingLong(task -> task.getDuration()));
//        double avg = tasks.stream().mapToInt(Task::getDuration).average().orElse(0);
//        double minDuration = tasks.stream().min(Comparator.comparingLong(Task::getDuration)).orElse(new Task()).getDuration();
//        double dueTime = tasks.stream().max(Comparator.comparingLong(Task::getEndTime)).orElse(new Task()).getEndTime();
//        double minDueTime = tasks.stream().min(Comparator.comparingLong(Task::getEndTime)).orElse(new Task()).getEndTime();
        double maxDuration = tasks.stream().max(Comparator.comparingLong(Task::getDuration)).orElse(new Task()).getDuration();

        while (!tasks.isEmpty()) {
            Machine machine = problem.getLessLoadedMachine();
            long endTime = machine.getTaskCompletionTime();
            Task task = tasks.stream().min(Comparator.comparingDouble(t -> Math.max(t.getStartTime() - endTime, 0) * 10 + t.getDuration() / maxDuration)).orElse(null);
            machine.addTask(task);
            tasks.remove(task);
        }
//        for (Task task : tasks) {
//            problem.getLessLoadedMachine().addTask(task);
//        }
        return problem;
    }
}
