package parallelcopier;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * This class provides functionality for parallel copying a directory in another
 * directory.
 */
public class ParallelCopier {
    private final int threads;
    private final ExecutorService executor;

    /**
     *
     * @param numberOfThreads the number of threads to perform the copying.
     */
    public ParallelCopier(final int numberOfThreads) {
        this.threads = numberOfThreads;
        executor = Executors.newFixedThreadPool(numberOfThreads);
    }

    /**
     * Copies a directory's content to a destination directory.
     * @param source the directory to be copied.
     * @param destination the destination directory.
     */
    public void parallelCopy(final Path source, final Path destination) {
        List<CopyTask> copyTasks = getAllCopyTasks(source, destination);
        int filesPerThread =
                (int) (Math.ceil(copyTasks.size() / ((double) threads)));
        for (int i = 0; i < threads; i++) {
            int startIndex = i * filesPerThread;
            int endIndex;
            if (i == threads - 1) {
                endIndex = copyTasks.size() - 1;
            } else {
                endIndex = (i + 1) * filesPerThread - 1;
            }

            Runnable filesCopier =
                    new FilesCopier(copyTasks.subList(startIndex, endIndex));
            executor.execute(filesCopier);
        }

        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            System.out.println(e.toString());
        }
    }

    private List<CopyTask> getAllCopyTasks(
            final Path source,
            final Path destination) {

        Collection<File> files =
                FileUtils.listFiles(source.toFile(), null, true);
        List<CopyTask> filesCopyTasks = new ArrayList<>();
        for (File file : files) {
            String target = file.toPath().toString()
                    .replaceAll(source.toString(), destination.toString());
            Path fileDestinationPath = Paths.get(target);
            CopyTask fileCopyTask =
                    new CopyTask(file.toPath(), fileDestinationPath);
            filesCopyTasks.add(fileCopyTask);
        }

        return filesCopyTasks;
    }
}
