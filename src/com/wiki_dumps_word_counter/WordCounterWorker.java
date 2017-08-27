package com.wiki_dumps_word_counter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;


public class WordCounterWorker implements Runnable {

    private Map<String, Integer> wordCount = new HashMap<>();
    private ArticleQueue articleQueue = ArticleQueue.getInstance();


    private void countWords() {
        // Insert words to wordCount HashMap
        while (!this.articleQueue.isEmpty()) {
            String[] article = this.articleQueue.pop().split("\\s");
            for (String word : article) {
                if (word.length() > 2) {
                    wordCount.merge(word, 1, Integer::sum);
                }
            }
        }

        Map<String,Integer> sortedWordCount = this.sortMapByValue(wordCount);
        this.writeMapToCSV(sortedWordCount);
    }

    private Map<String, Integer> sortMapByValue(Map <String,Integer> unSortedMap) {
        // Sort Map by value - descending order
        return unSortedMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }

    private void writeMapToCSV(Map<String,Integer> sortedWordCount) {
        // Write sorted key, value pairs to csv file
        String eol = System.getProperty("line.separator");
        try (Writer writer = new FileWriter("words.csv")) {
            writer.append("word, word_length, occurrences");
            writer.append(eol);
            for (Map.Entry<String, Integer> entry : sortedWordCount.entrySet()) {
                writer.append(entry.getKey())
                        .append(',')
                        // Can't call toString() on length(), Can't append int
                        .append(entry.getKey().length()+"")
                        .append(',')
                        .append(entry.getValue().toString())
                        .append(eol);
            }

        } catch (IOException ex) {
            ex.printStackTrace(System.err);
        }

    }
        @Override
        public void run () {
            this.countWords();
        }
    }
