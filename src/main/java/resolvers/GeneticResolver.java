package resolvers;

import model.Machine;
import model.Problem;
import model.SimpleSolution;
import model.Task;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

class GeneticResolver implements ProblemResolver {
    int mutations = 16;
    int populationSize = 8;
    Random random = new Random();
    int mutationRatio = 60;


    @Override
    public Problem resolveProblem(Problem problem) {
        List<SimpleSolution> startPopulation = new ArrayList<>();
        List<SimpleSolution> population = new ArrayList<>();


        for (int i = 0; i < populationSize - 1; i++) {
            double c = random.nextInt(40) + 60.0;
            List<Task> tasks = new ArrayList<>(problem.getTasks());
            double maxDuration = tasks.stream().max(Comparator.comparingLong(Task::getDuration)).orElse(new Task()).getDuration();
            Problem p =  new Problem();
            List<Task> newTasks = new ArrayList<>();
            while (!tasks.isEmpty()) {
                Machine machine = p.getLessLoadedMachine();
                long endTime = machine.getTaskCompletionTime();
                Task task = tasks.stream().min(Comparator.comparingDouble(t -> Math.max(t.getStartTime() - endTime, 0) * (10*(c/100)) + t.getDuration() / maxDuration)).orElse(null);
                machine.addTask(task);
                newTasks.add(task);
                tasks.remove(task);
            }
            startPopulation.add(SimpleSolution.builder().tasks(newTasks).build());

        }

        for (int i = 0; i < 8000; i++) {
            population = new ArrayList<>(startPopulation);

            for (int j = 0; j < mutations; j++) {
                if (random.nextInt(100) < mutationRatio) {
                    population.add(mutate(getRandom(startPopulation)));
                } else {
                    population.add(crossing(getRandom(startPopulation), getRandom(startPopulation)));
                }
            }
            startPopulation.clear();


            computeDelays(population);
            SimpleSolution best = population.stream().min(Comparator.comparingLong(SimpleSolution::getDelay)).get();
            population.remove(best);
            population.sort(Comparator.comparingLong(SimpleSolution::getDelay));
            startPopulation = new ArrayList<>(population.subList(0, populationSize - 1));
            startPopulation.add(best);
        }

        SimpleSolution best = population.stream().min(Comparator.comparingLong(SimpleSolution::getDelay)).get();
        Problem res = new Problem();
        res.setTasks(problem.getTasks());
        for (Task task : best.getTasks()) {
            res.getLessLoadedMachine().addTask(task);
        }
        System.out.println(best.getDelay());
        System.out.println(res.getDelay());
        return res;
    }

    private SimpleSolution getRandom(List<SimpleSolution> solutions) {
        return solutions.get(random.nextInt(solutions.size()));
    }

    private SimpleSolution crossing(SimpleSolution solution, SimpleSolution secondSolution) {
        List<Task> newOrder = new ArrayList<>(solution.getTasks());
        int len = random.nextInt(solution.getTasks().size()/5);
        int start = random.nextInt(newOrder.size() - len);
        for (int i = start; i < start + len; i++) {
            Task task = secondSolution.getTasks().get(i);
            newOrder.remove(task);
            newOrder.add(i, task);
        }

        return SimpleSolution.builder().tasks(newOrder).build();
    }

    private SimpleSolution mutate(SimpleSolution solution) {
        List<Task> newOrder = new ArrayList<>(solution.getTasks());
        for (int i = 0; i < random.nextInt(3) + 1; i++) {
            int index = random.nextInt(newOrder.size());
            int secondIndex = random.nextInt(newOrder.size());
            Task a = newOrder.get(index);
            newOrder.set(index, newOrder.get(secondIndex));
            newOrder.set(secondIndex, a);
        }
        return SimpleSolution.builder().tasks(newOrder).build();
    }

    private void computeDelays(List<SimpleSolution> population) {
        population.stream().parallel().forEach(SimpleSolution::countDelay);
    }

}
