package com.wiki_dumps_word_counter.workers;

import com.wiki_dumps_word_counter.pools.CounterWorkerPool;
import com.wiki_dumps_word_counter.shared_state.SharedMap;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;


public class CsvWriterWorker implements Runnable {

    private final static Logger LOGGER = Logger.getLogger(CounterWorkerPool.class.getName());
    private static SharedMap sharedMap = SharedMap.getInstance();

    private static Map<String, Integer> sortMapByValue(SharedMap unSortedMap) {
        // Sort Map by value - descending order
        return unSortedMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }


    private static void writeMapToCSV() {
        LOGGER.info("Writing to CSV...");
        Map<String,Integer> sortedWordCount = sortMapByValue(sharedMap);
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
            writeMapToCSV();
        }
    }
