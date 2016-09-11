DROP TABLE IF EXISTS verification;
DROP TABLE IF EXISTS `order`;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS publ_person;
DROP TABLE IF EXISTS person;
DROP TABLE IF EXISTS person_type;
DROP TABLE IF EXISTS publication;
DROP TABLE IF EXISTS publ_type;

CREATE TABLE publ_type (
  id int(11) PRIMARY KEY,
  name varchar(255) NOT NULL
);

CREATE TABLE publication (
  id int(11) PRIMARY KEY auto_increment,
  type_id int(11) NOT NULL,
  venue_id  int(11) NULL,
  title varchar(1000),
  venue varchar(255),
  year int(11),
  price decimal(19, 4),
  image_path text,
  description text,
  removed tinyint(4) NOT NULL DEFAULT 0,
  FOREIGN KEY (type_id) REFERENCES publ_type(id),
  FOREIGN KEY (venue_id) REFERENCES venue(id)
);

CREATE TABLE person (
  id int(11) PRIMARY KEY auto_increment,
  type tinyint(4) NOT NULL DEFAULT 0,
  name varchar(255) NOT NULL,
  aux text,
  bibtex text,
  KEY (name)
);

CREATE TABLE publ_person (
  publ_id int(11) NOT NULL,
  person_id int(11) NOT NULL,
  FOREIGN KEY (publ_id) REFERENCES publication(id) ON DELETE CASCADE,
  FOREIGN KEY (person_id) REFERENCES person(id) ON DELETE CASCADE
);

CREATE TABLE `user` (
  id int(11) PRIMARY KEY auto_increment,
  type tinyint(4) NOT NULL DEFAULT 0,
  username varchar(255) NOT NULL,
  password varchar(255),
  email varchar(255) NOT NULL,
  nickname varchar(255),
  first_name varchar(255),
  last_name varchar(255),
  birth_year int(11),
  credit_card varchar(255),
  address text,
  banned tinyint(4) NOT NULL DEFAULT 0
);

CREATE TABLE `order` (
  id int(11) PRIMARY KEY auto_increment,
  user_id int(11) NOT NULL,
  publ_id int(11) NOT NULL,
  `number` int(11) NOT NULL DEFAULT 1,
  add_ts timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  remove_ts timestamp NULL DEFAULT NULL,
  commit_ts timestamp NULL DEFAULT NULL,
  FOREIGN KEY (user_id) REFERENCES `user`(id),
  FOREIGN KEY (publ_id) REFERENCES publication(id)
);

CREATE TABLE verification (
  id int(11) PRIMARY KEY auto_increment,
  user_id int(11) NOT NULL,
  code varchar(255) NOT NULL,
  FOREIGN KEY (user_id) REFERENCES `user`(id)
);

CREATE TABLE venue (
  id int(11) PRIMARY KEY auto_increment,
  name varchar(255) NOT NULL,
  UNIQUE KEY (name)
);

INSERT INTO publ_type VALUES (0, 'article');
INSERT INTO publ_type VALUES (1, 'inproceedings');
INSERT INTO publ_type VALUES (2, 'proceedings');
INSERT INTO publ_type VALUES (3, 'book');
INSERT INTO publ_type VALUES (4, 'incollection');
INSERT INTO publ_type VALUES (5, 'phdthesis');
INSERT INTO publ_type VALUES (6, 'mastersthesis');
INSERT INTO publ_type VALUES (7, 'www');
