import logging

achord_logger = logging.getLogger(__name__)

def initiate_logger():
    achord_logger.setLevel(logging.INFO)
    _consoler_handler = logging.StreamHandler()
    formatter = logging.Formatter(
        "{asctime} - {levelname} - {message}",
        style="{",
        datefmt="%Y-%m-%d %H:%M",
    )
    _consoler_handler.setFormatter(formatter)
    achord_logger.addHandler(_consoler_handler)
