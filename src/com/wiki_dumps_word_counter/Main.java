package com.wiki_dumps_word_counter;

import java.util.concurrent.TimeUnit;

public class Main {

    private static FileNameRetrieverWorker nameRetrieverWorker =
            // A directory containing 906 1MB text files of the entire article collection from
            // Hebrew Wikipedia. Original file `hewiki-20170820-pages-articles.xml` 2.5GB.
            new FileNameRetrieverWorker("/home/mr_evya/Downloads/cli_wiki_dumps");
    private static WorkersPool pool = new WorkersPool();
    private static WordCounterWorker counterWorker = new WordCounterWorker();


    public static void main(String[] args) throws InterruptedException {
        nameRetrieverWorker.run();
        pool.execute();
        TimeUnit.SECONDS.sleep(1); // This gives WorkersPool a head start
        counterWorker.run();
    }
}
