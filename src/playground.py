# import json
# from test.utils.datastructure_utils import *
# from logic.keylogger import Keylogger
#
# kl = Keylogger()
# kl.start()

import fitz
from logic.pdf_converter import PdfConverter

pdf_path = "./test/resources/test-pdf-1.pdf"
output_image_path = "output.jpg"
page_number = 0
target_width = 800
target_height = 600

doc = fitz.open(pdf_path)
print(str(doc))

# pdf_page_to_image(pdf_path, page_number, output_image_path, target_width, target_height)

