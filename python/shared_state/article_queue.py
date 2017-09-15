from multiprocessing import Manager


class ArticleQueue:
    def __init__(self):
        self.__queue = Manager().Queue()

    def put(self, item):
        self.__queue.put(item)

    def poll(self):
        return self.__queue.get()

    def is_empty(self):
        return self.__queue.empty()

    def size(self):
        return self.__queue.qsize()
