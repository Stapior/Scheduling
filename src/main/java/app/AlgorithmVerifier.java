package app;

import lombok.extern.slf4j.Slf4j;
import model.Problem;
import model.Task;
import resolvers.ResolverFactory;
import utils.FileUtil;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
public class AlgorithmVerifier {
    public static void main(String[] args) {
        log.info("WERYFIKACJA ALGORYTMU");
        String resolverName = "simple";
        if (args.length > 0) {
            resolverName = args[0];
        }
        readInstances(resolverName);
    }

    private static void readInstances(String resolverName) {
        File instances = new File("instances");
        int turns = 100;
        long start;
        long stop;
        long sum=0;
        int n=0;
        List<String> files  = Arrays.asList(instances.list());
        files.sort(String::compareTo);
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
                log.info("Dla pilku {} wynik {} w czasie {}", filename, problem.getDelay(), (stop-start)/(1000*turns));
                sum += problem.getDelay();
                n+=1;
                FileUtil.saveSolution(problem, filename.substring(2, 8));
            } catch (Exception e) {
                log.error("Cannot read file ", e);
            }

        }
        log.info("#########################");
        log.info("srednia:{}", sum/n);
    }
}
