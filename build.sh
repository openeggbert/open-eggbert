mkdir dist
rm dist/openeggbert.jar
mvn clean install
mv target/open-eggbert-0.0.0-SNAPSHOT-jar-with-dependencies.jar dist/openeggbert.jar
