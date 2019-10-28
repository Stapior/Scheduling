import lombok.extern.slf4j.Slf4j;
import model.Solution;

import java.io.File;
import java.util.Objects;

@Slf4j
public class SolutionVerifier {
    public static void main(String[] args) {
        String solutionsCatalog = "results";
        String instancesCatalog = "instances";
        if (args.length > 0) {
            solutionsCatalog = args[0];
        }
        if (args.length > 1) {
            instancesCatalog = args[1];
        }
        log.info("WERYFIKACJA ROZWIAZAN");
        readSolutions(solutionsCatalog, instancesCatalog);
    }

    private static void readSolutions(String solutionsCatalog, String instancesCatalog) {
        File instances = new File(solutionsCatalog);
        for (String filename : Objects.requireNonNull(instances.list())) {
            Solution solution = FileUtil.readSolution(solutionsCatalog + "/" + filename, instancesCatalog + "/" + filename.replace("out", "in"));
            log.info("Weryfikacja rozwiazania: {}", solution.verifySolution());
        }
    }
}
