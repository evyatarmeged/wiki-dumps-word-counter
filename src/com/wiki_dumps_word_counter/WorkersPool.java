package com.wiki_dumps_word_counter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class WorkersPool {
    private static final int CORES = Runtime.getRuntime().availableProcessors();

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(CORES);
        for (int i = 0; i < CORES; i++) {
            Runnable worker = new SimpleWorker();
            pool.execute(worker);
        }
        pool.shutdown();
        if (pool.isTerminated()) {
            System.out.println("All workers are terminated");
        }
    }
}
