# Project: SimpleMultiChat
# File: makefile
# Author: Hurray Zhu
# Time: 2014.10.28
# E-mail: i@ihurray.com
# Web-site: http://blog.ihurray.com


CLASS_PATH   = ./build
SRC = ./src/
# JAR_PATH = ./jar
SRC_FILE        = R Client Server_MainThread Server
Main_enter = Server

define compile 
@for file in $(SRC_FILE); do \
echo javac -classpath $(CLASS_PATH)  -Djava.ext.dirs=$(JAR_PATH) -d $(CLASS_PATH)  $(SRC)$$file.java;\
(javac -classpath $(CLASS_PATH)  -Djava.ext.dirs=$(JAR_PATH) -d $(CLASS_PATH)  $(SRC)$$file.java) || exit; \
done;
endef

all: 
	$(call compile)

clean:
	rm -rf $(CLASS_PATH)/*.class

start:
	java -cp $(CLASS_PATH)/$(Main_enter)
