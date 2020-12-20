CREATE TABLE binding
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    auction_id INT,
    user_id    INT,
    FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE SET NULL ON UPDATE CASCADE,
    FOREIGN KEY (auction_id) REFERENCES auction (id) ON DELETE SET NULL ON UPDATE CASCADE
)