import queue
from multiprocessing import Manager


class FileNameQueue:
    def __init__(self):
        self.__queue = Manager().Queue()

    def __iter__(self):
        """Make FileNameQueue object iterable to enable a pool of processes run concurrently with map function"""
        while True:
            try:
                yield self.__queue.get_nowait()
            except queue.Empty:
                return

    def put(self, item):
        self.__queue.put(item)

    def poll(self):
        return self.__queue.get()

    def is_empty(self):
        return self.__queue.empty()

    def size(self):
        return self.__queue.qsize()
