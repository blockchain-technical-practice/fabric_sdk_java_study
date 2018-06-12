# 项目简介

本项目是区块链开发实战丛书 《区块链开发实战 Hyperledger Fabric关键技术与案例分析》 第八章的部分的相关代码

## 本书介绍

《区块链开发实战 Hyperledger Fabric关键技术与案例分析》 

<a href="https://search.jd.com/Search?keyword=区块链开发实战&enc=utf-8&wq=区块链开发实战&pvid=62e6789cc6ca42ed967de131df18fae8"><img width="400" height="600" src="https://github.com/blockchain-technical-practice/fabric_sdk_node_study/raw/master/pic/%E5%8C%BA%E5%9D%97%E9%93%BE%E6%8A%80%E6%9C%AF%E5%AE%9E%E6%88%98-fabric.jpeg"/></a>


如果您觉得本丛书的代码对您有帮助请购买本正版图书，谢谢。

[购买链接](https://search.jd.com/Search?keyword=区块链开发实战&enc=utf-8&wq=区块链开发实战&pvid=62e6789cc6ca42ed967de131df18fae8)

## 问题反馈，

为了帮助读者更好的阅读本书，我们跟中国最大的区块链技术社区区块链兄弟合作，在区块链兄弟上面开设了专栏。如果您在阅读本丛书的过程遇到的问题，可以通过以下连接提交问题

<a href="http://www.blockchainbrother.com/questions/blockchain-technical-practice">点击，提交阅读本书的过程中遇到的问题</a>


## 丛书介绍

<a href="https://github.com/blockchain-technical-practice/blockchain-technical-practice-doc/wiki/Home/_edit">区块链开发实战系列丛书简介</a>

## 部署过程

```
git clone https://github.com/hyperledger/fabric-sdk-java.git

mvn failsafe:integration-test -DskipITs=false    

mvn assembly:assembly    -- 本来用这个方法可以把所有的包合成一个包，但是在最新的版本中好像不能用了，有待继续研究

mvn dependency:copy-dependencies    用这个方法可以自动提取依赖包

```

由于maven的版本的问题，上面的方法可能会不成功，这个是可以用下面的流程试试看

```
mvn package  打开sdk包 ，然后拷贝到相关目录

mvn dependency:copy-dependencies   

copy 依赖包到项目的classpath路径下面

```

系统启动的时候通过 -Dorg.hyperledger.fabric.sdk.configuration=/project/javaworkspace/fabric-sdk-java-sample/src/org/hyperledger/fabric/sdk/configuration/config.properties  的方式来指定配置文件


具体的内容可以参考本书第160，161页相关的内容。


