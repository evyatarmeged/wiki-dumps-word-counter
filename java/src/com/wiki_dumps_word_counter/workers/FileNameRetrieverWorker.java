package com.wiki_dumps_word_counter.workers;

import com.wiki_dumps_word_counter.shared_state.FileNameQueue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileNameRetrieverWorker implements Runnable {

    private String path;
    private FileNameQueue fileNameQueue = FileNameQueue.getInstance();

    public FileNameRetrieverWorker(String path) {
        this.path = path;
    }

    private void EnqueueFilePath() {
        // Iterate over folder in path and push all file names to FileNameQueue
        try (Stream<Path> paths = Files.walk(Paths.get(this.path))) {
            paths.forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    this.fileNameQueue.insert(filePath);
                }
            });

        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }

    @Override
    public void run() {
        this.EnqueueFilePath();
    }
}
