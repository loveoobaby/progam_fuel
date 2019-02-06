## 1. YARN的编程组件

1. ResourceManager： 是一个纯粹的调度器，根据应用程序的资源请求严格限制系统的可用资源。在保证容量、公平性及服务等级的前提下，优化集群资源的利用率。为了使用不同的策略，RM有一个可插拔的资源调度器来应用不同的调度算法

2. ApplicationMaster：时间上是特定框架的实例，负责与ResourceMAnager协商资源，并和NodeManager协同工作来执行和监控的Container以及它们的资源消耗。它有责任与ResourceManager协商并获取合适的资源Container，跟踪它们的状态，以及监控其进展。其特性有：

   - 将原先JobTracker容错、状态追踪等功能移入AM，RM成为一个纯粹的资源管理器，将复杂性引入了AM
   - AM本质是还是客户端代码，因此不能信息，不能是一个特权服务
   - 扩展性、开放性高，每种应用需开发自己的ApplicationMaster

3. ResourceRequest

   一个应用程序通过AM请求特定资源来满足他的资源需求。ResourceRequest包括以下信息：

    	1.  资源名称：期望资源所在的主机、机架名，用*表示没有特殊需求
    	2.  优先级：应用程序内部的优先级，用于调整内部资源申请顺序
    	3.  资源需求：内存多少、CPU多少
    	4.  Container数量

4. Container

​         Container的启动API是与平台无关的，包含以下元素：

​               1.启动Container的命令行

​               2.环境变量

​               3.启动Container进程所需的本地资源文件

​               4.安全相关的令牌

5. LocalResource：代表了运行Container所需的资源文件，包含以下信息：

   1. URL：资源的下载地址，一般放在HDFS；
   2. Size：资源的大小；
   3. Creation TimeStamp：资源的时间戳，用于检查文件是否被修改过，保证各个Container的资源的一致性视图
   4. LocalResourceType：包括FILE普通文件，ARCHIVE压缩文件，PATTERN前两种的组合；
   5. LocalResourceVisibility：指定本地化可见性

   

## 2. YARN的三种调度器

1. FIFO: 从工作队列中拉去最早提交的任务进行调度，不考虑作用的优先级和范围

2. Capacity：管理员需配置一个或多个队列，每个队列有严格的ACL限制，用于控制哪些用户可以向该队列提交应用。Capacity调度器允许共享集群，同时给每个用户或组一定的最小容量保证。这些最小值在不需要是可以放弃，超出的容量将会给那些处于饥饿的队列。

   用队列预留资源的方式保证了小任务提交即可执行，同时延长了大任务的执行时间。

3. Fair：不需要预留一系列最小容量值，它可以动态调整各个job的资源量。当只有一个 job 在运行时，该应用程序最多可获取所有资源，再提交其他 job 时，资源将会被重新分配分配给目前的 job，这可以让大量 job 在合理的时间内完成，减少作业 pending 的情况。

![](./picture/FIFO.png)



## 3. YARN APP运行流程



## 4. YARN运维相关

+ 日志聚合配置：

​        yarn-site.xml中yarn.log-aggregation-enable设为true；

​        yarn.nodemanager.remote-app-log-dir设置日志聚合HDFS目录，默认/tmp/logs; 日志聚合目录要有相关的权限

+ 增加或关闭YARN节点

  yarn.resourcemanager.nodes.include-path: 指定RM接受节点列表

  yarn.resourcemanager.nodes.exclude-path: 不会接受的节点列表；

  yarn rmadmin -refreshNodes   #通知RM刷新节点列表

+ YARN的web代理

  yarn的web代理是独立的代理服务器，出于安全性考虑，它集中管理者AM的web界面。默认情况下作为RM的一部分运行着，可以通过修改配置yarn.web-proxy.address让它以独立方式运行

+ 更新用户到用户组的映射

  配置项hadoop.security.group.mapping决定了RM中使用的用户与用户组的映射，默认是ShellBasedUnixGroupsMapping。无论何时向系统添加一个用户或修改用户组的用户列表，都需要更新：

  yarn rmadmin -refreshUserToGroupsMapping

+ 





