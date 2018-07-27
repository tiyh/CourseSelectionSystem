## 简介
基于SpringBoot+SpringMVC+Druid+Hibernate的简单选课系统  
基于Spring Security+JWT 实现 RESTful API 权限控制  
基于Elasticsearch实现简单全文课程搜索　　
基于Spring-cache和Redis的缓存

## 技术栈
 SpringBoot+SpringMVC+druid+Hibernate+Redis+SpringSecurity+JWT 
## 编译运行　　
mvn springboot:run
### MySQL&Redis配置：  
src/main/resources/application.properties  
spring.datasource.url  
spring.datasource.username  
spring.datasource.password  
spring.redis.host  
spring.redis.password  
spring.redis.port  
### Elasticsearch环境搭建
* 安装Elasticsearch:  
 >1.wget https://download.elastic.co/elasticsearch/release/org/elasticsearch/distribution/tar/elasticsearch/2.4.4/elasticsearch-2.4.4.tar.gz  
 2.tar -xzvf elasticsearch-2.4.4.tar.gz -C /your/own/path  
 3.vi config/elasticsearch.yml :  
network.host: 101.37.*.25,10.29.*.58,127.0.0.1  
cluster.name: my-application

* 安装logstash和logstash-input-jdbc插件　同步MySQL与Elasticsearch
https://www.elastic.co/downloads/past-releases
安装logstash-input-jdbc
>1.安装 ruby 和 rubygems（注意：需要 ruby 的版本在 1.8.7 以上）
cd /usr/share/
vim Gemfile
修改 source 的值 为： "https://gems.ruby-china.org/"
vim  Gemfile.jruby-1.9.lock 
找到 remote 修改它的值为:https://gems.ruby-china.org/  
2./bin/logstash-plugin install --no-verify  logstash-input-jdbc  
3.写配置文件开始同步 vi xxx.conf [配置文件](other-conf-file/logstash.conf)

* 安装ik分词器:  
>下载ik分词器解压到elasticsearch/plugins/ik 
https://github.com/medcl/elasticsearch-analysis-ik  
在线测试分词器：
http://127.0.0.1:9200/_analyze?analyzer=ik&pretty=true&text=sojson在线工具  

* 启动Elasticsearch:  
>./bin/elasticsearch -Des.insecure.allow.root=true  
或者使用后台方式进行启动./bin/elasticsearch -d -Des.insecure.allow.root=true   

* 启动 logstash:  
>bin/logstash -f xxx.conf  
查看　elsatic logstash head
http://localhost:9200/_plugin/head/

## 另外
此项目有配套的前端系统：[前端项目传送地址](https://github.com/tiyh/course-selection-vue)
