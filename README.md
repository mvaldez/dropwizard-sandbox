# Dropwizard
RESTFul Web Services with Dropwizard
by Alexandros Dallas

Book examples using gradle

# dependencies
dropwizard.io version 0.7.0
=======
# Dropwizard Tutorial
Based on the examples in the book RESTFul Web Services with Dropwizard by Alexandros Dallas

Book uses Maven but this project uses Gradle

# dependencies
* dropwizard.io version 0.7.0
* MySQL version 5.1.22

# database
Examples use MySQL database.  Create database schema and tables with the following command

Start mysql client and create database
```
create database phonebook;
```

Create users
```
create user 'phonebookuser'@localhost' identified by 'phonebookpassword';
grant all on phonebook.* to 'phonebookuser'@'localhost';
use 'phonebook'
```

Import tables
```
mysql -u username -p phonebook < database/mysql/phonebook-import.sql
```
