DROP TABLE IF EXISTS wishlist_item;
DROP TABLE IF EXISTS wishlist;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS profile;

CREATE TABLE profile (
                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         username VARCHAR(255) NOT NULL UNIQUE,
                         email VARCHAR(255) NOT NULL UNIQUE,
                         password VARCHAR(255) NOT NULL
);

CREATE TABLE product (
                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         name VARCHAR(255) NOT NULL,
                         price FLOAT NOT NULL
);

CREATE TABLE wishlist (
                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          due_date DATE,
                          visibility VARCHAR(20) NOT NULL,
                          profile_id BIGINT NOT NULL,
                          FOREIGN KEY (profile_id) REFERENCES profile(id)
);

CREATE TABLE wishlist_item (
                               wishlist_id BIGINT,
                               product_id BIGINT,
                               PRIMARY KEY (wishlist_id, product_id),
                               FOREIGN KEY (wishlist_id) REFERENCES wishlist(id),
                               FOREIGN KEY (product_id) REFERENCES product(id)
);

INSERT INTO profile (username, email, password)
VALUES ('testuser', 'test@test.dk', '1234');

INSERT INTO product (name, price)
VALUES ('Laptop', 9999.0),
       ('Telefon', 5999.0);

INSERT INTO wishlist (due_date, visibility, profile_id)
VALUES (CURRENT_DATE, 'PUBLIC', 1);

INSERT INTO wishlist_item (wishlist_id, product_id)
VALUES (1, 1),
       (1, 2);