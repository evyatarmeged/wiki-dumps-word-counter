package com.wiki_dumps_word_counter.shared_state;

import java.util.concurrent.ConcurrentHashMap;

public class SharedMap extends ConcurrentHashMap<String, Integer> {
    private static SharedMap instance;

    private SharedMap() {}

    public static SharedMap getInstance() {
        if (instance == null) {
            instance = new SharedMap();
        }
        return instance;
    }

}
