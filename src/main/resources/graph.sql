DROP TABLE IF EXISTS graph_relation;
DROP TABLE IF EXISTS graph_entity;

CREATE TABLE graph_entity (
    id int(11) PRIMARY KEY AUTO_INCREMENT,
    entity_id int(11) NOT NULL,
    `key` varchar(255) NOT NULL,
    `value` text NULL
);

CREATE TABLE graph_relation (
    id int(11) PRIMARY KEY AUTO_INCREMENT,
    subject_id int(11) NOT NULL,
    object_id int(11) NOT NULL,
    edge_id int(11) NOT NULL
);