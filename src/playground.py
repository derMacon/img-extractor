import json
from test.utils.datastructure_utils import *
from tkinter import Tk

test = Tk().clipboard_get()
print(test)

# test = json.loads("""["ctrl","c"]""")
# print(test)
