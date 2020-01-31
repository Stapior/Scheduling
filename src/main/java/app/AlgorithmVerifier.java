package app;

import lombok.extern.slf4j.Slf4j;
import model.Problem;
import model.Stats;
import model.Task;
import resolvers.ResolverFactory;
import utils.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class AlgorithmVerifier {
    public static void main(String[] args) {
        log.info("WERYFIKACJA ALGORYTMU");
        String resolverName = "genetic";
        if (args.length > 0) {
            resolverName = args[0];
        }
        readInstances(resolverName);
    }

    private static void readInstances(String resolverName) {
        File instances = new File("instances");
        int turns = 1;
        long start;
        long stop;
        long sum = 0;
        int n = 0;
        List<String> files = Arrays.asList(instances.list());
        files.sort(String::compareTo);
        List<Stats> results = new ArrayList<>();
        for (String filename : files) {
            List<Task> result;
            try {
                result = FileUtil.readInstance("instances/" + filename);
                Problem problem = new Problem();
                problem.setTasks(result);
                start = System.nanoTime();
                for (int i = 0; i < turns; i++) {
                    problem.getMachines().forEach(machine -> machine.getScheduledTasks().clear());
                    ResolverFactory.getResolver(resolverName).resolveProblem(problem);
                }
                stop = System.nanoTime();
                log.info("Dla pilku {} wynik {} w czasie {}", filename, problem.getDelay(), (stop - start) / (1000 * turns));
                results.add(Stats.builder().index(filename.substring(2, 8)).result(problem.getDelay()).size(problem.getTasks().size()).time((stop - start) / (1000 * turns)).build());
                sum += problem.getDelay();
                n += 1;
                FileUtil.saveSolution(problem, filename.substring(2, 8));
            } catch (Exception e) {
                log.error("Cannot read file ", e);
            }
        }
        List<String> indexes = results.stream().map(Stats::getIndex).distinct().collect(Collectors.toList());
//        Integer[] a = {132225, 132214, 132219, 132195, 125342, 132209, 132207, 132221, 127173, 132349, 132348, 132197, 132319, 132215, 127329, 132280, 126151, 132192};
        Integer[] a = {132319};
        List<String> indexesInOrder = new ArrayList<Integer>(Arrays.asList(a)).stream().map(String::valueOf).collect(Collectors.toList());
        for (String index : indexesInOrder) {
            List<Stats> st = results.stream().filter(stats -> stats.getIndex().equals(index)).collect(Collectors.toList());
            st.sort(Comparator.comparingLong(Stats::getSize));
            st.forEach(stats -> System.out.println(stats.toResult()));

        }
        log.info("#########################");
        log.info("srednia:{}", sum / n);
    }
}
