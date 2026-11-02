create database CafeConnectDb;

use CafeConnectDb

CREATE TABLE food_items (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    category VARCHAR(255) NOT NULL,
    price DOUBLE NOT NULL,
    available BIT NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO food_items (name, category, price, available)
VALUES ('Veg Sandwich', 'Snack', 60.0, 1);

INSERT INTO food_items (name, category, price, available)
VALUES ('Veg Sandwich', 'Snack', 60.0, 1);

INSERT INTO food_items (name, category, price, available)
VALUES ('Chicken Burger', 'Fast Food', 120.0, 1);

INSERT INTO food_items (name, category, price, available)
VALUES ('Paneer Wrap', 'Snack', 80.0, 1);

INSERT INTO food_items (name, category, price, available)
VALUES ('Veg Fried Rice', 'Main Course', 100.0, 1);

INSERT INTO food_items (name, category, price, available)
VALUES ('Cold Coffee', 'Beverage', 50.0, 1);

INSERT INTO food_items (name, category, price, available)
VALUES ('Masala Dosa', 'South Indian', 70.0, 1);

SELECT * FROM food_items;
