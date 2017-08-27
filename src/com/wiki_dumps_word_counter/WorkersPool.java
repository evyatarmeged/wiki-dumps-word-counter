package com.wiki_dumps_word_counter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class WorkersPool {

    private ArticleQueue articleQueue = ArticleQueue.getInstance();

    private static final int CORES = Runtime.getRuntime().availableProcessors();

    void execute () {
        // Tried with 4-8 cores, result is always around 35-40 seconds E2E
        ExecutorService pool = Executors.newFixedThreadPool(CORES);
        for (int i = 0; i < CORES; i++) {
            Runnable worker = new SimpleWorker();
            pool.execute(worker);
        }

        while (!this.articleQueue.isEmpty()) {}
        pool.shutdown();
    }
}
