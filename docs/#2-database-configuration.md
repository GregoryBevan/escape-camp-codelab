## Configuration of PostgreSQL database

### Local Docker container creation
To easily set up a database on your computer, we're going to use Docker.
- First create a _docker-compose.yml_ file
- Copy the following content to it
```dockerfile
services:
  escape_camp_postgres:
    image: postgres:16-alpine
    container_name: escape_camp_codelab_postgres
    restart: always
    command: ["postgres", "-c", "log_min_duration_statement=1000", "-c", "log_destination=stderr"]
    volumes:
      - ./docker/init.sql:/docker-entrypoint-initdb.d/init.sql
      - escape-camp-data:/var/lib/posgresql/data
    ports:
      - 5432:5432
    environment:
      POSTGRES_PASSWORD: postgres

volumes:
  escape-camp-data:
```
- To initialize the database, create the file _init.sql_, it will be played on container startup
- Copy the following content to this file
```postgresql
create role admin with password 'guess-what' superuser createdb createrole inherit login;
create database "escape-camp" owner admin;
```
- Open a terminal to the project root path
- Type the command below:
```shell
docker compose up -d
```
This will start the container and you can check everything is running smoothly :
```shell
docker ps
```
![docker-ps.png](%232%2Fdocker-ps.png)


### Application configuration
- Rename _application.properties_ to _application.yml_
- Copy the following content to _application.yml_
```yml
spring:
  r2dbc:
    url: r2dbc:postgresql://${POSTGRESQL_ADDON_HOST:localhost}:${POSTGRESQL_ADDON_PORT:5432}/${POSTGRESQL_ADDON_DB:escape-camp}
    username: ${POSTGRESQL_ADDON_USER:admin}
    password: ${POSTGRESQL_ADDON_PASSWORD:guess-what}
    pool:
      initialSize: 2
      maxSize: 10
    properties:
      sslMode: ${POSTGRESQL_R2DBC_SSL_MODE:DISABLE}
  liquibase:
    change-log: classpath:db/changelog/db-changelog.sql
    url: jdbc:postgresql://${POSTGRESQL_ADDON_HOST:localhost}:${POSTGRESQL_ADDON_PORT:5432}/${POSTGRESQL_ADDON_DB:escape-camp}
    user: ${POSTGRESQL_ADDON_USER:admin}
    password: ${POSTGRESQL_ADDON_PASSWORD:guess-what}
```
- Create a file named _db-changelog.sql_ in folder _src/resources/db/changelog_
- Copy the line below to this file
```sql
--liquibase formatted sql
```

### Start the application
You can now start the application from IntelliJ :
- Open _EscapeCampApplication.kt_
- Click on the green arrow

![start-application.png](%232%2Fstart-application.png)
