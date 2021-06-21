CREATE TABLE IF NOT EXISTS `book_family` (
   `id` INTEGER PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    `description` VARCHAR(255),
    `library_id` INTEGER NOT NULL,
    CONSTRAINT `fk_book_family_library` FOREIGN KEY (`library_id`) REFERENCES `library` (`id`)
);

INSERT INTO book_family (name, description, library_id) VALUES ('Family 1', '', 1);

CREATE TABLE IF NOT EXISTS `book` (
    `id` INTEGER PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    `description` VARCHAR(255),
    `author` VARCHAR(255),
    `pages` INTEGER,
    `family_id` INTEGER NOT NULL,
     CONSTRAINT `fk_book_family` FOREIGN KEY (`family_id`) REFERENCES `book_family` (`id`)
);

INSERT INTO book (name, description, author, pages, family_id) VALUES ('The Hobbit', '', 'Token', 544, 1);