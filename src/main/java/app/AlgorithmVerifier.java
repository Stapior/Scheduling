package app;

import lombok.extern.slf4j.Slf4j;
import model.Problem;
import model.Task;
import resolvers.ResolverFactory;
import utils.FileUtil;

import java.io.File;
import java.util.List;
import java.util.Objects;

@Slf4j
public class AlgorithmVerifier {
    public static void main(String[] args) {
        log.info("WERYFIKACJA ALGORYTMU");
        String resolverName = "naive";
        if (args.length > 0) {
            resolverName = args[0];
        }
        readInstances(resolverName);
    }

    private static void readInstances(String resolverName) {
        File instances = new File("instances");
        long start;
        long stop;
        for (String filename : Objects.requireNonNull(instances.list())) {
            List<Task> result;
            try {
                result = FileUtil.readInstance("instances/" + filename);
                Problem problem = new Problem();
                problem.setTasks(result);
                start = System.currentTimeMillis();
                ResolverFactory.getResolver(resolverName).resolveProblem(problem);
                stop = System.currentTimeMillis();
                log.info("Dla pilku {} wynik {} w czasie {}", filename, problem.getDelay(), stop-start);
                FileUtil.saveSolution(problem, filename.substring(2, 8));
            } catch (Exception e) {
                log.error("Cannot read file ", e);
            }
        }
    }
}
