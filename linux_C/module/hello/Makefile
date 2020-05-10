TARGET = helloworld
 
KDIR = /usr/src/kernels/3.10.0-514.el7.x86_64
 
PWD = $(shell pwd)
 
obj-m += $(TARGET).o
 
default:
	make -C $(KDIR) M=$(PWD) modules
