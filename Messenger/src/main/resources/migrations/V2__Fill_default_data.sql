insert into chat_user(email, user_name, password, active, created_at, last_time_online) values
('businessmail1710@mail.ru', 'Пётр', '{bcrypt}$2y$10$1amoPIu7BlgtkTshSjrQbeeSwqjLAE7vI7iOpzeSDlbXlBpWo0CJS', false, '2023-09-02 12:45:30', '2023-09-02 12:45:30'),
('example@mail.ru', 'Влад', '{bcrypt}$2y$10$ETBa83fecXgpHMhESpTMxOu0JVfI6o7q3V7dHzTQ2zd0ei8g53fdi', false, '2023-09-02 12:45:30', '2023-09-02 12:45:30'),
('lololo@mail.ru', 'Гриша', '{bcrypt}$2y$10$ETBa83fecXgpHMhESpTMxOu0JVfI6o7q3V7dHzTQ2zd0ei8g53fdi', false, '2023-09-02 12:45:30', '2023-09-02 12:45:30');

insert into user_role values
('businessmail1710@mail.ru', 'ROLE_SIMPLE'),
('example@mail.ru', 'ROLE_SIMPLE'),
('lololo@mail.ru', 'ROLE_SIMPLE');

insert into chat values
('7b1b558c-5939-48fa-9321-d8b42b18dfff', '2023-09-02 12:50:30'),
('15fccb66-e0ca-4249-aa34-7a3dd2f6af57', '2023-09-02 12:47:30'),
('cc7fb649-0514-4853-99f0-2acb86a43137', '2023-09-02 12:48:30');

insert into user_to_chat values
('7b1b558c-5939-48fa-9321-d8b42b18dfff', 'businessmail1710@mail.ru'),
('7b1b558c-5939-48fa-9321-d8b42b18dfff', 'example@mail.ru'),
('15fccb66-e0ca-4249-aa34-7a3dd2f6af57', 'businessmail1710@mail.ru'),
('15fccb66-e0ca-4249-aa34-7a3dd2f6af57', 'lololo@mail.ru'),
('cc7fb649-0514-4853-99f0-2acb86a43137', 'example@mail.ru'),
('cc7fb649-0514-4853-99f0-2acb86a43137', 'lololo@mail.ru');

insert into chat_message values
('543d6a9b-d4ff-4bda-a147-ee0bb9038ba5', '7b1b558c-5939-48fa-9321-d8b42b18dfff', 'businessmail1710@mail.ru', 'Привет Влад', '2023-09-02 12:45:30', 'READ'),
('dcc3df44-515d-4799-ad26-8d15d21b26e8', '7b1b558c-5939-48fa-9321-d8b42b18dfff', 'example@mail.ru', 'И тебе привет Пётр', '2023-09-02 12:50:30', 'READ'),
('d7c519f4-8f9f-48f5-8154-2a39b70cac42', '15fccb66-e0ca-4249-aa34-7a3dd2f6af57', 'businessmail1710@mail.ru', 'Ало', '2023-09-02 12:46:30', 'READ'),
('95ba53ba-94df-479e-a021-70c4e8e5bf8f', '15fccb66-e0ca-4249-aa34-7a3dd2f6af57', 'businessmail1710@mail.ru', 'Не игнорь', '2023-09-02 12:47:30', 'READ'),
('8a2d8965-ae68-4d80-8d81-4cd4ff7a0889', 'cc7fb649-0514-4853-99f0-2acb86a43137', 'example@mail.ru', 'Пётр утомил', '2023-09-02 12:47:30', 'READ'),
('642bd9ed-1442-4a6f-8824-f20f5ef0e45f', 'cc7fb649-0514-4853-99f0-2acb86a43137', 'lololo@mail.ru', 'Поддерживаю', '2023-09-02 12:48:30', 'READ');
