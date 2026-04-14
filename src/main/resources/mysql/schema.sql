CREATE DATABASE IF NOT EXISTS wishlist;
USE wishlist;

DROP TABLE IF EXISTS wishlist_item;
DROP TABLE IF EXISTS wishlist;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL,
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
                          visibility VARCHAR(20),
                          user_id BIGINT NOT NULL,
                          FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE wishlist_item (
                               wishlist_id BIGINT,
                               product_id BIGINT,
                               quantity INT,
                               PRIMARY KEY (wishlist_id, product_id),
                               FOREIGN KEY (wishlist_id) REFERENCES wishlist(id),
                               FOREIGN KEY (product_id) REFERENCES product(id)
);

