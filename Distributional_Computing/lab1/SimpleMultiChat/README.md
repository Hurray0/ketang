#SimpleMultiChat-简单的群聊聊天室
##作者信息
```
Project: SimpleMultiChat
Author: Hurray Zhu
Time: 2014.10.28
E-mail: i@ihurray.com
Web-site: http://blog.ihurray.com
GitHub: https://github.com/Hurray0/ketang/tree/master/Distributional_Computing/lab1
```
##文件目录
```
.
|
|____build
| |____Client$ClientThreadInput.class
| |____Client$ClientThreadOutput.class
| |____Client.class
| |____R.class
| |____Server.class
| |____Server_MainThread.class
|____build.xml
|____jar
|____makefile
|____README.md
|____src
| |____Client.java
| |____R.java
| |____Server.java
| |____Server_MainThread.java
```

##编码环境
* MacOS X

* 命令行编译运行

##运行环境及方法
###编译环境
* *unix(含MacOS) 使用makefile/Ant编译
* Windows使用Ant编译
* 或者按照R.java Client.java Server_MainThread.java Server.java的顺序依次编译src文件夹的文件，然后java命令运行

###例图
####makefile编译
![](jietu/makefile.png)

####ant编译
![](jietu/ant.png)

####运行服务器
![](jietu/runserver.png)

####运行客户端
![](jietu/runclient.png)

##运行效果
####服务器接收用户登录
![](jietu/jieshoudenglu.png)

####用户发消息时服务器及客户端反应
![](jietu/faxiaoxi.png)

####群聊效果
![](jietu/qunliao.png)