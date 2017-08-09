## Run the PSP-server 
The PSP-server used a Maven-based build system and liquibase for the database evolution. It also uses Spring Boot to run the compiled JAR artifact with an embedded Tomcat Server.

## Prerequisites

* Maven 3.3.3 or above
* Java 8

## Basic setup

These are steps that you will only need to perform the first time you are 
configuring the PSP-server.

### Step 0, clone the repo

```shell
git clone https://github.com/FundacionParaguaya/FP-PSP-SERVER.git
```
### Step 1, create the database

Run with psql the script:

```shell
psql -U YOUR_USER_HERE -f src/main/resources/create_database.sql
```

Yo can also copy and paste the following snippet to your favorite PSQL client:

```
CREATE database "fp_psp_db";
SELECT datname FROM pg_database WHERE datistemplate = false;
CREATE USER "fp_psp_db" WITH ENCRYPTED PASSWORD 'fp_psp_db';
GRANT ALL PRIVILEGES ON DATABASE "fp_psp_db" to "fp_psp_db";
ALTER DATABASE "fp_psp_db" OWNER TO "fp_psp_db";
\c "fp_psp_db";
CREATE SCHEMA "fp_psp_db";
GRANT ALL PRIVILEGES ON SCHEMA "fp_psp_db" to "fp_psp_db";
```

### Step 2, compile the application

From the fp-psp-server directory, run:

```shell
mvn clean install -DskipTests
```

If you'd rather run unit tests:

```shell
mvn clean install
```

### Step 3, run the application

Start the server:

```shell
java -jar target/*.jar
```

And that is it ;), your server will be launched by default at http://localhost:8080.

## What's next?

- Do you want to make changes to the database using Liquibase?  [Read the Liquibase instructions.](LIQUIBASE.md)

- Do you want to provide custom configuration on applicaiton startup? [Read the CUSTOM_CONFIG instructions](CUSTOM_CONFIG.md)