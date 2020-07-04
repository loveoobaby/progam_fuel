
import colorful_printer as printer

class DeployLogger:

    def __init__(self, module):
        self.module = module

    def message(self, msg, endline = True):
        output = printer.coloration('[', printer.YELLOW) + printer.coloration(self.module, printer.MAGENTA) + printer.coloration('] ', printer.YELLOW) + msg
        if endline:
            print output
        else:
            print output,

    def debug(self, msg, endline = True):
        printer.white(msg, endline)

    def info(self, msg):
        printer.green(msg)

    def warn(self, msg):
        printer.magenta(msg)

    def error(self, msg):
        printer.red(msg)


