package parallelcopier;

import java.nio.file.Path;

/**
 * Represents the copying task for one file.
 */
class CopyTask {
    private final Path sourceFile;
    private final Path destinationFile;

    CopyTask(final Path sourceFile, final Path destination) {
        this.sourceFile = sourceFile;
        this.destinationFile = destination;
    }

    Path getSourceFile() {
        return sourceFile;
    }

    Path getDestinationFile() {
        return destinationFile;
    }
}
