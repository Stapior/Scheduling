
package app;

        import lombok.extern.slf4j.Slf4j;
        import model.Solution;
        import model.Stats;
        import utils.FileUtil;

        import java.io.File;
        import java.util.ArrayList;
        import java.util.Comparator;
        import java.util.List;
        import java.util.Objects;
        import java.util.stream.Collectors;

@Slf4j
public class SolutionVerifier {
    public static void main(String[] args) {
        List<Stats> results = new ArrayList<>();
        String solutionsCatalog = "results";
        String instancesCatalog = "instances";
        if (args.length > 0) {
            solutionsCatalog = args[0];
        }
        if (args.length > 1) {
            instancesCatalog = args[1];
        }
        log.info("WERYFIKACJA ROZWIAZAN");
        readSolutions(solutionsCatalog, instancesCatalog, results);
        List<String> indexes = results.stream().map(Stats::getIndex).distinct().collect(Collectors.toList());
        for (String index: indexes) {
            System.out.println("\n"+index);
            List<Stats> st = results.stream().filter(stats -> stats.getIndex().equals(index)).collect(Collectors.toList());
            st.sort(Comparator.comparingLong(Stats::getSize));
            st.forEach(stats -> System.out.println(stats));

        }

    }

    private static void readSolutions(String solutionsCatalog, String instancesCatalog, List<Stats> stats) {
        File instances = new File(solutionsCatalog);
        for (String filename : Objects.requireNonNull(instances.list())) {
            Solution solution = FileUtil.readSolution(solutionsCatalog + "/" + filename, instancesCatalog + "/" + filename.replace("out", "in"));
            stats.add(Stats.builder().index(filename.substring(3,9)).result(solution.getDelay()).size(solution.getProblem().getTasks().size()).build());
            log.info("Weryfikacja rozwiazania: {}", solution.verifySolution());
        }
    }
}