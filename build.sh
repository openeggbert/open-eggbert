
if [ `whoami` = "robertvokac" ]; then
  export JAVA_HOME=/home/robertvokac/Desktop/jdk-17.0.2/ 
fi

./gradlew build $1