#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE USER root WITH PASSWORD 'root';
    CREATE DATABASE links_db;
    GRANT ALL PRIVILEGES ON DATABASE links_db TO root;
EOSQL
