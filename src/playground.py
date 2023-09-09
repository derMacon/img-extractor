from flask import Flask, render_template
from .business_logic import BusinessLogic  # Import your business logic class

app = Flask(__name__)

@app.route('/')
def index():
    # Create an instance of the BusinessLogic class
    logic = BusinessLogic()
    result = logic.do_something()  # Use the business logic method
    # return render_template('index.html', result=result)
    return "works"
