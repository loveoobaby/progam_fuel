
import traceback, datetime, os
from deploy_base import DeployBase
from history_recorder import HistoryRecorder

class IirsDeployer(DeployBase):

    def __init__(self, config_dict):
        DeployBase.__init__(self, config_dict)

    def replace_jar(self):
        # self.sshCmd('rm -rf ~/iris_package')
        self.sshCmd('tar -xvmf /tmp/iris.tar -C ~')

    def start_jar(self):
        self.sshCmd('source /etc/profile;cd ~/iris_package;nohup java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/root/iris.dump -jar henghe-0.0.1-SNAPSHOT.jar 1>/dev/null 2>/dev/null &')
        self.logger.info('==> starting jar ...')


    def stop_iris(self):
        try:
            self.sshRun("ps aux|grep henghe-0.0.1-SNAPSHOT.jar | grep -v 'grep'| awk '{print $2}' | xargs -l kill -9")
        except:
            self.logger.warn("remote iris server is not started.")

    def restart(self):
        self.stop_iris()
        self.start_jar()
        self.monitor()

    def package(self):
        # copy udpClient.py
        os.chdir(self.config['root'])
        cmd = 'cp ./script/udpClient.py ./henghe-iris-api/sh'
        self.localCmd(cmd)

        web_path = os.path.join(self.config['root'], 'henghe-iris-web-v2')
        os.chdir(web_path)

        cmd = 'npm run build'
        self.localCmd(cmd)
        cmd = 'mkdir -p ../henghe-iris-api/src/main/resources/static'
        self.localCmd(cmd)
        cmd = 'rm -rf ../henghe-iris-api/src/main/resources/static/*'
        self.localCmd(cmd)
        cmd = 'cp -r dist/* ../henghe-iris-api/src/main/resources/static'
        self.localCmd(cmd)

        os.chdir(os.path.join(self.config['root'], 'henghe-iris-sink'))
        self.localCmd('mvn clean package -DskipTests')
        self.localCmd('cp ./target/hadoop-metrics-iris-2.7.4.jar ../henghe-iris-api/package')

        os.chdir(os.path.join(self.config['root'], 'henghe-iris-api'))
        cmd = "mvn clean install package -U -Dmaven.test.skip=true"
        self.localCmd(cmd)
        self.localCmd('rm -rf /tmp/iris_package')
        self.localCmd('mkdir /tmp/iris_package')

        self.localCmd('cp ./target/henghe-0.0.1-SNAPSHOT.jar /tmp/iris_package')
        if self.config['package']:
            self.localCmd('cp -r ./package /tmp/iris_package')
        self.localCmd('cp -r ./sh /tmp/iris_package')
        self.localCmd('mkdir /tmp/iris_package/config')
        self.localCmd('cp -r ./config/' + self.config['profile'] +  '/* /tmp/iris_package/config')
        self.localCmd('tar -cvf /tmp/iris.tar -C /tmp iris_package/')

        self.localCmd('cp ../script/henghe_iris.sql /tmp/iris_package')


    def deploy(self):
        try:
            self.package()
            self.upload('/tmp/iris.tar', '/tmp/iris.tar')
            self.stop_iris()
            self.replace_jar()
            self.start_jar()
            self.monitor()
        except:
            self.logger.error(traceback.format_exc())


    def execute(self):
        task =self.config['task']
        if task == 'deploy':
            self.deploy()
        elif task == 'restart':
            self.restart()
        else:
            self.logger.error('task:%s is illegal, run deploy' % (task))
            self.deploy()

