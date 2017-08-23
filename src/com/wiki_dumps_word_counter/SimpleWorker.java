package com.wiki_dumps_word_counter;

import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Stream;

public class SimpleWorker implements Runnable {

    private static final String CHARSET = "אבגדהוזחטיכלמנסעפצקרשת";
    private StringBuilder data;
    private Stream<String> lines;


    private void ParseFileContents() throws IOException {
        // Read file contents, remove unwanted chars and push to ParsedArticleQueue
        while (!FileNameQueue.isEmpty()) {
            data = new StringBuilder();
            lines = Files.lines(FileNameQueue.pop());
            lines.forEach(line -> data.append(line).append("\n"));
            lines.close();
        }
//         TODO: implement better text parsing (remove all chars except א-ב)
        ParsedArticleQueue.insert(data.toString().replaceAll("[\\w]", " "));
    }

    public void run() {
        try {
            this.ParseFileContents();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
