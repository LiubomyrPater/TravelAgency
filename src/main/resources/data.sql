INSERT into role(name)
VALUES
  ('ROLE_USER'),
  ('ROLE_MANAGER');


INSERT into city(name)
VALUES
  ('Lviv'),
  ('Odesa'),
  ('Kyiv'),
  ('Kharkiv');

INSERT into hotel (id, name, city_id)
VALUES
(1, 'Hilton', 1),
(2, 'Hyatt', 2),
(3, 'Ritz', 3),
(4, 'Perlyna', 4),
(5, 'Hilton', 4),
(6, 'Hyatt', 3),
(7, 'Ritz', 2),
(8, 'Natali', 1);


INSERT into room (number,  hotel_id, type_id)
VALUES
  ('1A', 1, 4),
  ('1', 7, 1),
  ('100', 3, 3),
  ('1213', 1, 1),
  ('1', 2, 2),
  ('2', 8, 7);

insert into user (id, email, enabled, name, password, phone_number, surname)
values
  (1, 'user@gmail.com', true, 'User', '$2a$10$nA/td1s1d3DM.myEsS8Xt.HzbyjL4nlqq.BY8AkPCzW6/wSN9hPXW', '+380971234567', 'User'),
  (2, 'manager@gmail.com', true, 'Manager', '$2a$10$lnxoYuBG.hXB4eneimyyeubrBY7MIAn6i5qCGpDVGAbAi6DXqS1Tm', '+380977654321','Manager');

insert into user_role (user_id, role_id)
VALUES
  (1, 1),
  (2, 2);