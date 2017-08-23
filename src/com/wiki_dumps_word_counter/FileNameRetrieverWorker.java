package com.wiki_dumps_word_counter;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileNameRetrieverWorker implements Runnable {

    private String path;

    public FileNameRetrieverWorker(String path) {
        this.path = path;
    }

    private void createFileNamesArray() {
        // Iterate over folder in path and push all file names to FileNameQueue
        try (Stream<Path> paths = Files.walk(Paths.get(this.path))) {
            paths.forEach(FileNameQueue::insert);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void run() {
        this.createFileNamesArray();
    }
}

