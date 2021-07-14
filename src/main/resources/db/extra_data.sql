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

INSERT INTO restaurants (name, address, image_url)
VALUES ('Якитория', 'Новый Арбат, 22', 'assets/images/yakitoriya.jpeg'),
       ('Бургер Кинг', 'Авиамоторная, 34', 'assets/images/burger-king.jpeg'),
       ('Paulaner', 'Невский, 89', 'assets/images/paulaner.jpg'),
       ('Орда', 'Мясницкая, 43, стр. 2', 'assets/images/orda.jpg'),
       ('Plov Project', 'Малая Дмитровка, 20', 'assets/images/plov-project.jpg'),
       ('Venting Cafe', 'Цветной бульвар, 34', 'assets/images/venting-cafe.jpg');

INSERT INTO menus (restaurant_id, date)
VALUES (100002, now()),
       (100003, now()),
       (100004, now()),
       (100005, now()),
       (100006, now());

INSERT INTO dishes (menu_id, name, price)
VALUES (100008, 'Суши', 250),
       (100008, 'Рис', 300),
       (100008, 'Рыба', 350),
       (100008, 'Лапша', 400),
       (100009, 'Гамбургер', 200),
       (100009, 'Кола', 250),
       (100009, 'Чизбургер', 300),
       (100009, 'Фанта', 500),
       (100010, 'Paulaner светлое', 500),
       (100010, 'Немецкие колбаски', 600),
       (100011, 'Мясное ассорти', 600),
       (100011, 'Гуляш', 400),
       (100012, 'Плов куриный', 300),
       (100012, 'Плов из баранины', 500);