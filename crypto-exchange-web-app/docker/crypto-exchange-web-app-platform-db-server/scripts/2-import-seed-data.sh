#!/bin/sh
set -e

echo Importing data.sql...
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" -f /opt/apps/crypto_exchange_db/db/scripts/seed-data-sql/data.sql
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" -f /opt/apps/crypto_exchange_db/db/scripts/seed-data-sql/auth_data.sql
