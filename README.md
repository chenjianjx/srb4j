#Still Under Construction

#srbj4j

##Quick Start

````
cd /path/to/your/workspace

mvn -X archetype:generate \ 
-DarchetypeGroupId=com.github.chenjianjx -DarchetypeArtifactId=srb4j -DarchetypeVersion=1.0 \
-DgroupId=your.groupId  \
-DartifactId=yourArtifactid \
-Dpackage=your.package.name \
-Dversion=1.0-SNAPSHOT \
-DarchetypeRepository=https://jitpack.io \


mkdir ~/yourArtifactId
cd ~/yourArtifactId

wget https://raw.githubusercontent.com/chenjianjx/srb4j/master/src/main/resources/archetype-resources/doc/app.properties.sample -O app.properties

````

Please edit app.properties according to your environment.

Then you must set up a db according to the config in app.properties. After creating database run the [ddl](https://github.com/chenjianjx/srb4j/blob/master/src/main/resources/archetype-resources/doc/sql/ddl.sql). 
 
````
cd /path/to/your/workspace/yourArtifactid

mvn install -DskipTests

cd webapp

mvn jetty:run

````

now visit http://locahost:8080


## Features
