#encoding: utf-8
import os
import sys
import paramiko
import argparse, ConfigParser

class SSHClient(object):

    def __init__(self, host, username, password, port=22):
        # init ssh client
        self.client = paramiko.SSHClient()
        self.client.set_missing_host_key_policy(paramiko.AutoAddPolicy())
        self.client.connect(host, port, username=username, password=password)
        self.sftp = self.client.open_sftp()

    def run_cmd(self, cmd):
        stdin, stdout, stderr = self.client.exec_command(cmd)
        line = stdout.readline()
        while len(line) > 0:
            print line
            line = stdout.readline()

        error = stderr.read()
        if len(error) > 0:
            # print str(error)
            return False
        else:
            return True

    def close(self):
        self.sftp.close()
        self.client.close()



parser = argparse.ArgumentParser()
parser.add_argument("-m", "--module",
        dest="module",
        help="目标模块名，即配置文件里方括号里的字")


if __name__ == '__main__':

    args = parser.parse_args()
    root_project_path = os.path.dirname(os.path.dirname(os.path.dirname(os.path.abspath(sys.argv[0]))))

    config = ConfigParser.ConfigParser()
    config.read(os.path.join(root_project_path, 'script', 'deployer', 'settings.ini'))

    module_name = args.module
    defined_modules = config.sections()

    if module_name not in defined_modules:
        print '[%s] not defined in %s' % (module_name, defined_modules)
        sys.exit(-1)
    module_config = {}
    for config_item in config.items(module_name):
        module_config[config_item[0]] = config_item[1]

    host_list = module_config['cluster_hosts'].split(",")
    for host in host_list:
        sshClient = SSHClient(host, module_config['username'], module_config['password'])
        print '------------------',host,'系统配置---------------------'
        # 关闭、禁用防火墙
    	sshClient.run_cmd("systemctl disable firewalld")
        print "关闭防火墙"
    	#设置SELinux 成为permissive模式,
    	#永久禁用，修改/etc/selinux/config
        print "禁用selinux"
    	sshClient.run_cmd("sed -i 's/SELINUX\\=enforcing/SELINUX\\=disabled/g' /etc/selinux/config")
    	#设置时区
    	sshClient.run_cmd("timedatectl set-timezone Asia/Shanghai")
    	#禁用交换分区
        print "禁用交换分区:echo 'vm.swappiness=10' >>/etc/sysctl.conf"
    	sshClient.run_cmd("echo 'vm.swappiness=10' >>/etc/sysctl.conf")

    	#设置hostname
        print "设置hostname:",host
    	sshClient.run_cmd("sed -i '1d' /etc/hostname");
    	sshClient.run_cmd("echo '"+host+"' > /etc/hostname")
    	#文件句柄修改
        print "文件句柄修改：65536"
    	sshClient.run_cmd("sed -i '/^* soft  nofile.*/d' /etc/security/limits.conf")
    	sshClient.run_cmd("sed -i '/^* hard  nofile.*/d' /etc/security/limits.conf")
    	## 加上限制
    	sshClient.run_cmd("sed -i '$a\\*  hard nofile  65536' /etc/security/limits.conf")
    	sshClient.run_cmd("sed -i '$a\\*  soft nofile  65536' /etc/security/limits.conf")
        sshClient.run_cmd("shutdown -r now")

        sshClient.close();
        print host,'系统关闭&重启.........................'
