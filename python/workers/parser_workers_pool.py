import re
from multiprocessing import cpu_count, Pool
from python.logger.custom_logger import Logger

LOGGER = Logger().get_logger(__name__)


class ParserWorkersPool:
    CHARSET = "אבגדהוזחטיכלמנסעפצקרשתםףץןך"
    PATTERN = re.compile("[^"+CHARSET+"]")  # regex pattern

    def __init__(self, article_queue, file_name_queue, base_dir):
        try:
            self.CORES = cpu_count()
        except NotImplementedError:
            self.CORES = 1
        self.file_name_queue = file_name_queue
        self.article_queue = article_queue
        self.base_dir = base_dir
        self.pool = Pool(self.CORES)

    def __getstate__(self):
        """
        Solves NotImplementedError - particularly pickling of the self.pool variable
        https://stackoverflow.com/questions/25382455/python-notimplementederror-pool-objects
        -cannot-be-passed-between-processes
        """
        self_dict = self.__dict__.copy()
        del self_dict['pool']
        return self_dict

    def enqueue_parsed_article(self, path):
        """
        Reads the file given in path param, strips all chars not in CHARSET const
        and pushes to article queue
        :param path: File path to open
        """
        with open(self.base_dir + '/' + path, "r+") as file:
            article = self.parse_article(file.read())
            self.article_queue.put(article)

    @staticmethod
    def parse_article(article):
        """
        Removes all characters not present in CHARSET
        :param article: String to parse
        """
        return re.sub(ParserWorkersPool.PATTERN, ' ', article).strip()

    def execute(self):
        LOGGER.info("Started parsing articles")
        self.pool.map(self.enqueue_parsed_article, self.file_name_queue)
        self.pool.close()
        LOGGER.info("Done")

