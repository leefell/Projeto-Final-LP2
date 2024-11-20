CREATE TABLE tasks (
    id SERIAL,
    title VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL,
    type VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    CONSTRAINT pk_tasks PRIMARY KEY(id)
);
