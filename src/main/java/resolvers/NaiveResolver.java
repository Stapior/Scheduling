package resolvers;

import model.Problem;

public class NaiveResolver implements ProblemResolver {
    @Override
    public Problem resolveProblem(Problem problem) {
        double n = Math.ceil(problem.getTasks().size() / 4.0);
        problem.getTasks().forEach((task -> {
            problem.getMachines().get((int) Math.floor((task.getId() - 1) / n)).addTask(task);
        }));
        return problem;
    }
}
