CREATE TABLE IF NOT EXISTS user
(
    id            INT PRIMARY KEY AUTO_INCREMENT,
    email         VARCHAR(255),
    password      VARCHAR(255),
    name          VARCHAR(255),
    creation_date DATE,
    type          VARCHAR(255),
    role VARCHAR(30)
    )