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
(2, now(), now(), 'Petr', 'Petrov', '1234', 'ENABLED', 'petr'),
(3, now(), now(), 'Gregory', 'Moore', '1234', 'ENABLED', 'gregory_moore'),
(4, now(), now(), 'Edward', 'Weaver', '1234', 'ENABLED', 'edward_weaver'),
(5, now(), now(), 'Brian', 'Howard', '1234', 'ENABLED', 'brian_howard'),
(6, now(), now(), 'Abigail', 'Barnett', '1234', 'ENABLED', 'abigail_barnett'),
(7, now(), now(), 'Regina', 'Lyons', '1234', 'ENABLED', 'regina_lyons'),
(8, now(), now(), 'Michael', 'Fleming', '1234', 'ENABLED', 'michael_fleming'),
(9, now(), now(), 'Simon', 'Walker', '1234', 'ENABLED', 'simon_walker'),
(10, now(), now(), 'Francis', 'Gibson', '1234', 'ENABLED', 'francis_gibson'),
(11, now(), now(), 'John', 'Davis', '1234', 'ENABLED', 'john_davis'),
(12, now(), now(), 'Samuel', 'Todd', '1234', 'ENABLED', 'samuel_todd');

INSERT INTO studhub_test.public.user_roles (user_id, role_id) VALUES
(1, 1),
(1, 3),
(2, 1),
(2, 2);

INSERT INTO studhub_test.public.ref_courses (id, created, last_modified, title) VALUES
(1, now(), now(), 'Программирование на С++');

INSERT INTO studhub_test.public.courses (id, created, last_modified, course_status, course_type, user_id) VALUES
(1, now(), now(), 'ACTIVE', 1, 2);