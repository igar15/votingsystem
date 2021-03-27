DELETE FROM votes;
DELETE FROM dishes;
DELETE FROM menus;
DELETE FROM restaurants;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User1', 'user1@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('User2', 'user2@yandex.ru', 'password');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001),
       ('USER', 100001),
       ('USER', 100002);

INSERT INTO restaurants (name, address)
VALUES ('zRestaurant1', 'Restaurant1Address'),
       ('aRestaurant2', 'Restaurant2Address');

INSERT INTO menus (restaurant_id, date)
VALUES (100003, '2021-02-25'),
       (100003, '2021-02-26'),
       (100004, '2021-02-26'),
       (100004, '2021-02-27');

INSERT INTO dishes (menu_id, name, price)
VALUES (100005, 'Restaurant1-25-02-2021-Dish1', 250),
       (100005, 'Restaurant1-25-02-2021-Dish2', 300),
       (100006, 'Restaurant1-26-02-2021-Dish1', 350),
       (100006, 'Restaurant1-26-02-2021-Dish2', 400),
       (100007, 'Restaurant2-26-02-2021-Dish1', 200),
       (100007, 'Restaurant2-26-02-2021-Dish2', 250),
       (100008, 'Restaurant2-27-02-2021-Dish1', 300),
       (100008, 'Restaurant2-27-02-2021-Dish2', 500);

INSERT INTO votes (user_id, restaurant_id, date)
VALUES (100000, 100003, '2021-02-25'),
       (100000, 100003, '2021-02-26'),
       (100000, 100004, '2021-02-27'),
       (100001, 100003, '2021-02-26'),
       (100001, 100004, '2021-02-27');