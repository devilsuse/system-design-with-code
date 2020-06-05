CREATE DATABASE IF NOT EXISTS short_url_generator;

ALTER USER 'root' IDENTIFIED BY 'unsecured';
GRANT ALL PRIVILEGES ON urldb.* TO 'root'@'%';
GRANT ALL PRIVILEGES ON urldb.* TO 'root'@'localhost';

USE short_url_generator;

CREATE TABLE redirection(
    id VARCHAR(10) NOT NULL,
    long_url VARCHAR(500) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    PRIMARY KEY(id)
)