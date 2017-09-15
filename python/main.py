import time
from python.shared_state.article_queue import ArticleQueue
from python.logger.custom_logger import Logger
from python.shared_state.file_name_queue import FileNameQueue
from python.workers.file_name_worker import FileNameWorker
from python.workers.parser_workers_pool import ParserWorkersPool
from python.workers.word_counter_worker import WordCounterWorker

"""
A directory containing 906 1MB text files of the entire article collection from
Hebrew Wikipedia. Original file `hewiki-20170820-pages-articles.xml` 2.5GB. 
"""
PATH = "/home/mr_evya/Downloads/cli_wiki_dumps"

LOGGER = Logger().get_logger(__name__)


def main():
    """A try at Python parallelism"""
    LOGGER.info("init")
    start = time.time()

    file_name_queue = FileNameQueue()
    article_queue = ArticleQueue()
    file_name_worker = FileNameWorker(file_name_queue, PATH)
    parser_pool = ParserWorkersPool(article_queue, file_name_queue, PATH)
    word_counter_worker = WordCounterWorker(article_queue)

    file_name_worker.start()
    file_name_worker.join()

    parser_pool.execute()

    word_counter_worker.start()
    word_counter_worker.join()
    LOGGER.info("Script finished in: " + str(time.time() - start))


if __name__ == "__main__":
    main()
