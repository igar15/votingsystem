DELETE FROM votes;
DELETE FROM dishes;
DELETE FROM menus;
DELETE FROM restaurants;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User_1', 'user1@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('User_2', 'user2@yandex.ru', 'password');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001),
       ('USER', 100002);

INSERT INTO restaurants (name, address)
VALUES ('Rest_2', 'Rest_2_Address'),
       ('Rest_1', 'Rest_1_Address');

INSERT INTO menus (restaurant_id, date, published)
VALUES (100003, '2021-02-25', true),
       (100003, '2021-02-26', true),
       (100004, '2021-02-26', true),
       (100004, '2021-02-27', true);

INSERT INTO dishes (menu_id, name, price)
VALUES (100005, 'Rest_1_25-02_Dish_2', 250),
       (100005, 'Rest_1_25-02_Dish_1', 300),
       (100006, 'Rest_1_26-02_Dish_1', 350),
       (100006, 'Rest_1_26-02_Dish_2', 400),
       (100007, 'Rest_2_26-02_Dish_1', 200),
       (100007, 'Rest_2_26-02_Dish_2', 250),
       (100008, 'Rest_2_27-02_Dish_1', 300),
       (100008, 'Rest_2_27-02_Dish_2', 500);

INSERT INTO votes (user_id, restaurant_id, date)
VALUES (100000, 100003, '2021-02-25'),
       (100000, 100003, '2021-02-26'),
       (100000, 100004, '2021-02-27'),
       (100001, 100003, '2021-02-26'),
       (100001, 100004, '2021-02-27');