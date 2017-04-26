@ECHO OFF

title "Recreating PostgreSQL database"

SET POSTGRESQL_ROOT=C:\Users\fast\Programs\pgsql

echo "Dropping database Ofbiz..."
call %POSTGRESQL_ROOT%\bin\dropdb -U postgres ofbiz
echo "Dropping user Ofbiz..."
call %POSTGRESQL_ROOT%\bin\dropuser -U postgres ofbiz
echo "Creating user Ofbiz..."
call %POSTGRESQL_ROOT%\bin\createuser -U postgres -S -D -R -P -E ofbiz
echo "Creating database Ofbiz..."
call %POSTGRESQL_ROOT%\bin\createdb -E UTF-8 -U postgres -O ofbiz ofbiz
echo "Done!"

