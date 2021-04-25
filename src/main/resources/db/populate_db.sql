DELETE FROM votes;
DELETE FROM dishes;
DELETE FROM menus;
DELETE FROM restaurants;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001),
       ('USER', 100001);

INSERT INTO restaurants (name, address)
VALUES ('Якитория', 'Новый Арбат, 22'),
       ('Бургер Кинг', 'Авиамоторная, 34');

INSERT INTO menus (restaurant_id, date)
VALUES (100002, '2021-02-25'),
       (100002, '2021-02-26'),
       (100003, '2021-02-26'),
       (100003, now());

INSERT INTO dishes (menu_id, name, price)
VALUES (100004, 'Суши', 250),
       (100004, 'Рис', 300),
       (100005, 'Рыба', 350),
       (100005, 'Лапша', 400),
       (100006, 'Гамбургер', 200),
       (100006, 'Кола', 250),
       (100007, 'Чизбургер', 300),
       (100007, 'Фанта', 500);

INSERT INTO votes (user_id, restaurant_id, date)
VALUES (100000, 100002, '2021-02-25'),
       (100000, 100002, '2021-02-26'),
       (100000, 100003, '2021-02-27'),
       (100001, 100002, '2021-02-26'),
       (100001, 100003, '2021-02-27');