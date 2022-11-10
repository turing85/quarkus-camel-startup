DROP TABLE IF EXISTS animal;

CREATE TABLE animal(
    id BIGINT CONSTRAINT animal__pk__id PRIMARY KEY,
    name VARCHAR(255)
);

INSERT INTO animal(id, name)
    VALUES (0, 'doggo'), (1, 'dogette'), (2, 'nyancat');