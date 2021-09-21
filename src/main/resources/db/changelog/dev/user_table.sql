CREATE TABLE IF NOT EXISTS user
(
    id            INT PRIMARY KEY AUTO_INCREMENT,
    email         VARCHAR(255),
    password      VARCHAR(255),
    username          VARCHAR(255),
    first_name          VARCHAR(255),
    last_name          VARCHAR(255),
    creation_date DATE,
    type          VARCHAR(255),
    role_id INT,
    FOREIGN KEY (role_id)
            REFERENCES roles (id)
    )