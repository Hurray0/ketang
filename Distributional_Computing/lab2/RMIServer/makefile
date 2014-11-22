# Project: RMIServer
# File: makefile
# Author: Hurray Zhu
# Time: 2014.11.23
# E-mail: i@ihurray.com
# Web-site: http://www.ihurray.com


CLASS_PATH   = ./build
# JAR_PATH = ./build
SRC = ./src/
SRC_FILE  = *
Main_enter = RMIServer

# define compile 
# @for file in $(SRC_FILE); do \
# echo javac -classpath $(CLASS_PATH)  -Djava.ext.dirs=$(JAR_PATH) -d $(CLASS_PATH)  $(SRC)$$file.java;\
# (javac -classpath $(CLASS_PATH)  -Djava.ext.dirs=$(JAR_PATH) -d $(CLASS_PATH)  $(SRC)$$file.java) || exit; \
# done;
# endef

all: 
	rm -rf build
	mkdir build
	cd src && javac -d ../build *.java
	cp bat\&bash/* build

clean:
	rm -rf build

run:
	cd build && bash runServer.sh

server:
	cd build && bash runServer.sh

client:
	cd build && java RMIClient

register:
	cd build && bash register.sh

add:
	cd build && bash add.sh

query:
	cd build && bash query.sh

delete:
	cd build && bash delete.sh

clear:
	cd build && bash clear.sh