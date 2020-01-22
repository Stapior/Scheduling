package resolvers;

import model.Machine;
import model.Problem;
import model.Task;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class GeneticResolver implements ProblemResolver {

    @Override
    public Problem resolveProblem(Problem problem) {
        List<Task> tasks = new ArrayList<>(problem.getTasks());
        double maxDuration = tasks.stream().max(Comparator.comparingLong(Task::getDuration)).orElse(new Task()).getDuration();

        while (!tasks.isEmpty()) {
            Machine machine = problem.getLessLoadedMachine();
            long endTime = machine.getTaskCompletionTime();
            Task task = tasks.stream().min(Comparator.comparingDouble(t -> Math.max(t.getStartTime() - endTime, 0) * 10 + t.getDuration() / maxDuration)).orElse(null);
            machine.addTask(task);
            tasks.remove(task);
        }
        return problem;
    }
}
