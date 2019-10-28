package resolvers;

public class ResolverFactory {
    public static ProblemResolver getResolver(String name) {
        ProblemResolver problemResolver;
        switch (name.toUpperCase()) {
            default:
                problemResolver = new NaiveResolver();
        }
        return problemResolver;
    }
}
