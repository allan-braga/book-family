create schema if not exists testdb;

CREATE TABLE IF NOT EXISTS `role` (
     `id` INTEGER PRIMARY KEY AUTO_INCREMENT,
     `name` VARCHAR(50)
);

INSERT INTO role (name) VALUES ('ROLE_USER');
INSERT INTO role (name) VALUES ('ROLE_ADMIN');

CREATE TABLE IF NOT EXISTS `user` (
      `id` INTEGER PRIMARY KEY AUTO_INCREMENT,
      `username` VARCHAR(255),
      `password` VARCHAR(255)
);

INSERT INTO user (username, password) VALUES ('user', '$2y$12$7nMP.FhkfEBcLOKHqoMrje2fG8rI7/UoI8M3QwW0nAm/kjhVWTSRW');
INSERT INTO user (username, password) VALUES ('admin', '$2y$12$6xhlSg1//6t.QsVrumP6IuF8ugPfGe/10mk1cfnrbv4MNPfYziuOa');

CREATE TABLE IF NOT EXISTS `user_role` (
      `user_id` INTEGER,
      `role_id` INTEGER
);

INSERT INTO user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO user_role (user_id, role_id) VALUES (2, 2);