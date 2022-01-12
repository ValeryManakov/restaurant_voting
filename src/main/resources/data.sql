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
       ('User10', 'user10@yandex.ru', '{noop}password10'),
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

INSERT INTO DISHES (name, registered, price, restaurant_id)
VALUES ('Dish01', CURRENT_DATE(), 100.00, 1),
       ('Dish02', CURRENT_DATE(), 100.50, 1),
       ('Dish03', CURRENT_DATE(), 101.00, 1),
       ('Dish04', CURRENT_DATE(), 101.50, 1),
       ('Dish05', CURRENT_DATE(), 102.00, 2),
       ('Dish06', CURRENT_DATE(), 102.50, 2),
       ('Dish07', CURRENT_DATE(), 103.00, 2),
       ('Dish08', CURRENT_DATE(), 103.50, 2),
       ('Dish09', CURRENT_DATE(), 104.00, 3),
       ('Dish10', CURRENT_DATE(), 104.50, 3),
       ('Dish11', CURRENT_DATE(), 105.00, 3),
       ('Dish12', CURRENT_DATE(), 105.50, 3),
       ('Dish13', CURRENT_DATE() - 1, 106.00, 1),
       ('Dish14', CURRENT_DATE() - 1, 106.50, 2),
       ('Dish15', CURRENT_DATE() - 1, 107.00, 3);

INSERT INTO VOTES (registered, user_id, restaurant_id)
VALUES (CURRENT_DATE(), 12, 1),
       (CURRENT_DATE(), 11, 1),
       (CURRENT_DATE(), 10, 1),
       (CURRENT_DATE(), 9, 1),
       (CURRENT_DATE(), 8, 2),
       (CURRENT_DATE(), 7, 2),
       (CURRENT_DATE(), 6, 2),
       (CURRENT_DATE(), 5, 3),
       (CURRENT_DATE() - 1, 4, 3),
       (CURRENT_DATE() - 1, 3, 3);
