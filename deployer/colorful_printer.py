# -*- coding: utf-8 -*-


BLACK, RED, GREEN, YELLOW, BLUE, MAGENTA, CYAN, WHITE = range(30,38)
BG_BLACK, BG_RED, BG_GREEN, BG_YELLOW, BG_BLUE, BG_MAGENTA, BG_CYAN, BG_WHITE = range(40,48)

def coloration(s, color, background_color = 0):
    result = "\033[0;"
    if background_color != 0:
        result += str(background_color) + ";"
    result += str(color) + "m" + str(s) + "\033[0m"
    return result

def colorful_print(msg, color, endline):
    if endline:
        print coloration(msg, color)
    else:
        print coloration(msg, color),

def green(msg, endline = True):
    colorful_print(msg, GREEN, endline)

def red(msg, endline = True):
    colorful_print(msg, RED, endline)

def magenta(msg, endline = True):
    colorful_print(msg, MAGENTA, endline)

def white(msg, endline = True):
    colorful_print(msg, WHITE, endline)
