package com.wiki_dumps_word_counter;

import java.nio.file.Path;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;


public class FileNameQueue {

    private static Queue<Path> fileNames = new ConcurrentLinkedQueue<>();

    static void insert(Path filePath) {
        fileNames.add(filePath);
    }


    static Path pop() {
        return fileNames.poll();
    }

    static Boolean isEmpty() {
        return fileNames.isEmpty();
    }


}
