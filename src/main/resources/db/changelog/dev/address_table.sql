CREATE TABLE IF NOT EXISTS address
(
    id       INT PRIMARY KEY AUTO_INCREMENT,
    user_id  INT,
    province VARCHAR(255),
    city     VARCHAR(255),
    address  VARCHAR(255),
    FOREIGN KEY (user_id)
        REFERENCES user (id)
)