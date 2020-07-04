#!/bin/bash

userdel -r -f zookeeper
userdel -r -f hive
userdel -r -f flume
userdel -r -f yarn
userdel -r -f hdfs
userdel -r -f hbase
userdel -r -f sqoop1  
userdel -r -f datastream  
userdel -r -f kafka  
userdel -r -f storm  
userdel -r -f oozie  
userdel -r -f flink  
userdel -r -f zeppelin  
userdel -r -f elsearch  

groupdel zookeeper
groupdel hive
groupdel flume
groupdel yarn
groupdel hdfs
groupdel hbase
groupdel sqoop1
groupdel datastream  
groupdel kafka  
groupdel storm  
groupdel oozie  
groupdel flink  
groupdel zeppelin  
groupdel elsearch

rm -rf /tmp/hsperfdata_zookeeper 
rm -rf /tmp/hsperfdata_hive 
rm -rf /tmp/hsperfdata_flume 
rm -rf /tmp/hsperfdata_yarn 
rm -rf /tmp/hsperfdata_hdfs 
rm -rf /tmp/hsperfdata_hbase 
rm -rf /tmp/hsperfdata_sqoop1 
rm -rf /tmp/hsperfdata_datastream 
rm -rf /tmp/hsperfdata_kafka 
rm -rf /tmp/hsperfdata_storm 
rm -rf /tmp/hsperfdata_oozie 
rm -rf /tmp/hsperfdata_zeppelin 
rm -rf /tmp/hsperfdata_elsearch 