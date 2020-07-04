#encoding: utf-8
import os
import sys
import paramiko
import argparse
import io

class SSHClient(object):

    def __init__(self, host, username, password, port=22):
        # init ssh client
        self.client = paramiko.SSHClient()
        self.client.set_missing_host_key_policy(paramiko.AutoAddPolicy())
        self.client.connect(host, port, username=username, password=password)
        self.sftp = self.client.open_sftp()

    def upload(self, local, remote):
        try:
            self.sftp.put(local, remote)
        except Exception,e:
            print 'upload files failed:',e
            self.sftp.close()
            self.client.close()

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

def alterFile(file,old_str,new_str):
    """
    替换文件中的字符串
    :param file:文件名
    :param old_str:就字符串
    :param new_str:新字符串
    :return:
    """
    file_data = ""
    with io.open(file, "r", encoding="utf-8") as f:
        for line in f:
            if old_str in line:
                line = line.replace(old_str,new_str)
            file_data += line
    with io.open(file,"w",encoding="utf-8") as f:
        f.write(file_data)



parser = argparse.ArgumentParser()
parser.add_argument("-r", "--remote host",
        dest="host")
parser.add_argument("-u", "--remote user",
        dest="user")
parser.add_argument("-p", "--remote password",
        dest="password")

# python ConfigChrony.py -r henghe-111 -u root -p 'iris!@#$'

if __name__ == '__main__':

    args = parser.parse_args()
    host = args.host
    username = args.user
    password = args.password

    os.system('cp ../../henghe-iris-api/src/main/resources/template/chrony/chrony.conf /tmp')

    sshClient = SSHClient(host, username, password)

    #修改配置
    alterFile("/tmp/chrony.conf", "${master_host}", host)
    #上传文件
    sshClient.upload('/tmp/chrony.conf', '/etc/chrony.conf')
    #重启服务
    sshClient.run_cmd('systemctl restart chronyd')
    sshClient.close()


