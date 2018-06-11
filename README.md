git clone https://github.com/hyperledger/fabric-sdk-java.git


 mvn failsafe:integration-test -DskipITs=false    

mvn assembly:assembly   本来用这个方法可以把所有的包合成一个包，但是在最新的版本中好像不能用了，有待继续研究

mvn dependency:copy-dependencies    用这个方法可以自动提取依赖包

最终成功的方法 


mvn package  打开sdk包 ，然后拷贝到相关目录

mvn dependency:copy-dependencies   copy 依赖包  

在 	VM_OPTION	中设置下面的参数

系统启动的时候通过 -Dorg.hyperledger.fabric.sdk.configuration=/project/javaworkspace/fabric-sdk-java-sample/src/org/hyperledger/fabric/sdk/configuration/config.properties  的方式来指定配置文件




