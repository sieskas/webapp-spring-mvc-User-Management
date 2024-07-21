CREATE TABLE tokens (
                        id BIGINT IDENTITY(1,1) PRIMARY KEY,
                        user_id BIGINT NOT NULL,
                        token VARCHAR(255) NOT NULL,
                        expiration_enum_id  VARCHAR(10) NOT NULL,
                        expiration_time DATETIME2 NOT NULL,
                        created_at DATETIME2 DEFAULT GETDATE(),
                        updated_at DATETIME2 DEFAULT GETDATE(),
                        FOREIGN KEY (user_id) REFERENCES users(id)
);

GO

CREATE TRIGGER trg_tokens_update
    ON tokens
    AFTER UPDATE
              AS
BEGIN
UPDATE tokens
SET updated_at = GETDATE()
    FROM tokens u
    INNER JOIN inserted i ON u.id = i.id
END;