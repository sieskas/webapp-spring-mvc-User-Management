CREATE TABLE roles (
                       id BIGINT IDENTITY(1,1) PRIMARY KEY,
                       name VARCHAR(50) NOT NULL UNIQUE,
                       created_at DATETIME2 DEFAULT GETDATE(),
                       updated_at DATETIME2 DEFAULT GETDATE()
);

GO

CREATE TRIGGER trg_roles_update
    ON roles
    AFTER UPDATE
              AS
BEGIN
UPDATE roles
SET updated_at = GETDATE()
    FROM roles r
    INNER JOIN inserted i ON r.id = i.id
END

GO

-- Insérer des rôles par défaut
INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');