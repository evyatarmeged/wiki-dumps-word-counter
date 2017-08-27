package com.wiki_dumps_word_counter;

import java.util.concurrent.ConcurrentLinkedQueue;


public class ArticleQueue extends ConcurrentLinkedQueue<String> {
    // Singleton DP for Queue
    private static final ArticleQueue articleQueue = new ArticleQueue();

    private ArticleQueue(){}

    static ArticleQueue getInstance() {
        return articleQueue;
    }

    void insert(String article) {
        articleQueue.add(article);
    }

    String pop() {
        return articleQueue.poll();
    }
}
