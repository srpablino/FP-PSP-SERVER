## Changeset tag

The chageset tag is what Liquibase uses to group database changes together. Each changeSet tag is uniquely identified by the combination of the “id” tag, the “author” tag, and the changelog file classpath name. The id tag is only used as an identifier, it does not direct the order that changes are run and does not even have to be an integer.
As Liquibase executes the databaseChangeLog, it reads the changeSets in order and, for each one, checks the “databasechangelog” table to see if the combination of id/author/filepath has been run. If it has been run, the changeSet will be skipped unless there is a true “runAlways” tag. After all the changes in the changeSet are run, Liquibase will insert a new row with the id/author/filepath along with an MD5Sum of the changeSet in the “databasechangelog”.

### Liquibase Formatted SQL

An easy way to add  a change set is to write the changes to the database as we normally do, using SQL, and adding a couple extra lines to make it a Liquibase-formatted SQL file. For example:

```
--liquibase formatted sql
--changeset codecentric-docs:release_1.create_tables.sql
 
CREATE TABLE SampleTable
(
  id uuid NOT NULL,
  someNumber INTEGER,
  CONSTRAINT sampletable_pkey PRIMARY KEY (id)
);
ALTER TABLE SampleTable
  OWNER TO sample;
 
--rollback DROP TABLE SampleTable;
```

The lines starting with "-" is what makes the file a Liquibase-formatted SQL-file. Only a few additional lines are needed.

```
--liquibase formatted sql
```

The above statement simply introduces the file as a Liquibase-formatted SQL-file.

```
--changeset codecentric-docs:release_1.create_tables.sql
```

As Liquibase is working on Changesets the name of the changeset introduced with this file must be defined. The part before the “:” is the author and the part after the “:” the name of the changeset itsself. We will use the following convention:

``
--changeset <author_username>:<serial id>.sql
``

One really nice feature is the possibility to define rollback statements. If used properly it is possible to return to an earlier state of the database this way. It should be noted that a Liquibase-formatted SQL-file can contain several SQL-statements and thus several rollback statements.

Now the only thing thats left is to include this file in the `db-changelog-evolution.xml` as follows:
```
<include file="<path_to_the_sql_file>/my_sql_file.sql" />
```

### Using Liquibase changeset format
Another way to introduce a change set is using Liquibase syntax. For example
```
<changeSet id="1" author="bob">
     <comment>A sample change log</comment>
     <createTable/>
</changeSet>

<changeSet id="2" author="bob" runAlways="true">
     <alterTable/>
</changeSet>
```

Where "id" and "author" must follow the same conventions previously discussed.

`runAlways="true"` executes the change set on every run, even if it has been run before.

To learn more about how to write change sets using the Liquibase format you can visit [this link](http://www.liquibase.org/documentation/changeset.html)







