## Poverty Stoplight
Fundación Paraguaya (FP from now on) has developed a methodology called 
“Poverty Stoplight”. The Poverty Stoplight seeks to eliminate the 
multidimensional poverty that affects many families. It allows families to 
trace their own poverty map and develop and implement a clear plan to 
overcome it.

Through a visual survey that shows photographs, families self-assess their 
level of poverty with 50 indicators. These indicators are in turn grouped 
into 6 different dimensions of poverty.

##Poverty Stoplight Platform
The PSP (Poverty Stoplight Platform) is a set of components that assist 
people in the process of acquiring data, analyzing and helping the families 
with solution to overcome his poverty.

The PSP-server is a Spring Boot application that expose a REST API to 
diferent components (i.e. an Andorid Application)

##Status of the project
The project is just starting and we expect to have a running version by the 
end of the year.

The platform is currently being developed by a group of developers 
from FP and SODEP. We welcome help from other developers, but we understand 
that the project is still in its early days.

Since we have a comprise to remain an open source project, we decided to push
 it to github and let everybody know our progress.

##Code of conduct
Under development

##Test environment
Under development

##Issue Tracking
We've decided to use our internal issue tracking for fastest communication in
 the development team. We are going to use pull requests from day one and we are
 planning to move to github tickets once we reach a stable version.
 
##Building from source
The PSP-server used a Maven-based build system and liquibase for the database
 evolution.
 
 The directory "scripts" contains some custom scripts on top of maven and 
 liquibase that assist a developer during the development lifecyle.

Under the directory "conf" you will find examples of configuration file to 
customize your installation.
### Prerequisites

[Git][] and [JDK 8 update 20 or later][JDK8 build]

###Basic setup
These are steps that you will only need to perform the first time you are 
configuring the PSP-server.
####Step 1) Create /opt/psp-server diretory
Create the directory /opt/psp-server. This is the recommended path, but you 
are free to change it. 

####Step 2) Configure development.vars
Copy the file "conf/development.vars" to /opt/psp-server/ 
Edit the content of the file. You will find to variables
```
# The maven setting
export MVN_SETTINGS=$HOME/.m2/settings.xml

# The profile directory. It is passed to maven to read the application.properties from theres.
export PROFILE_DIR=/opt/psp-server/dev
```
####Step 3) Create your dev directory
Create the directory /opt/psp-server/dev

Copy the file conf/application.properties to /opt/psp-server/dev

Note: You can have many "profile" directory. For example /opt/psp-server/test
 or /opt/psp-server/prod, each with its own configuration
 
####Step 4) Configure the application.properties
Probably you will need to configure the database properties. Please take a 
look at the following properties:
```
spring.datasource.driver-class-name=org.postgresql.Driver
spring.datasource.url=jdbc\:postgresql\://localhost\:5432/poverty_stoplight_platform
spring.datasource.username=postgres
spring.datasource.password=postgres
```

###Creating the Database
#### Step 1) Setup the ENV_VARS variable
Setup a environment variable that points to your development.vars file.
 
`export ENV_VARS=/opt/psp-server/development.vars`

Note: You can have several *.vars files. For example production.vars, testing
.vars. Each pointing to a different configuration file.

#### Step 2) Create the DB
If this is your first time, or you just want a new fresh DB please execute 
the following command. 

`scripts/updater fresh`

It will create the DB and update it to the latest  version.

#### Step 3) Update the DB
If you just want to update the DB, you can execute the following command:

`scripts/updater update`

###Compile the server

```
mvn -Dext.prop.dir=/opt/psp-server/dev -Dspring.config
.location=file:/opt/psp-server/dev/application.properties test
```

###Starting the server
The PSP-server is build on top of Spring Boot, therefore you can just run it 
as any other Spring Boot App. However, we recommend to have the application
.properties file on an external directory, as mentioned on the previous 
section. 

If you follow our recommendation, the following line should start a new server.

```
cd target
java -jar spring.config.location=file:/opt/psp-server/dev/application
.properties psp-server-0.0.1-SNAPSHOT.jar
```

## License
The Spring Framework is released under version 2.0 of the [Apache License][]

[Git]: http://help.github.com/set-up-git-redirect
[JDK8 build]: http://www.oracle.com/technetwork/java/javase/downloads
[Apache License]: http://www.apache.org/licenses/LICENSE-2.0