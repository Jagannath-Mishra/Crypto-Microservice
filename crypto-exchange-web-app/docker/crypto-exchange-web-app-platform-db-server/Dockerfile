# Use postgres as the base image
FROM postgres:12.4

# Environment variable for database port
ENV DB_SERVER_PORT=5432

# create a folder where the postgres initdb scripts are located
RUN bash -c 'mkdir -p /docker-entrypoint-initdb.d /opt/apps/${customDbName}/db/scripts/schema-sql /opt/apps/platform/db/scripts/seed-data-sql'

# Copy the sql files to the container and the scripts to the docker-entrypoint-initdb.d folder.
# Postgres docker image loads the scripts in the docker-entrypoint-initdb.d folder after db initialization
ADD scripts/schema-sql/*.sql /opt/apps/${customDbName}/db/scripts/schema-sql/
ADD scripts/seed-data-sql/*.sql /opt/apps/${customDbName}/db/scripts/seed-data-sql/
ADD scripts/*.sh /docker-entrypoint-initdb.d/

# Change permissions
RUN bash -c 'chmod 755 /docker-entrypoint-initdb.d/*.sh'

# Expose the port
EXPOSE ${DB_SERVER_PORT}

# Command that will be executed to startup the container
CMD ["postgres"]