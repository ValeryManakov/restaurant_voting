INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin'),
       ('User3', 'user3@yandex.ru', '{noop}password3'),
       ('User4', 'user4@yandex.ru', '{noop}password4'),
       ('User5', 'user5@yandex.ru', '{noop}password5'),
       ('User6', 'user6@yandex.ru', '{noop}password6'),
       ('User7', 'user7@yandex.ru', '{noop}password7'),
       ('User8', 'user8@yandex.ru', '{noop}password8'),
       ('User9', 'user9@yandex.ru', '{noop}password9'),
       ('User10', 'use10@yandex.ru', '{noop}password10'),
       ('User11', 'user11@yandex.ru', '{noop}password11'),
       ('User12', 'user12@yandex.ru', '{noop}password12');

INSERT INTO USER_ROLES (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2),
       ('USER', 3),
       ('USER', 4),
       ('USER', 5),
       ('USER', 6),
       ('USER', 7),
       ('USER', 8),
       ('USER', 9),
       ('USER', 10),
       ('USER', 11),
       ('USER', 12);

INSERT INTO RESTAURANTS (name)
VALUES ('Farfor'),
       ('Geronimo'),
       ('Spinasse');

INSERT INTO DISHES (name, price, restaurant_id)
VALUES ('Gyro', 112.50, 1),
       ('Onion soup', 143.18, 1),
       ('Meatballs', 171.25, 1),
       ('Chicken noodles', 150.13, 1),
       ('Tomato soup', 98.33, 2),
       ('Greek salad', 145.76, 2),
       ('Onion soup', 156.14, 2),
       ('Pork chop', 197.13, 2),
       ('Risotto', 114.78, 3),
       ('Roast', 200.19, 3),
       ('Vegetable soup', 171.13, 3),
       ('Sauce', 58.13, 3);

INSERT INTO VOTES (registered, user_id, restaurant_id)
VALUES (CURRENT_DATE(), 12, 1),
       (CURRENT_DATE(), 11, 1),
       (CURRENT_DATE(), 10, 1),
       (CURRENT_DATE(), 9, 1),
       (CURRENT_DATE(), 8, 2),
       (CURRENT_DATE(), 7, 2),
       (CURRENT_DATE(), 6, 3);
