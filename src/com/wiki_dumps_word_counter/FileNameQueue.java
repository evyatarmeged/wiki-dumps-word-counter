package com.wiki_dumps_word_counter;

import java.nio.file.Path;
import java.util.concurrent.ConcurrentLinkedQueue;


public class FileNameQueue  extends ConcurrentLinkedQueue<Path> {
    private static FileNameQueue fileNameQueue = new FileNameQueue();

    private FileNameQueue() {}

    static FileNameQueue getInstance() {
        return fileNameQueue;
    }

    void insert(Path filePath) {
        fileNameQueue.add(filePath);
    }

    Path pop() {
        return fileNameQueue.poll();
    }
}
