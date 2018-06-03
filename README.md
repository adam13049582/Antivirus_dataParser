# Antivirus_dataParser

  This program was made for search information about information about antivirus software from website. 
In main menu there are four capabilities: "Get new information", "Edit existing information", "Add new version of existing information" 
and "Use information".If user choose one of the first three he/she has to type name of antivirus and website adress, 
then program search this page and all subwebsites of it. If my app find information about antivirus save it in Postgresql database. 
  "Use information" panel is needly when user want to check which antyvirus software  meets certain conditions. He/she has to choose one 
or more criteria . Data of this programs are showing in table on left.

                                                               How to start?
                                                              
1. Download Parser.jar file.
2. Download Antyviruses.sql file from catalog 'baza'
3. Create the database on your PostgreSQL Server.
Database name: Antyviruses
User: postgres
Password: antisoft
Use this command in CMD:
     psql -U postgres -c "create database Antyviruses with owner postgres encoding = 'UNICODE';"
4. Copy data from Antyviruses.sql file.
    $ psql -U postgres -d Antyviruses -f Antyviruses.sql

REMEMBER! If you change database name, user or password application will not work correctly.
