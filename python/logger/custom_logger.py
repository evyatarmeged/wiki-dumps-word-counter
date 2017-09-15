import logging


class Logger:
    def __init__(self):
        self.formatter = logging.Formatter(fmt='%(asctime)s - %(levelname)s - %(module)s - %(message)s')

        self.handler = logging.StreamHandler()
        self.handler.setFormatter(self.formatter)

    def get_logger(self, name, level=logging.INFO):
        logger = logging.getLogger(name)
        logger.setLevel(level)
        logger.addHandler(self.handler)
        return logger


