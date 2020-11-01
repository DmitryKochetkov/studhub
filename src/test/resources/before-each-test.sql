DELETE FROM user_roles cascade;
DELETE FROM courses cascade;
DELETE FROM users cascade;
DELETE FROM roles cascade;
DELETE FROM ref_courses cascade;

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

INSERT INTO studhub_test.public.ref_courses (id, created, last_modified, title) VALUES
(1, now(), now(), 'Программирование на С++');

INSERT INTO studhub_test.public.courses (id, created, last_modified, course_status, course_type, user_id) VALUES
(1, now(), now(), 'ACTIVE', 1, 2);