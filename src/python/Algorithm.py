
from com.example import Algorithm

class SimpleAlgorithm(Algorithm):
    def __init__(self):
        pass

    def processList(self, list):
        print "Received list =",
        print list
        return True

    def processCustomData(self, data):
        print "Received data =",
        print data,
        print data.getData()
        return True

def can_find_xlsxwriter():
    try:
        import xlsxwriter
        return True
    except ImportError:
        return False