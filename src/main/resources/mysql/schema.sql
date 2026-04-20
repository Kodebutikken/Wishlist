CREATE DATABASE IF NOT EXISTS wishlist;
USE wishlist;

DROP TABLE IF EXISTS wishlist_item;
DROP TABLE IF EXISTS wishlist;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS profile;

CREATE TABLE profile (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         username VARCHAR(255) NOT NULL UNIQUE,
                         email VARCHAR(255) NOT NULL UNIQUE,
                         password VARCHAR(255) NOT NULL
);

CREATE TABLE product (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         price FLOAT NOT NULL,
                         description TEXT,
                         product_url VARCHAR(255)
);

CREATE TABLE wishlist (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          due_date DATE,
                          visibility VARCHAR(20) NOT NULL,
                          profile_id BIGINT NOT NULL,
                          FOREIGN KEY (profile_id) REFERENCES profile(id)
);

CREATE TABLE wishlist_item (
                               wishlist_id BIGINT,
                               product_id BIGINT,
                               quantity INT NOT NULL,
                               PRIMARY KEY (wishlist_id, product_id),
                               FOREIGN KEY (wishlist_id) REFERENCES wishlist(id),
                               FOREIGN KEY (product_id) REFERENCES product(id)
);