\set DB_NAME 'crypto_exchange_db'
\connect :DB_NAME

CREATE EXTENSION IF NOT EXISTS pgcrypto;
-- Table: user
INSERT INTO REPLACE_AUTH_SCHEMA."user"(id,username,full_name,email,tenant_id,password) VALUES
    (111, 'superadmin@yopmail.com','Super Admin','superadmin@yopmail.com','b5e6b281-6a70-4517-9e65-e27bd972d74b','$2a$04$/6WrFB4ddO5EGt8gzfehf.BAf8hRZvSZx.1r2CkWP.navTHRQNEn.');

UPDATE REPLACE_AUTH_SCHEMA."user" set super_admin =true where email='superadmin@yopmail.com';

-- Table: role
INSERT INTO REPLACE_AUTH_SCHEMA.role(id, name)
VALUES
    (gen_random_uuid(), 'Administrator'),
    (gen_random_uuid(), 'DevOps'),
    (gen_random_uuid(), 'Compliance');

-- Table: user_role
INSERT INTO REPLACE_AUTH_SCHEMA.user_role(user_id, role_id) VALUES
    ((SELECT id FROM REPLACE_AUTH_SCHEMA."user" WHERE email = 'superadmin@yopmail.com'), (SELECT id FROM REPLACE_AUTH_SCHEMA.role WHERE name = 'Administrator'));

-- Lookup Table: user_asset_type
INSERT INTO REPLACE_AUTH_SCHEMA.user_asset_type(code, value)
VALUES
    ('PROFILE_PICTURE', 'Profile Picture');