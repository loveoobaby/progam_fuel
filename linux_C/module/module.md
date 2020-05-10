### 1. linux内核源码下载

 https://mirrors.edge.kernel.org/pub/linux/kernel/v3.x/

 ### 2. 单文件模块
 1. 写hello.c
 ```C
#include <linux/module.h>        /* Needed by all modules */
#include <linux/kernel.h>          /* Needed for KERN_INFO */
 
int init_module(void)
{
    printk(KERN_INFO “Hello World!\n”);
    return 0;
}
 
void cleanup_module(void)
{
    printk(KERN_INFO “Goodbye!\n”);
}
 
MODULE_LICENSE(“GPL”);
 ```

 2. 写makefile, 编译生成helloworld.ko
 ```makefile
TARGET = helloworld
 
KDIR = /usr/src/kernels/3.10.0-514.el7.x86_64
 
PWD = $(shell pwd)
 
obj-m += $(TARGET).o
 
default:
       make -C $(KDIR) M=$(PWD) modules
 ```
然后简单输入make即可生成内核模块文件elloworld.ko


 3. 加载删除模块
 ```shell
insmod helloworld.ko //加载模块
lsmod |grep hello    // 查看模块
rmmod helloworld     // 删除模块
 ```

使用dmesg可以查看内核日志

屏幕上没有打印的消息全在：/var/log/message

### 3. 内核模块多文件






