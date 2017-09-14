package com.wiki_dumps_word_counter.shared_state;

import java.nio.file.Path;
import java.util.concurrent.ConcurrentLinkedQueue;


public class FileNameQueue  extends ConcurrentLinkedQueue<Path> {
    private static FileNameQueue instance;

    private FileNameQueue() {}

    public static FileNameQueue getInstance() {
        if (instance == null) {
            instance = new FileNameQueue();
        }
        return instance;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public void insert(Path filePath) {
        instance.add(filePath);
    }

    public Path pop() {
        return instance.poll();
    }
}
