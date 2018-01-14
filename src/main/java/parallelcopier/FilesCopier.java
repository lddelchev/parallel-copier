package parallelcopier;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * This class provides functionality for copying files.
 */
class FilesCopier implements Runnable {
    private List<CopyTask> filesCopyTasks;

    FilesCopier(final List<CopyTask> filesCopyTasks) {
        this.filesCopyTasks = filesCopyTasks;
    }

    public void run() {
        for (CopyTask fileCopyTask : filesCopyTasks) {
            try {
                FileUtils.copyFile(
                        new File(fileCopyTask.getSourceFile().toString()),
                        new File(fileCopyTask.getDestinationFile().toString()));
            } catch (IOException e) {
                System.out.println(e.toString());
            }
        }
    }
}
