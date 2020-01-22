package resolvers;

public class ResolverFactory {
    public static ProblemResolver getResolver(String name) {
        ProblemResolver problemResolver;
        switch (name.toUpperCase()) {
            case "SIMPLE":
                problemResolver = new SimpleResolver();
                break;
            case "GENETIC":
                problemResolver = new GeneticResolver();
                break;
            default:
                problemResolver = new NaiveResolver();
        }
        return problemResolver;
    }
}
