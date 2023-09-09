class MockService:

    def __init__(self):
        self.counter = 0

    def print_self(self):
        self.counter += 1
        print(self.counter)
