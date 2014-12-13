mkdir build
cd src
idlj -td ../build/ -fall *.idl
javac -cp ../build/ -d ../build/ *.java