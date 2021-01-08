# DocumentFlow

Hello there!

Welcome to DocumentFlow repository.

# WTF?
*DocumentFlow allows you to organize the chaos in paper documents. Everything is stored in one place. Easy to find, impossible to lose.*


**INCOMING DOCUMENTS**
- registration in system adding of scan-copies
- creating and executing tasks by document
- methods:
    1. showIn - displays a register of cards of incoming documents
    2. getCard - returns an incoming document card
    3. regEditDoc - saves an incoming document card
    4. delete - changes the status of the incoming document card to “deleted”

**OUTGOING DOCUMENTS**
- prepearing projects of documents
- document approval (by tasks)
- generating PDF with signing

**TASKS**
- list of tasks appointed to current user
- list of tasks, created by user

**SYSTEM DICTIONARIES**
- organization structure and users document and tasks types

# HOW TO INSTALL
1. add file database.properties into folder: documentflow/src/main/resources/
2. add configuration of your postgres-database connection to database.properties like this:
```
spring.datasource.url=jdbc:postgresql://YOUR_DB
spring.datasource.username=YOUR_DB_USERNAME
spring.datasource.password=YOUR_DB_PASSWORD
```
3. migrate db by flyway
4. run ```public static void main(String[] args)``` in DocumentflowApplication.java
5. front migrate to vue cd /frontend run build/serve for prod/dev

6. PROFIT
