CREATE TABLE user_roles (
                            user_id BIGINT NOT NULL,
                            role_id BIGINT NOT NULL,
                            PRIMARY KEY (user_id, role_id),
                            CONSTRAINT FK_user_roles_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                            CONSTRAINT FK_user_roles_role FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);
GO
INSERT INTO USERS (email, password, auth_provider) VALUES ('admin', '$2a$10$5jFb1bcqZl5Yg5/O/pMYYuSy9QG/wGpcFor2jXE/Yyu/gBuqOqZo6', 'INTERNAL');
INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);