package resolvers;

import model.Problem;
import model.Task;

import java.util.Comparator;
import java.util.List;

class SimpleResolver implements  ProblemResolver{

    @Override
    public Problem resolveProblem(Problem problem) {
        List<Task> tasks = problem.getTasks();
        tasks.sort(Comparator.comparingLong(Task::getStartTime));
        for (Task task : tasks) {
            problem.getLessLoadedMachine().addTask(task);
        }
        return  problem;
    }
}
