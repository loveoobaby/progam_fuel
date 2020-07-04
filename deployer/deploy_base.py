
import os, sys
import traceback, time
import paramiko
from progressbar import ProgressBar
from log_monitor import LogMonitor
from deploy_logger import DeployLogger

class DeployBase:

    def __init__(self, config_dict):
        self.config = config_dict
        self.logger = DeployLogger(self.config['name'])

        # init ssh client
        self.client = paramiko.SSHClient()
        self.client.set_missing_host_key_policy(paramiko.AutoAddPolicy())
        self.client.connect(self.config['host'], port=22, username=self.config['username'], password=self.config['password'])
        self.sftp = self.client.open_sftp()
        self.log_monitor = None
        self.url_monitor = None
        self.logger.error('iris will be install @ ' + self.config['host'])

    def config_exists(self, key):
        return self.config.has_key(key) and len(self.config[key]) > 0 and not self.config[key].isspace()

    def localCmd(self, cmd, ignoreError = False):
        self.logger.info('execute local => "' + cmd + '"')
        resultStatus = os.system(cmd)
        if resultStatus != 0 and not ignoreError:
            self.logger.error('command execute failed: ' + cmd)
        else:
            self.logger.info('command execute successful: ' + cmd)


    def sshExec(self, cmd):
        self.logger.info('execute remote "' + cmd + '"')
        return self.client.exec_command(cmd)

    def sshCmd(self, cmd, ignoreError = False):
        stdin, stdout, stderr = self.sshExec(cmd)  # @UnusedVariable
        line = stdout.readline()
        while len(line) > 0:
            self.logger.debug(line, False)
            line = stdout.readline()
        errInfo = stderr.read()
        if len(errInfo) > 0:
            self.logger.error('execute cmd failed => ' + errInfo)
            if not ignoreError:
                sys.exit(1)

    def sshRun(self, cmd):
        stdin, stdout, stderr = self.sshExec(cmd)  # @UnusedVariable
        if len(stderr.read()) > 0:
            sys.exit('execute cmd failed: ' + cmd)
        return stdout.read()

    def upload(self, srcPath, remotePath):
        self.logger.info('scp ' + srcPath + ' --> ' + remotePath)
        package_size = os.path.getsize(srcPath)
        progressbar = ProgressBar(maxval=package_size).start()
        self.sftp.put(srcPath, remotePath, callback=(lambda transfered, total: progressbar.update(transfered)))
        progressbar.finish()

    def monitor_log(self):
        self.log_monitor = None
        def sftp_file_getter(path):
            return self.sftp.file(path)
        try:
            if self.config_exists('path.log'):
                self.log_monitor = LogMonitor(self.config['path.log'], self.config['monitor.duration'], sftp_file_getter, self.logger)
                self.log_monitor.start()
            else:
                self.logger.warn('no path.log specified!')
            return self.log_monitor
        except:
            self.logger.error("monitor log failed: %(path.log)s" % self.config)
            self.logger.error(traceback.format_exc())
            return None

    def monitor_url(self):
        def stop_log_monitor():
            if self.log_monitor is not None:
                self.log_monitor.stop()
        try:
            if self.config_exists('monitor.url'):
                self.url_monitor = UrlMonitor(self.config['monitor.url'], self.config['monitor.duration'], self.logger, stop_log_monitor)
                self.url_monitor.setDaemon(True)
                self.url_monitor.start()
            else:
                self.logger.warn('no monitor.url specified!')
        except:
            self.logger.error("monitor url failed: %(monitor.url)s" % self.config)
            self.logger.error(traceback.format_exc())

    def monitor(self):
        self.monitor_log()
        if self.log_monitor is None and self.url_monitor is None:
            return
        while True:
            try:
                if (self.log_monitor is None or not self.log_monitor.isAlive()) and (self.url_monitor is None or not self.url_monitor.isAlive()):
                    break
                time.sleep(1)
            except KeyboardInterrupt:
                self.logger.warn('stopped manually!')
                if self.log_monitor is not None:
                    self.log_monitor.stop()
                if self.url_monitor is not None:
                    self.url_monitor.stop()
                break

