package com.wiki_dumps_word_counter.pools;

import com.wiki_dumps_word_counter.workers.CounterWorker;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;


public class CounterWorkerPool {

    private final static Logger LOGGER = Logger.getLogger(CounterWorkerPool.class.getName());
    private static final int CORES = Runtime.getRuntime().availableProcessors();

    public void execute() {
        LOGGER.info("Started counting word occurrences");
        ExecutorService pool = Executors.newFixedThreadPool(10);
        for (int i = 0; i < CORES; i++) {
            Runnable worker = new CounterWorker();
            pool.execute(worker);
        }
        pool.shutdown();
        try {
            while (!pool.awaitTermination(400, TimeUnit.MILLISECONDS)) {
                TimeUnit.MILLISECONDS.sleep(10);
            }

        } catch (InterruptedException e) {
            LOGGER.severe(e.getMessage());
        } finally {
            LOGGER.info("Done");
        }
    }
}

