gaia-framework
============

Percistence framework based on Hibernate.

Usage
-----

# BUILD GAIA
mvn clean install

# BUILD GAIA - CREATING SOURCES
mvn clean source:jar install

# BUILD GAIA WISH MySQL
# default properties:
# - mysql.host=localhost
# - mysql.portNumber=3306
# - mysql.user=root
# - mysql.pass=root

mvn clean install -Pmysql
# OR SET ESPECIFIC PROPERTIES
mvn clean install -Pmysql -Dmysql.host=localhost -Dmysql.portNumber=3306 -Dmysql.user=root -Dmysql.pass=root

# BUILD GAIA WISH PostgreSQL
# default properties:
# - postgres.host=localhost
# - postgres.portNumber=5432
# - postgres.user=postgres
# - postgres.pass=postgres

mvn clean install -Ppostgres
# OR SET ESPECIFIC PROPERTIES
mvn clean install -Ppostgres -Dpostgres.host=localhost -Dpostgres.portNumber=5432 -Dpostgres.user=postgres -Dpostgres.pass=postgres

# BUILD GAIA WISH ALL DATABASES
mvn clean install -Palldb
# OR SET ESPECIFIC PROPERTIES 
mvn clean install -Palldb -Dmysql.host=localhost -Dmysql.portNumber=3306 -Dmysql.user=root -Dmysql.pass=root -Dpostgres.host=localhost -Dpostgres.portNumber=5432 -Dpostgres.user=postgres -Dpostgres.pass=postgres
