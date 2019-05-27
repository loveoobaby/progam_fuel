# 1. 下载最新的内核源码

可以去国内ftp下载：<http://ftp.sjtu.edu.cn/sites/ftp.kernel.org/pub/linux/kernel/v5.x/>

下载完成后解压即可



# 2. 安装GCC编译工具

```shell
sudo apt-get install build-essential libncurses-dev bison flex libssl-dev libelf-dev
```



# 3. 配置内核编译选项

目前内核的配置命令支持一下三种：

+ make menuconfig： 出现基于文本的颜色菜单、对话框、单选按钮，这种模式在远程shell中仍然可用，对于远程编译非常友好。
+ make xconfig：基于X window的配置工具，在KDE桌面环境下工作较好；
+ make gconfig：也是基于X window的配置工具，在gnome桌面环境下使用较好；

配置完成后在源码根目录会生成.config文件



# 4. 编译内核

```shell
make -j $(nproc)
```



# 5. 安装内核模块

```shell
make modules_install
```



# 6. 安装内核镜像

```shell
sudo make install
```

# 7. 重启系统，查看内核

```shell
sudo reboot

# 重新启动后查看内核版本
$uname -r
5.1.15
```







