
#coding=utf8

import os, sys
import argparse, ConfigParser, commands
from iris_deployer import IirsDeployer

DEFAULT = 'default'
TASK_DELIMITER = '@'

parser = argparse.ArgumentParser()
parser.add_argument("-m", "--module",
        dest="modules", action="append", metavar="module" + TASK_DELIMITER + "task",
        help="目标模块名，即配置文件里方括号里的字，可以指定多个，如：-m xxx -m yyy。模块名可以接任务名，如：-m xxx" + TASK_DELIMITER + "restart。任务可以是：deploy（默认任务）、restart，不指定时执行deploy任务")


parser.add_argument("-p", "--package", help="is all package", action="store_true")

if __name__ == '__main__':

    args = parser.parse_args()
    root_project_path = os.path.dirname(os.path.dirname(os.path.dirname(os.path.abspath(sys.argv[0]))))

    config = ConfigParser.ConfigParser()
    config.read(os.path.join(root_project_path, 'script', 'deployer', 'settings.ini'))


    # prepare module task
    target_module_task = {}
    for arg_module in args.modules:
        idx = arg_module.rfind(TASK_DELIMITER)
        module = arg_module
        task = 'deploy'
        if idx > 0:
            module = arg_module[0:idx]
            task = arg_module[idx+1:]
        target_module_task[module] = task




    # prepare module config
    default_config = {}
    default_config_items = config.items(DEFAULT)
    for config_item in default_config_items:
        default_config[config_item[0]] = config_item[1]
    defined_modules = config.sections()
    modules_config_dict = {}
    for module_name in target_module_task.keys():
        if module_name not in defined_modules:
            print '[%s] not defined in %s' % (module_name, args.settings)
            continue
        module_config = default_config.copy()
        for config_item in config.items(module_name):
            module_config[config_item[0]] = config_item[1]
        module_config['name'] = module_name
        module_config['root'] = root_project_path
        module_config['task'] = target_module_task[module_name]
        module_config['package'] = args.package
        modules_config_dict[module_name] = module_config

    # run
    for module_name in modules_config_dict.keys():
        module_config = modules_config_dict[module_name]
        module_deployer = IirsDeployer(module_config)
        module_deployer.execute()


