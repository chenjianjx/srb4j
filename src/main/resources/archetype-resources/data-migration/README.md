# What is this project for? 

* Run data migration with Maven
* Provide data migration tool in Java
* Data generation for some special cases 

# How to run data migration with Maven 

````
mvn clean package #so that the sql can appear in classpath
mvn initialize flyway:migrate 
````

## Why "initialize" ?
````
mvn flyway:migrate  #won't work
````

It's because during "initialize" lifecycle a plugin will be run to load database properties from they system's configuration file, to populate the variables in the pom.xml file, whose values are needed by flyway plugin.

But you can also do everything in a single line: 

````
mvn clean package flyway:migrate  # You won't need "initialize" because "mvn clean" will invoke "initialize"
````

# To generate a password for a staff user

````
mvn clean package exec:java -Dexec.mainClass="com.github.chenjianjx.srb4jfullsample.datagen.StaffUserPasswordGenerator"
````