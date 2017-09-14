package com.wiki_dumps_word_counter;

import com.wiki_dumps_word_counter.pools.CounterWorkerPool;
import com.wiki_dumps_word_counter.pools.ParserWorkerPool;
import com.wiki_dumps_word_counter.workers.CsvWriterWorker;
import com.wiki_dumps_word_counter.workers.FileNameRetrieverWorker;

import java.util.logging.Logger;


public class Main {
    private final static Logger LOGGER = Logger.getLogger(CounterWorkerPool.class.getName());
    private static FileNameRetrieverWorker nameRetrieverWorker =
    /* A directory containing 906 1MB text files of the entire article collection from
    Hebrew Wikipedia. Original file `hewiki-20170820-pages-articles.xml` 2.5GB. */
            new FileNameRetrieverWorker("/home/mr_evya/Downloads/cli_wiki_dumps");
    private static ParserWorkerPool parserWorkerPool = new ParserWorkerPool();
    private static CounterWorkerPool counterWorkerPool = new CounterWorkerPool();
    private static CsvWriterWorker counterWorker = new CsvWriterWorker();


    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        nameRetrieverWorker.run();
        parserWorkerPool.execute();
        counterWorkerPool.execute();
        counterWorker.run();
        LOGGER.info("Finished running in " + Long.toString((System.currentTimeMillis() - start) / 1000) + " seconds");
    }
}
