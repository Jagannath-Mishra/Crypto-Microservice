-- Set the database and schema names
\set DB_NAME 'crypto_exchange_db'
\set SCHEMA_NAME 'public'

\echo "Creating database, schema and the necessary tables..."

-- Create the database, connect to the created database and create a new schema
-- Schema name is defaulted to the same name as the database name.
-- CREATE DATABASE :DB_NAME;
\connect :DB_NAME
CREATE SCHEMA IF NOT EXISTS public;
    --  Lookup Table: database_engine
    CREATE TABLE IF NOT EXISTS public.database_engine
    (
        code  VARCHAR(16) NOT NULL PRIMARY KEY,
        value VARCHAR(32) NOT NULL
    );

    -- Lookup Table: tenant_status
    CREATE TABLE IF NOT EXISTS public.tenant_status
    (
        code  VARCHAR(64)  NOT NULL PRIMARY KEY,
        value VARCHAR(512) NOT NULL
    );

    -- Lookup Table: tenant_asset_type
    CREATE TABLE IF NOT EXISTS public.tenant_asset_type
    (
        code  VARCHAR(32) NOT NULL PRIMARY KEY,
        value VARCHAR(64) NOT NULL
    );

    -- Table: tenant_database_configuration
    CREATE TABLE IF NOT EXISTS public.tenant_database_configuration
    (
        id                      VARCHAR(36)  NOT NULL PRIMARY KEY,
        database_engine_code    VARCHAR(36) REFERENCES public.database_engine(code),
        host                    VARCHAR(128) NOT NULL,
        port                    INTEGER NOT NULL,
        instance_id             VARCHAR(64) NOT NULL UNIQUE,
        name                    VARCHAR(64) NOT NULL UNIQUE,
        schema                  VARCHAR(64) NOT NULL,
        username                VARCHAR(16) NOT NULL,
        password                VARCHAR(128) NOT NULL
    );

    -- Table: tenant_administrator
    CREATE TABLE IF NOT EXISTS public.tenant_administrator
    (
        id         VARCHAR(36)  NOT NULL PRIMARY KEY,
        email      VARCHAR(64)  NOT NULL UNIQUE,
        full_name VARCHAR(64),
        phone      VARCHAR(24),
        status     VARCHAR(64)
    );

    -- Table: tenant_asset
    CREATE TABLE IF NOT EXISTS public.tenant_asset
    (
        id                VARCHAR(36) NOT NULL PRIMARY KEY,
        small_file_name   VARCHAR(64) ,
        medium_file_name  VARCHAR(64) ,
        large_file_name   VARCHAR(64) ,
        large_file_path   VARCHAR(512),
        medium_file_path  VARCHAR(512),
        small_file_path   VARCHAR(512),
        logo_file_path    VARCHAR(512),
        logo_file_name    VARCHAR(512),
        type_code         VARCHAR(32) NOT NULL REFERENCES public.tenant_asset_type(code)
    );

    -- Table: tenant
    CREATE TABLE IF NOT EXISTS public.tenant
    (
        id                                  VARCHAR(36) NOT NULL PRIMARY KEY,
        name                                VARCHAR(128) NOT NULL UNIQUE,
        platform_subdomain_name             VARCHAR(16) NOT NULL UNIQUE,
        asset_id                            VARCHAR(36) REFERENCES public.tenant_asset(id),
        secret                              VARCHAR(64) NOT NULL,
        administrator_id                    VARCHAR(36) NOT NULL REFERENCES public.tenant_administrator(id),
        status_code                         VARCHAR(64) NOT NULL REFERENCES public.tenant_status(code),
        tenant_database_configuration_id    VARCHAR(36) REFERENCES public.tenant_database_configuration(id),
        master                              BOOLEAN DEFAULT FALSE,
        marked_for_deletion_timestamp       BIGINT,
        created_by                          VARCHAR(36),
        creation_timestamp                  BIGINT,
        last_modified_by                    VARCHAR(36),
        last_modified_timestamp             BIGINT,
        status_reason                       VARCHAR(256)
    );

    -- Table: provisioned_tenant_domain
    CREATE TABLE IF NOT EXISTS public.provisioned_tenant_domain
    (
        id          VARCHAR(36)  NOT NULL PRIMARY KEY,
        tenant_id   VARCHAR(36) REFERENCES public.tenant(id),
        name        VARCHAR(128) NOT NULL UNIQUE,
        category    VARCHAR(32) NOT NULL
    );

    -- Table: tenant_domain
    CREATE TABLE IF NOT EXISTS public.tenant_domain
    (
        id           VARCHAR(36)  NOT NULL PRIMARY KEY,
        name         VARCHAR(128) NOT NULL,
        tenant_id    VARCHAR(36) REFERENCES public.tenant(id),
        domain_type  VARCHAR(128)
    );

    -- Table: settings
    CREATE TABLE public.settings
    (
        id           VARCHAR(36)  NOT NULL PRIMARY KEY,
        tenant_id    VARCHAR(36) REFERENCES public.tenant(id),
        data         VARCHAR(65535)
    );

    -- Table: public.email_configuration
    CREATE TABLE IF NOT EXISTS public.email_configuration
    (
        id                                  VARCHAR(36) NOT NULL PRIMARY KEY,
        account_name                        VARCHAR(128) NOT NULL UNIQUE,
        email                               VARCHAR(64) NOT NULL,
        host                                VARCHAR(128) NOT NULL,
        is_default                          BOOLEAN,
        port                                INTEGER,
        protocol                            VARCHAR(16),
        mail_smtp_auth                      VARCHAR(128),
        mail_smtp_ssl_enable                VARCHAR(128),
        same_creds_for_email_notification   VARCHAR(128),
        password                            VARCHAR(128) NOT NULL,
        send_to                             VARCHAR(128),
        tenant_id                           VARCHAR(36) REFERENCES public.tenant(id)
    );

    -- Table: public.email_template
    CREATE TABLE IF NOT EXISTS public.email_template
    (
        id                           VARCHAR(36) NOT NULL PRIMARY KEY,
        name                         VARCHAR(128) NOT NULL,
        description                  VARCHAR(256),
        subject                      VARCHAR(256),
        body                         VARCHAR(1024),
        template_type                VARCHAR(64),
        tenant_id                    VARCHAR(36) REFERENCES public.tenant(id),
        is_default_email_template    BOOLEAN
    );

    -- Table: email_domain
    CREATE TABLE IF NOT EXISTS public.email_domain
    (
    id                      VARCHAR(36) NOT NULL PRIMARY KEY,
    name                    VARCHAR(128) NOT NULL ,
    email_configuration_id  VARCHAR(36) REFERENCES public.email_configuration(id),
    domain_type             VARCHAR(36)
    );

    -- Lookup Table: tenant_template_types
    CREATE TABLE IF NOT EXISTS public.tenant_template_type
    (
        code    VARCHAR(64) NOT NULL PRIMARY KEY,
        value   VARCHAR(512) NOT NULL
    );

    -- Lookup Table: tenant_template_fields
    CREATE TABLE IF NOT EXISTS public.tenant_template_field
    (
        code    VARCHAR(64) NOT NULL PRIMARY KEY,
        value   VARCHAR(512) NOT NULL
    );

    -- Table: public.password_policy
    CREATE TABLE public.password_policy
    (
        id                           VARCHAR(64) NOT NULL PRIMARY KEY,
        tenant_id                    VARCHAR(36) REFERENCES public.tenant(id),
        minimum_length               INTEGER,
        maximum_length               INTEGER,
        minimum_uppercase_characters INTEGER,
        minimum_lowercase_characters INTEGER,
        minimum_digits               INTEGER,
        minimum_special_characters   INTEGER,
        password_age_in_days         INTEGER
    );

    -- Lookup Table: tenant_template
    CREATE TABLE IF NOT EXISTS public.tenant_template
    (
        template_type       VARCHAR(64) NOT NULL PRIMARY KEY,
        value               VARCHAR(64),
        is_admin_template   BOOLEAN
    );

    -- Table: template_placeholder
    CREATE TABLE IF NOT EXISTS public.template_placeholder
    (
          id                 VARCHAR(36) NOT NULL PRIMARY KEY,
          placeholder_id     VARCHAR(64) NOT NULL REFERENCES public.tenant_template(template_type),
          placeholder_value  VARCHAR(128)
    );

    CREATE TABLE IF NOT EXISTS public.tenant_logos
    (
            id              VARCHAR(36) NOT NULL PRIMARY KEY,
            file_name       VARCHAR(128),
            file_path       VARCHAR(1024),
            file_type       VARCHAR(36),
            tenant_id       VARCHAR(36)
    );