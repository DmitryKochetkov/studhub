-- noinspection SqlWithoutWhereForFile


INSERT INTO studhub.public.roles (id, created, last_modified, name) VALUES
(1, '2020-01-01 12:00:00', '2020-01-01 12:00:00', 'ROLE_USER'),
(2, '2020-01-01 12:00:00', '2020-01-01 12:00:00', 'ROLE_STUDENT'),
(3, '2020-01-01 12:00:00', '2020-01-01 12:00:00', 'ROLE_ADMIN');

INSERT INTO studhub.public.users (id, created, last_modified, first_name, last_name, password, status, username) VALUES
(1, '2020-01-01 12:00:00', '2020-01-01 12:00:00', 'Ivan', 'Ivanov', '1234', 'ENABLED', 'admin'),
(2, '2020-01-01 12:00:00', '2020-01-01 12:00:00', 'Petr', 'Petrov', '1234', 'ENABLED', 'petr'),
(3, '2020-01-01 12:00:00', '2020-01-01 12:00:00', 'Gregory', 'Moore', '1234', 'ENABLED', 'gregory_moore'),
(4, '2020-01-01 12:00:00', '2020-01-01 12:00:00', 'Edward', 'Weaver', '1234', 'ENABLED', 'edward_weaver'),
(5, '2020-01-01 12:00:00', '2020-01-01 12:00:00', 'Brian', 'Howard', '1234', 'ENABLED', 'brian_howard'),
(6, '2020-01-01 12:00:00', '2020-01-01 12:00:00', 'Abigail', 'Barnett', '1234', 'ENABLED', 'abigail_barnett'),
(7, '2020-01-01 12:00:00', '2020-01-01 12:00:00', 'Regina', 'Lyons', '1234', 'ENABLED', 'regina_lyons'),
(8, '2020-01-01 12:00:00', '2020-01-01 12:00:00', 'Michael', 'Fleming', '1234', 'ENABLED', 'michael_fleming'),
(9, '2020-01-01 12:00:00', '2020-01-01 12:00:00', 'Simon', 'Walker', '1234', 'ENABLED', 'simon_walker'),
(10, '2020-01-01 12:00:00', '2020-01-01 12:00:00', 'Francis', 'Gibson', '1234', 'ENABLED', 'francis_gibson'),
(11, '2020-01-01 12:00:00', '2020-01-01 12:00:00', 'John', 'Davis', '1234', 'ENABLED', 'john_davis'),
(12, '2020-01-01 12:00:00', '2020-01-01 12:00:00', 'Samuel', 'Todd', '1234', 'ENABLED', 'samuel_todd'),
(13, '2020-01-01 12:00:00', '2020-01-01 12:00:00', 'Alan', 'Curtis', '1234', 'ENABLED', 'alan_curtis'),
(14, '2020-01-01 12:00:00', '2020-01-01 12:00:00', 'Robert', 'Shaw', '1234', 'ENABLED', 'robert_shaw');

INSERT INTO students (id) VALUES
(2);

INSERT INTO studhub.public.user_roles (user_id, role_id) VALUES
(1, 1),
(1, 3),
(2, 1),
(2, 2),
(3, 1),
(4, 1),
(5, 1),
(6, 1),
(7, 1),
(8, 1),
(9, 1),
(10, 1),
(11, 1),
(12, 1),
(13, 1),
(14, 1);

INSERT INTO studhub.public.ref_courses (id, created, last_modified, title) VALUES
(1, '2020-01-01 12:00:00', '2020-01-01 12:00:00', 'Программирование на С++'),
(2, '2020-01-01 12:00:00', '2020-01-01 12:00:00', 'Программирование на Pascal'),
(3, '2020-01-01 12:00:00', '2020-01-01 12:00:00', 'Программирование на Java'),
(4, '2020-01-01 12:00:00', '2020-01-01 12:00:00', 'Олимпиадное программирование'),
(5, '2020-01-01 12:00:00', '2020-01-01 12:00:00', 'Информатика (ЕГЭ)'),
(6, '2020-01-01 12:00:00', '2020-01-01 12:00:00', 'Математика (ЕГЭ)'),
(7, '2020-01-01 12:00:00', '2020-01-01 12:00:00', 'Информатика (ОГЭ)'),
(8, '2020-01-01 12:00:00', '2020-01-01 12:00:00', 'Математика (ОГЭ)'),
(9, '2020-01-01 12:00:00', '2020-01-01 12:00:00', 'Математический анализ'),
(10, '2020-01-01 12:00:00', '2020-01-01 12:00:00', 'Линейная алгебра');

INSERT INTO studhub.public.courses (id, created, last_modified, course_status, course_type, student_id) VALUES
(1, '2020-01-01 12:00:00', '2020-01-01 12:00:00', 'ACTIVE', 5, 2);

INSERT INTO studhub.public.followers (user_id, follower_id) VALUES
(2, 3),
(2, 4),
(2, 5),
(2, 6),
(2, 7),
(2, 8),
(2, 9),
(2, 10),
(2, 11),
(2, 12),
(2, 13),
(2, 14);

INSERT INTO studhub.public.lessons (id, created, last_modified, start_date_time, status, topic, course_id) VALUES
(1, '2020-01-01 12:00:00', '2020-01-01 12:00:00', '2020-01-01 12:00:00', 'SCHEDULED', 'Topic 1', 1);

INSERT INTO studhub.public.homework (id, created, last_modified, deadline, description, course_id, lesson_id) VALUES
(1, '2020-01-01 13:00:00', '2020-01-01 13:00:00', '2020-01-07 12:00:00', 'Basics of linear algebra.', 1, 1);

INSERT INTO studhub.public.abstract_problem (id, created, last_modified, formulation, problem_type) VALUES
(1, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 'Find sum of 2 and 3.', 'short_answer_problem'),
(2, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 'Find 10 modulo 3.', 'short_answer_problem'),
(3, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 'What is the capital of Great Britain?', 'short_answer_problem'),
(4, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 'Какова алгоритмическая сложность алгоритма бинарного поиска?', 'choice_problem');

INSERT INTO studhub.public.short_answer_problem (id, answer) VALUES
(1, '5'),
(2, '1'),
(3, 'London');

INSERT INTO studhub.public.choice_problem (id, multiple) VALUES
(4, false);

INSERT INTO studhub.public.homework_problems (id, created, last_modified, homework_id, problem_id, number_in_homework, required, max_attempts, used_attempts) VALUES
(1, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 1, 1, 1, true, 2, 0),
(2, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 1, 2, 2, true, 20, 0),
(3, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 1, 3, 3, true, 100, 0),
(4, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 1, 4, 4, true, 1, 0);

INSERT INTO studhub.public.choice_problem_answers (choice_problem_id, answers) VALUES
(4, 'O(n)'),
(4, 'O(log n)'),
(4, 'O(n^2)'),
(4, 'O(n!)');

INSERT INTO studhub.public.submissions (id, created, last_modified, answer, verdict, homework_problem_id) VALUES
(1, '2020-01-01 13:00:00', '2020-01-01 13:00:00', '3', 'WA', 1),
(2, '2020-01-01 13:00:00', '2020-01-01 13:00:00', '1', 'WA', 1),
(3, '2020-01-01 13:00:00', '2020-01-01 13:00:00', '3', 'WA', 2);