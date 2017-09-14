package com.wiki_dumps_word_counter.shared_state;

import java.util.concurrent.ConcurrentLinkedQueue;


public class ArticleQueue extends ConcurrentLinkedQueue<String> {
    private static ArticleQueue instance;

    private ArticleQueue(){}

    public static ArticleQueue getInstance() {
        if (instance == null) {
            instance = new ArticleQueue();
        }

        return instance;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public void insert(String article) {
        instance.add(article);
    }

    public String  pop() {
        return instance.poll();
    }
}
