package com.wiki_dumps_word_counter;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;


public class ParsedArticleQueue {
    private static Queue<String> articles = new ConcurrentLinkedQueue<>();

    static void insert(String article) {
        articles.add(article);
    }


    static String pop() {
        return articles.poll();
    }

    static Boolean isEmpty() {
        return articles.isEmpty();
    }


}
