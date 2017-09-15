from multiprocessing import Process
from os import walk, path
from python.logger.custom_logger import Logger

logger = Logger().get_logger(__name__)


class FileNameWorker(Process):
    def __init__(self, file_name_queue, dir_path):
        self.file_name_queue = file_name_queue
        self.dir_path = dir_path
        super().__init__()

    def enqueue_file_path(self):
        """
        Pushes full file path for each file in dir_path to file_name_queue
        :return:
        """
        if not path.isdir(self.dir_path):
            raise FileNotFoundError("Invalid directory path: " + self.dir_path)
        logger.info("Started writing to queue")
        for root, dirs, files in walk(self.dir_path):
            for file in files:
                self.file_name_queue.put(file)
        logger.info("Done")

    def run(self):
        self.enqueue_file_path()
