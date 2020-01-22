package resolvers;

import model.Problem;
import model.SimpleSolution;
import model.Task;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class GeneticResolver implements ProblemResolver {
    int mutations = 200;
    int populationSize = 30;
    Random random = new Random();
    int mutationRatio = 30;

    @Override
    public Problem resolveProblem(Problem problem) {
        List<Task> tasks = new ArrayList<>(problem.getTasks());
        List<SimpleSolution> startPopulation = new ArrayList<>();
        List<SimpleSolution> population = new ArrayList<>();
        startPopulation.add(SimpleSolution.builder().tasks(tasks.stream().
                sorted(Comparator.comparingDouble(task -> task.getEndTime()))
                .collect(Collectors.toList())).build());

        for (int i = 0; i < populationSize -1 ; i++) {
            double c = random.nextInt(100);
            Collections.shuffle(tasks);
            startPopulation.add(SimpleSolution.builder().tasks(new ArrayList<>(tasks)).build());

        }

        for (int i = 0; i < 400; i++) {
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
            startPopulation = new ArrayList<>( population.subList(0, populationSize - 1));
            startPopulation.add(best);
        }

        SimpleSolution best = population.stream().min(Comparator.comparingLong(SimpleSolution::getDelay)).get();
        Problem res = new Problem();
        for (Task task : best.getTasks()) {
            problem.getLessLoadedMachine().addTask(task);
        }
        return res;
    }

    private SimpleSolution getRandom(List<SimpleSolution> solutions) {
        return solutions.get(random.nextInt(solutions.size()));
    }

    private SimpleSolution crossing(SimpleSolution solution, SimpleSolution secondSolution) {
        List<Task> newOrder = new ArrayList<>(solution.getTasks());
        int len = random.nextInt(20);
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
