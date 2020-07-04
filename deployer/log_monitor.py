
import threading, time

class LogMonitor(threading.Thread):
    def __init__(self, log_path, monitor_duration, log_file_getter, logger):
        threading.Thread.__init__(self)
        self.log_path = log_path
        self.duration = int(monitor_duration)
        self.log_file_getter = log_file_getter
        self.logger = logger
        self.terminated = False

    def run(self):
        tried = 0
        log_file = None
        self.start_time = time.time()
        while True:
            if self.should_stop():
                break
            try:
                tried += 1
                log_file = self.log_file_getter(self.log_path)
                if tried == 1:
                    log_file.seek(0,2)
                break
            except IOError:
                if self.should_stop():
                    self.logger.error('no such log file:' + self.log_path)
                    break
                self.logger.warn('no log file yet, wait for it 1 second.')
                time.sleep(1)

        if log_file is None:
            return
        self.logger.info('monitor log:' + self.log_path)
        line = log_file.readline()
        while True:
            if self.should_stop():
                break
            if len(line) == 0:
                time.sleep(1)
            else:
                print line,
            line = log_file.readline()

    def stop(self):
        self.terminated = True

    def should_stop(self):
        return self.terminated or (time.time() - self.start_time >= self.duration)
