## 1. YARN的组件

1. ResourceManager： 是一个纯粹的调度器，根据应用程序的资源请求严格限制系统的可用资源。在保证容量、公平性及服务等级的前提下，优化集群资源的利用率。为了使用不同的策略，RM有一个可插拔的资源调度器来应用不同的调度算法
2. ApplicationMaster：时间上是特定框架的实例，负责与ResourceMAnager协商资源，并和NodeManager协同工作来执行和监控的Container以及它们的资源消耗。它有责任与ResourceManager协商并获取合适的资源Container，跟踪它们的状态，以及监控其进展。其特性有：
   - 