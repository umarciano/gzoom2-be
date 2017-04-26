How to install OfBiz customization
----------------------------------

1. Checkout SVN source code under apache-ofbiz/hot-deploy preserving the `gn' folder (the folder `apache-ofbiz/hot-deploy/gn' will be created).
2. Initialize database:
    ant run-install-seed
3. Compile and run OfBiz:
    ant run

How to update the OfBiz customization
-------------------------------------
If the customization has been installed once, after a SVN update the following steps have to be taken:

1. Clean current db (optional, if something goes wrong try it):
    ant clean-data (if db is derby)
2. Re-init db:
    ant run-install-seed
3. Compile and run OfBiz:
    ant run

Customization of OfBiz files
----------------------------
This is a list of OfBiz files that must be changed for customization
	* /framework/security/config/security.properties
		* max.failed.logins=10 (max number of failed logins before disable the user, set to 0 if you won't disable it)
		* login.disable.minutes=3 (minutes that a disabled user must wait before it can access again)
		* store.login.history=true (to store login access in UserLoginHistory, used in service gnReenableUserLogin)
		* store.login.history.on.service.auth=true (to store login access by service invocation, used in service gnReenableUserLogin)

How to use a different DB (say Postgres)
----------------------------------------
0. See:
    https://blogs.oracle.com/robertlor/entry/ofbiz_and_postgresql

1. Download the relative JDBC drivers:
    ant download-PG-JDBC

2. Edit $OFBIZDIR/framework/entity/config/entityengine.xml.
   Lines with *** should just be uncommented or changed (but default should be ok).
    
    <delegator name="default" entity-model-reader="main" entity-group-reader="main" entity-eca-reader="main" distributed-cache-clear-enabled="false">
***        <group-map group-name="org.ofbiz" datasource-name="localpostgres"/>
        ...
    </delegator>
        
    <datasource name="localpostgres"
        ...
        <inline-jdbc
***        jdbc-driver="org.postgresql.Driver"
***        jdbc-uri="jdbc:postgresql://127.0.0.1/ofbiz"
***        jdbc-username="ofbiz"
***        jdbc-password="ofbiz"
        isolation-level="ReadCommitted"
        pool-minsize="2"
        pool-maxsize="250"/>
    </datasource>
 
3. In order to clean up or initialize the PostgreSQL's data folder, do as follows:
    cd pgsql
    rm -fr data
    SET POSTGRESQL_ROOT=C:\path\to\pgsql
    SET PGDATA=%POSTGRESQL_ROOT%\data
    SET PGENC=UTF-8
    "%POSTGRESQL_ROOT%\bin\initdb" -U postgres -E %PGENC% -A trust
 
4. Create the ofbiz database and user:
    cd pgsql\bin
    createuser -U postgres -S -D -R -P -E ofbiz
    createdb -E UTF-8 -U postgres -O ofbiz ofbiz

5. Drop PostgreSQL tables (prior to updates to Ofbiz schemas):
    ant clean-postgresql-db -f hot-deploy\gn\db-clean.xml 
