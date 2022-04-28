#!/bin/sh
set -e

echo Creating schema...
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" -f /opt/apps/crypto_exchange_db/db/scripts/schema-sql/schema.sql
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" -f /opt/apps/crypto_exchange_db/db/scripts/schema-sql/gen-schema.sql
