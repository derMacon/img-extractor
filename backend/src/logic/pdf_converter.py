from src.utils.logging_config import log
from src.data.settings import Settings
import fitz
import os
from PIL import Image
import sys


class PdfConverter:

    def __init__(self, pdf_path: str, settings: Settings):
        if pdf_path is None:
            log.error('no pdf path provided for pdf converter - stopping application')
            sys.exit(1)

        if settings is None:
            log.error('no setting provided for pdf converter - stopping application')
            sys.exit(1)

        # TODO throw exception if input file is not .pdf
        log.debug("pdf converter for path.: %s", pdf_path)

        self._doc = fitz.open(pdf_path)
        self._name = os.path.basename(pdf_path)
        self._settings = settings

        log.info('opening pdf with path: %s', pdf_path)
        log.debug('render settings: %s', self._settings)

    def close(self):
        log.info('closing pdf: %s', str(self._doc))
        self._doc.close()

    def get_page_count(self):
        return self._doc.page_count

    def render_img(self, page_number):
        log.info('rendering page %s at idx %s', self._doc, page_number)

        if page_number < 0 or page_number >= self._doc.page_count:
            log.error("Invalid page number: %s", page_number)
            self._doc.close()
            return

        page = self._doc[page_number]

        # Calculate the scaling factor to fit the image within the target dimensions
        scaling_factor_width = self._settings.final_resolution[1] / page.rect.width
        scaling_factor_height = self._settings.final_resolution[0] / page.rect.height

        # Choose the smaller scaling factor to avoid stretching the image
        scaling_factor = min(scaling_factor_width, scaling_factor_height)

        # Calculate the new dimensions
        new_width = int(page.rect.width * scaling_factor)
        new_height = int(page.rect.height * scaling_factor)

        # Convert the PDF page to an image
        img = page.get_pixmap(matrix=fitz.Matrix(scaling_factor, scaling_factor))

        # Convert the pixmap to a PIL image
        pil_image = Image.frombytes("RGB", [img.width, img.height], img.samples)

        # Resize the image to the desired target dimensions
        resized_image = pil_image.resize((new_width, new_height), Image.ANTIALIAS)

        log.debug("img tmp dir: %s", self._settings.img_dir)
        if not os.path.exists(self._settings.img_dir):
            log.debug("img dir not existent, gets initialized at: %s", self._settings.img_dir)
            os.makedirs(self._settings.img_dir)

        # Save the resized image to the specified output path
        name = self._settings.img_dir + self._name + '_' + str(page_number) + '.png'
        log.debug("saving img at: %s", name)
        resized_image.save(name, "PNG")

        return name
