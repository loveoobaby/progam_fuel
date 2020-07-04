
from sysinfo import SysInfo

class HistoryRecorder(SysInfo):
    def __init__(self, log_file):
        SysInfo.__init__(self);
        self.history_file = log_file

    def do_record(self, info):
        self.history_file.write(info + '\n')
        self.history_file.flush()

    def record_deploy(self, project):
        self.do_record('[%s] from [%s(%s)] deployed [%s] at [%s]' % (self.user, self.hostname, self.ip, project, self.get_current_time()))

    def record_rollback(self, target):
        self.do_record('[%s] from [%s(%s)] rolled back [%s] at [%s]' % (self.user, self.hostname, self.ip, target, self.get_current_time()))

    def record_restart(self, project):
        self.do_record('[%s] from [%s(%s)] restarted [%s] at [%s]' % (self.user, self.hostname, self.ip, project, self.get_current_time()))
