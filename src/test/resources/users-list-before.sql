DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM roles;

INSERT INTO studhub_test.public.roles VALUES
(1, now(), now(), 'ROLE_USER'), (2, now(), now(), 'ROLE_STUDENT'),  (3, now(), now(), 'ROLE_ADMIN')
ON CONFLICT DO NOTHING;

INSERT INTO studhub_test.public.users (id, created, last_modified, first_name, last_name, password, status, username) VALUES
(1, now(), now(), 'Ivan', 'Ivanov', '1234', 'ENABLED', 'admin'),
(2, now(), now(), 'Petr', 'Petrov', '1234', 'ENABLED', 'petr');

INSERT INTO studhub_test.public.user_roles (user_id, role_id) VALUES
(1, 1),
(1, 3),
(2, 1),
(2, 2);