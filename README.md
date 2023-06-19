# 大数据学习笔记

## 一、Hadoop

### 1.1 概念

Hadoop是一个适合海量数据的分布式存储和分布式计算的框架。

所以目前Hadoop发行版非常的多，有华为发行版、Intel发行版、Cloudera发行版CDH、Hortonworks发行版HDP，这些发行版都是基于Apache Hadoop衍生出来的。

CDH是一个商业版本，它对官方版本做了一些优化，提供收费技术支持，提供界面操作，方便集群运维管理
CDH目前在企业中使用的还是比较多的，虽然CDH是收费的，但是CDH中的一些基本功能是不收费的，可以一直使用，高级功能是需要收费才能使用的，如果不想付费，也能凑合着使用。

![图片描述](https://damon-study.oss-cn-shenzhen.aliyuncs.com/%20typora/%E5%B9%B6%E5%8F%91%E7%BC%96%E7%A8%8B5f3f6b23098fb2d518420589.jpg)



Hadoop3.x的细节优化

- 最低Java版本要求从Java7变为Java8
- 在Hadoop 3中，HDFS支持纠删码，纠删码是一种比副本存储更节省存储空间的数据持久化存储方法，使用这种方法，相同容错的情况下可以比之前节省一半的存储空间
- Hadoop 2中的HDFS最多支持两个NameNode，一主一备，而Hadoop 3中的HDFS支持多个NameNode，一主多备
- MapReduce任务级本地优化，MapReduce添加了映射输出收集器的本地化实现的支持。对于密集型的洗牌操作（shuffle-intensive）jobs，可以带来30%的性能提升
- 修改了多重服务的默认端口，Hadoop2中一些服务的端口和Hadoop3中是不一样的



Hadoop主要包含三大组件：HDFS+MapReduce+YARN

- HDFS负责海量数据的分布式存储
- MapReduce是一个计算模型，负责海量数据的分布式计算
- YARN主要负责集群资源的管理和调度

### 1.2 HDFS



### 1.3 Mapreduce



### 1.4 YARN



### 1.5 总结



### 1.6 案例

## 二、Fiume



## 三、Hive

## 