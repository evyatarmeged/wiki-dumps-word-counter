package com.wiki_dumps_word_counter.workers;


import com.wiki_dumps_word_counter.shared_state.ArticleQueue;
import com.wiki_dumps_word_counter.shared_state.SharedMap;

public class CounterWorker implements Runnable {

    private SharedMap sharedMap = SharedMap.getInstance();
    private ArticleQueue articleQueue = ArticleQueue.getInstance();

    private void countWords() {
        // Insert words to wordCount HashMap
        while (!this.articleQueue.isEmpty()) {
            synchronized (this) {
                String[] article = this.articleQueue.pop().split("\\s");
                for (String word : article) {
                    if (word.length() > 2) {
                        sharedMap.merge(word, 1, Integer::sum);
                    }
                }
            }
        }
    }

    @Override
    public void run() {
        this.countWords();
    }
}
