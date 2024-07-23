CREATE TABLE external_applications
(
    id             BIGINT IDENTITY(1,1) PRIMARY KEY,
    name           VARCHAR(255) NOT NULL,
    authorized_url VARCHAR(255) NOT NULL
);

GO

ALTER TABLE tokens
    ADD external_application_id BIGINT;

GO

ALTER TABLE tokens
    ADD CONSTRAINT FK_tokens_external_applications FOREIGN KEY (external_application_id) REFERENCES external_applications (id);

GO

INSERT INTO external_applications (name, authorized_url) VALUES ('PowerBI', '/api/v1/data');
