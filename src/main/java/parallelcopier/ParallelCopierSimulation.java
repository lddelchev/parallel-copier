package parallelcopier;

import java.nio.file.Paths;

/**
 * Provides functionality for testing the parallel copier.
 */
final class ParallelCopierSimulation {
    private ParallelCopierSimulation() { };

    private static void simulation(
            final String source,
            final String destination) {
        long startTime;
        long endTime;

        int threads = Runtime.getRuntime().availableProcessors();
        System.out.println(String.format("Performing %d threads copy test:",
                threads));

        startTime = System.currentTimeMillis();
        ParallelCopier parallelCopier2 = new ParallelCopier(threads);
        parallelCopier2.parallelCopy(Paths.get(source), Paths.get(destination));

        endTime = System.currentTimeMillis();
        long executionTime2 = endTime - startTime;
        System.out.println(String.format("Done. The execution time was: %d",
                executionTime2));
    }

    public static void main(final String[] args) {
        String source = args[0];
        String destination = args[1];
        simulation(source, destination);
    }
}
