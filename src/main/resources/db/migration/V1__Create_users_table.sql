CREATE TABLE users (
                       id BIGINT IDENTITY(1,1) PRIMARY KEY,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       auth_provider VARCHAR(50) NOT NULL,
                       created_at DATETIME2 DEFAULT GETDATE(),
                       updated_at DATETIME2 DEFAULT GETDATE()
);

GO

CREATE TRIGGER trg_users_update
    ON users
    AFTER UPDATE
              AS
BEGIN
UPDATE users
SET updated_at = GETDATE()
    FROM users u
    INNER JOIN inserted i ON u.id = i.id
END