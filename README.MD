# Wiki-dumps-word-counter<br>
Counting word occurrence in hewiki dumps downloaded from https://dumps.wikimedia.org/hewiki/.<br>
Using <a href='https://github.com/attardi/wikiextractor'>WikiExtractor</a> to extract text from the XML dump,
parsing each article with RegEx to strip it from any non-hebrew characters.<br>
Writes the results to csv. Uses multithreading for optimized performance.


