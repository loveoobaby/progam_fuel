
import os, datetime, getpass, socket

class SysInfo:
    def __init__(self):
        self.user = getpass.getuser()
        self.hostname = socket.gethostname()
        ip_lines = os.popen("ifconfig -a | grep inet | awk -F ' ' '{print $2}' | grep '192.168'")
        self.ip = ''
        for line in ip_lines:
            if len(self.ip) > 0:
                self.ip += ','
            self.ip += line.strip()

    @staticmethod
    def get_current_time():
        return datetime.datetime.now().strftime("%Y/%m/%d_%H:%M:%S")

