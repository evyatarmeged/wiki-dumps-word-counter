from multiprocessing import Process
from operator import itemgetter
from python.logger.custom_logger import Logger

"""
Note: Tried implementing this as a processing pool too, but Python's shared state dictionary 
(e.g multiprocessing.Manager().dict()) is very slow and somewhat ineffective.
https://docs.python.org/3/library/multiprocessing.html#sharing-state-between-processes 
"""

LOGGER = Logger().get_logger(__name__)


class WordCounterWorker(Process):
    def __init__(self, article_queue):
        self.word_count = dict()
        self.article_queue = article_queue
        self.min_word_len = 2
        super().__init__()

    def count_words(self):
        """
        Counts word occurrences for all words longer than 2 characters and updates self.word_count dictionary
        """
        while not self.article_queue.is_empty():
            article = self.article_queue.poll().split()
            for word in article:
                if len(word) <= self.min_word_len:
                    continue
                self.word_count[word] = self.word_count.get(word, 0) + 1
        LOGGER.info("Done")
        self.write_to_csv()

    def sort_dict_by_value(self):
        """Yields key, value tuples sorted by value"""
        yield sorted(self.word_count.items(), key=itemgetter(1), reverse=True)

    def write_to_csv(self):
        """Writes all key, values to csv (formatting - key, len(key), value)"""
        pattern = '{0}, {1}, {2}\n'
        LOGGER.info("Writing to CSV...")
        word_count = self.sort_dict_by_value()
        with open("python/results/words.csv", "w") as csv_file:
            csv_file.write(pattern.format("word", "size", "occurrences"))
            for item in word_count:
                for key, value in item:
                    csv_file.write(pattern.format(key, len(key), value))

    def run(self):
        LOGGER.info("Started counting word occurrences")
        self.count_words()
