#encoding: utf-8
import os
import sys
import paramiko
import pymysql
import argparse, ConfigParser
import threading

module_config = {}

class SSHClient(object):

    def __init__(self, host, username, password, port=22):
        # init ssh client
        self.client = paramiko.SSHClient()
        self.client.set_missing_host_key_policy(paramiko.AutoAddPolicy())
        self.client.connect(host, port, username=username, password=password)
        self.sftp = self.client.open_sftp()

    def run_cmd(self, cmd, pringError=False):
        stdin, stdout, stderr = self.client.exec_command(cmd)
        line = stdout.readline()
        while len(line) > 0:
            print line
            line = stdout.readline()

        error = stderr.read()

        if len(error) > 0:
            if pringError:
                print error
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


def cleanHost(host):
    sshClient = SSHClient(host, module_config['username'], module_config['password'])

    for i in range(0, 2):

        #清理监控脚本
        sshClient.run_cmd("ps -ef|grep udpClient |grep -v color |awk '{print $2}'|xargs kill -9")
        # su - " +user+ " -c \""+command+"\""
        # 清理zookeeper
        sshClient.run_cmd("ps -ef|grep QuorumPeerMain |grep -v color | grep -v grep |awk '{print $2}'|xargs kill -9")
        sshClient.run_cmd("rm -rf /opt/zookeeper-3.4.12")
        sshClient.run_cmd("sed -i '/^export \(HADOOP_*\|SPARK_*\|SQOOP_*\|PATH=*\|YARN_*\)/d' /etc/profile")
        sshClient.run_cmd("sed -i '$a export PATH=$JAVA_HOME/bin:$PATH' /etc/profile" )
        # 清理HDF   
        sshClient.run_cmd("ps -ef|grep JournalNode |grep -v color | grep -v grep |awk '{print $2}'|xargs kill -9")
        sshClient.run_cmd("ps -ef|grep DFSZKFailoverController |grep -v color | grep -v grep |awk '{print $2}'|xargs kill -9")
        sshClient.run_cmd("ps -ef |grep NameNode |grep -v color | grep -v grep |awk '{print $2}'|xargs kill -9")
        sshClient.run_cmd("ps -ef |grep DataNode |grep -v color | grep -v grep |awk '{print $2}'|xargs kill -9")
        sshClient.run_cmd("rm -rf /opt/hadoop-2.7.4")
        sshClient.run_cmd("rm -rf /tmp/hadoop*")
        sshClient.run_cmd("sed -i '/^export \(HADOOP_*\|SPARK_*\|SQOOP_*\|PATH=*\|YARN_*\)/d' /etc/profile")
        sshClient.run_cmd("sed -i '$a export PATH=$JAVA_HOME/bin:$PATH' /etc/profile" )
        # # 清理Yarn
        sshClient.run_cmd("ps -ef |grep NodeManager |grep -v color | grep -v grep |awk '{print $2}'|xargs kill -9")
        sshClient.run_cmd("ps -ef |grep ResourceManager |grep -v color | grep -v grep |awk '{print $2}'|xargs kill -9")
        sshClient.run_cmd("ps -ef |grep ApplicationHistoryServer |grep -v color | grep -v grep |awk '{print $2}'|xargs kill -9")
        sshClient.run_cmd("rm -rf /opt/hadoop-2.7.4")
        sshClient.run_cmd("sed -i '/^export \(HADOOP_*\|SPARK_*\|SQOOP_*\|PATH=*\|YARN_*\)/d' /etc/profile")
        sshClient.run_cmd("sed -i '$a export PATH=$JAVA_HOME/bin:$PATH' /etc/profile" ) 
        # 清理sqoop
        sshClient.run_cmd("rm -rf /opt/sqoop-1.4.7.bin__hadoop-2.6.0")
        sshClient.run_cmd("sed -i '/^export \(HADOOP_*\|SPARK_*\|SQOOP_*\|PATH=*\)/d' /etc/profile")
        sshClient.run_cmd("sed -i '$a export PATH=$JAVA_HOME/bin:$PATH' /etc/profile")  
        # 清理Hbase
        sshClient.run_cmd("ps -ef |grep HRegionServer |grep -v color | grep -v grep |awk '{print $2}'|xargs kill -9")
        sshClient.run_cmd("ps -ef |grep HMaster |grep -v color | grep -v grep |awk '{print $2}'|xargs kill -9")
        sshClient.run_cmd("rm -rf /opt/hbase-2.*")
        sshClient.run_cmd("sed -i '/^export \(HADOOP_*\|SPARK_*\|SQOOP_*\|PATH=*\)/d' /etc/profile")
        sshClient.run_cmd("sed -i '$a export PATH=$JAVA_HOME/bin:$PATH' /etc/profile" )
        # 清理DataStream
        sshClient.run_cmd("ps -ef|grep DataStreamUi |grep -v color | grep -v grep |awk '{print $2}'|xargs kill -9")
        sshClient.run_cmd("rm -rf /opt/install")
        sshClient.run_cmd("rm -fr /opt/hadoop-2.*")
        sshClient.run_cmd("sed -i '/^export \(HADOOP_*\|SPARK_*\|SQOOP_*\|PATH=*\)/d' /etc/profile")
        sshClient.run_cmd("sed -i '$a export PATH=$JAVA_HOME/bin:$PATH' /etc/profile")  
        # 清理Hive
        sshClient.run_cmd("ps -ef|grep HiveServer2 |grep -v color | grep -v grep |awk '{print $2}'|xargs kill -9")
        sshClient.run_cmd("ps -ef|grep HiveMetaStore |grep -v color | grep -v grep |awk '{print $2}'|xargs kill -9")
        sshClient.run_cmd("rm -rf /opt/hive2.3.3")
        sshClient.run_cmd("sed -i '/^export \(HADOOP_*\|SPARK_*\|SQOOP_*\|PATH=*\|HIVE_*\)/d' /etc/profile")
        sshClient.run_cmd("sed -i '$a export PATH=$JAVA_HOME/bin:$PATH' /etc/profile" ) 
        # 清理kafka
        sshClient.run_cmd("ps -ef|grep Kafka |grep -v color | grep -v grep |awk '{print $2}'|xargs kill -9")
        sshClient.run_cmd("rm -rf /opt/kafka2.12")
        sshClient.run_cmd("rm -rf /tmp/kafka-logs")
        sshClient.run_cmd("sed -i '/^export \(HADOOP_*\|SPARK_*\|SQOOP_*\|PATH=*\|KAFKA_*\)/d' /etc/profile")
        sshClient.run_cmd("sed -i '$a export PATH=$JAVA_HOME/bin:$PATH' /etc/profile" ) 
        # 清理flume
        sshClient.run_cmd("ps -ef|grep flume |grep -v color | grep -v grep |awk '{print $2}'|xargs kill -9")
        sshClient.run_cmd("rm -rf /opt/flume-1.8.0")
        sshClient.run_cmd("sed -i '/^export \(HADOOP_*\|SPARK_*\|SQOOP_*\|PATH=*\|KAFKA_*\)/d' /etc/profile")
        sshClient.run_cmd("sed -i '$a export PATH=$JAVA_HOME/bin:$PATH' /etc/profile")  
        # 清理storm
        sshClient.run_cmd("ps -ef|grep core|grep -v color | grep -v grep |awk '{print $2}'|xargs kill -9")
        sshClient.run_cmd("ps -ef|grep supervisor|grep -v color | grep -v grep |awk '{print $2}'|xargs kill -9")
        sshClient.run_cmd("ps -ef|grep nimbus|grep -v color | grep -v grep |awk '{print $2}'|xargs kill -9")
        sshClient.run_cmd("rm -rf /opt/apache-storm-*")
        sshClient.run_cmd("sed -i '/^export \(HADOOP_*\|SPARK_*\|SQOOP_*\|PATH=*\|KAFKA_*\|STORM_*\)/d' /etc/profile")
        sshClient.run_cmd("sed -i '$a export PATH=$JAVA_HOME/bin:$PATH' /etc/profile") 
        # 清理OOZIE
        sshClient.run_cmd("ps -ef|grep EmbeddedOozieServer |grep -v color | grep -v grep |awk '{print $2}'|xargs kill -9")
        sshClient.run_cmd("rm -rf /opt/oozie-5.1.0")
        sshClient.run_cmd("sed -i '/^export \(OOZIE_*\|PATH=*\)/d' /etc/profile")
        sshClient.run_cmd("sed -i '$a export PATH=$JAVA_HOME/bin:$PATH' /etc/profile")
        #清理OOZIE Client
        sshClient.run_cmd("rm -rf /opt/oozie-client-5.1.0")
        sshClient.run_cmd("sed -i '/^export \(OOZIE_*\|PATH=*\)/d' /etc/profile")
        sshClient.run_cmd("sed -i '$a export PATH=$JAVA_HOME/bin:$PATH' /etc/profile") 
        # 清理zeppelin
        sshClient.run_cmd("ps -ef|grep ZeppelinServer |grep -v color | grep -v grep |awk '{print $2}'|xargs kill -9")
        sshClient.run_cmd("rm -rf /opt/zeppelin-0.8.0-bin-all")
        sshClient.run_cmd("sed -i '/^export \(ZEPPELIN_*\\)/d' /etc/profile" )  
        # 清理spark安装包
        sshClient.run_cmd("rm -rf /opt/spark-2.1.*")
        sshClient.run_cmd("sed -i '/^export \(SPARK_*\\)/d' /etc/profile" ) 
        # 清理filebeat安装包
        sshClient.run_cmd(
            "ps -ef|grep filebeat|grep -v color |awk '{print $2}'|xargs kill -9")
        sshClient.run_cmd("rm -rf /opt/filebeat6.6.1" )  
        # 清理Elasticsearch
        sshClient.run_cmd(
            "ps -ef|grep Elasticsearch |grep -v color |awk '{print $2}'|xargs kill -9")
        sshClient.run_cmd("rm -rf /opt/elasticsearch6.6.1")
        sshClient.run_cmd("sed -i '/^elsearch \(hard*\|soft*\)/d' /etc/security/limits.conf")
        sshClient.run_cmd("sed -i '/vm.max_map_count=262144/d' /etc/sysctl.conf")
        sshClient.run_cmd("sed -i '/currentId.properties/d' /opt/jdk1.8/jre/lib/security/java.policy")

        #清理knowhosts
        sshClient.run_cmd("rm -rf ~/.ssh/known_hosts")

        # 清理服务用户
        print os.path.dirname(os.path.abspath(sys.argv[0])) + "/cleanHost.sh"
        sshClient.sftp.put(os.path.dirname(os.path.abspath(sys.argv[0])) + "/cleanHost.sh", "/tmp/cleanHost.sh")
        sshClient.run_cmd("sh /tmp/cleanHost.sh")

        # 不清理IRIS运行节点的hosts
        if host != module_config['host']:
            sshClient.run_cmd("sed -i '/henghe/d' /etc/hosts")
        
    print 'clean host ', host, ' finished!'
    sshClient.close()


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

    
    for config_item in config.items(module_name):
        module_config[config_item[0]] = config_item[1]

    host_list = module_config['cluster_hosts'].split(",")

    # 清除数据库
    db_user = module_config['iris_db_user']
    db_passwd = module_config['iris_db_password']
    db_url = module_config['iris_db_url']
    charSet = "utf8mb4"  # Character set
    connection = pymysql.connect(host=db_url, user=db_user, password=db_passwd, db='henghe_iris', cursorclass=pymysql.cursors.DictCursor)

    sqls=["delete from host_index",
          "delete from host_index_m",
          "delete from hadoop_index",
          "delete from hadoop_index_m",
          "delete from yarn_index",
          "delete from yarn_index_m",
          "delete from hbase_index",
          "delete from hbase_index_m",
          "delete from zk_index",
          "delete from zk_index_m",
          "delete from kafka_index",
          "delete from kafka_index_m",
          "delete from flume_index",
          "delete from flume_index_m",
          "delete from storm_index",
          "delete from storm_index_m",
          "delete from host_index_aggregation",
          "truncate t_action_task",
          "truncate t_cluster",
          "truncate t_scan_result",
          "truncate t_lock",
          "truncate t_resource_pool",
          "truncate t_install_service",
          "truncate t_progress",
          "truncate other_cluster_config",
          "truncate service_widget",
          "truncate flume_agent",
          "truncate storm_topology_index",
          "truncate hive_sql",
          "truncate merge_mark",
          ]

    try:
        dbCursor = connection.cursor()
        for sql in sqls:
            dbCursor.execute(sql)
            print 'executor sql: ', sql
    except Exception as e:
        print("Exeception occured:{}".format(e))
    finally:
        connection.close()

    # 清理hive数据库
    hive_db_user = module_config['hive_db_user']
    hive_db_passwd = module_config['hive_db_password']
    hive_db_url = module_config['hive_db_url']
    hive_db_name = module_config['hive_db_name']
    charSet = "utf8mb4"  # Character set
    print hive_db_user, hive_db_passwd, hive_db_url, hive_db_name
    connection = pymysql.connect(host=hive_db_url, user=hive_db_user, password=hive_db_passwd,  cursorclass=pymysql.cursors.DictCursor)

    sqls=["drop database " + hive_db_name,
          "create database " + hive_db_name]

    try:
        dbCursor = connection.cursor()
        for sql in sqls:
            dbCursor.execute(sql)
            print 'executor sql: ', sql
    except Exception as e:
        print("Exeception occured:{}".format(e))
    finally:
        connection.close()

    for host in host_list:
        print 'clean ', host
        thread2 = threading.Thread(target=cleanHost, args=(host,))
        # thread2.setDaemon(True)
        thread2.start()

    # 重启IRIS
    sshClient = SSHClient(module_config['host'], module_config['username'], module_config['password'])
    sshClient.run_cmd("ps aux|grep henghe-0.0.1-SNAPSHOT.jar | grep -v 'grep'| awk '{print $2}' | xargs -l kill -9");
    sshClient.run_cmd('source /etc/profile;cd ~/iris_package;nohup java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/root/iris.dump -jar henghe-0.0.1-SNAPSHOT.jar 1>/dev/null 2>/dev/null &')
    sshClient.close()