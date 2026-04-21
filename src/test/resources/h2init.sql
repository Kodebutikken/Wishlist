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
                         price FLOAT NOT NULL,
                         description VARCHAR(255),
                         product_url VARCHAR(255),
                         profile_id BIGINT,
                         FOREIGN KEY (profile_id) REFERENCES profile(id)
);

CREATE TABLE wishlist (
                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          name VARCHAR(255) NOT NULL,
                          image_url VARCHAR(255),
                          due_date DATE,
                          visibility VARCHAR(20) NOT NULL,
                          profile_id BIGINT NOT NULL,
                          FOREIGN KEY (profile_id) REFERENCES profile(id)
);

CREATE TABLE wishlist_item (
                               wishlist_id BIGINT NOT NULL,
                               product_id BIGINT NOT NULL,
                               quantity INT NOT NULL DEFAULT 1,
                               PRIMARY KEY (wishlist_id, product_id),
                               FOREIGN KEY (wishlist_id) REFERENCES wishlist(id),
                               FOREIGN KEY (product_id) REFERENCES product(id)
);


ALTER TABLE profile ALTER COLUMN id RESTART WITH 2;
ALTER TABLE product ALTER COLUMN id RESTART WITH 3;
ALTER TABLE wishlist ALTER COLUMN id RESTART WITH 2;


INSERT INTO profile (id, username, email, password)
VALUES (1, 'testuser', 'test@test.dk', '1234');

INSERT INTO product (id, name, price, description, product_url, profile_id)
VALUES
    (1, 'Laptop', 9999.0, 'Gaming laptop', 'http://example.com/laptop', 1),
    (2, 'Telefon', 5999.0, 'Smartphone', 'http://example.com/phone', 1);

INSERT INTO wishlist (id, name, image_url, due_date, visibility, profile_id)
VALUES
    (1, 'Test wishlist', NULL, CURRENT_DATE, 'PUBLIC', 1);

INSERT INTO wishlist_item (wishlist_id, product_id, quantity)
VALUES
    (1, 1, 1),
    (1, 2, 2);