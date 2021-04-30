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

INSERT INTO restaurants (name, address, imageUrl)
VALUES ('Якитория', 'Новый Арбат, 22', 'assets/images/yakitoriya.jpeg'),
       ('Бургер Кинг', 'Авиамоторная, 34', 'assets/images/burger-king.jpeg'),
       ('Paulaner', 'Невский, 89', 'assets/images/paulaner.jpg');

INSERT INTO menus (restaurant_id, date)
VALUES (100002, '2021-02-25'),
       (100002, '2021-02-26'),
       (100003, '2021-02-26'),
       (100003, now());

INSERT INTO dishes (menu_id, name, price)
VALUES (100005, 'Суши', 250),
       (100005, 'Рис', 300),
       (100006, 'Рыба', 350),
       (100006, 'Лапша', 400),
       (100007, 'Гамбургер', 200),
       (100007, 'Кола', 250),
       (100008, 'Чизбургер', 300),
       (100008, 'Фанта', 500);

INSERT INTO votes (user_id, restaurant_id, date)
VALUES (100000, 100002, '2021-02-25'),
       (100000, 100002, '2021-02-26'),
       (100000, 100003, '2021-02-27'),
       (100001, 100002, '2021-02-26'),
       (100001, 100003, '2021-02-27');

INSERT INTO restaurants (name, address, imageUrl)
VALUES ('Орда', 'Мясницкая, 43, стр. 2', 'assets/images/orda.jpeg'),
       ('Plov Project', 'Малая Дмитровка, 20', 'assets/images/plov-project.jpeg'),
       ('Venting Cafe', 'Цветной бульвар, 34', 'assets/images/venting-cafe.jpg');