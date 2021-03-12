CREATE TABLE user (
    id INTEGER NOT NULL AUTO_INCREMENT,
    name CHAR(100) NOT NULL,
    password CHAR(100) NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE user ADD CONSTRAINT constr_unique_user_name UNIQUE (name);