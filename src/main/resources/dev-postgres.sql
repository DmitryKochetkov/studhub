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
(2),
(14);

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
(14, 1),
(14, 2);

INSERT INTO studhub.public.subjects (id, created, last_modified, title) VALUES
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

INSERT INTO studhub.public.specification (id, created, last_modified, title, subject_id) VALUES
(1, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 'Информатика ОГЭ до 2020', 7),
(2, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 'Информатика ОГЭ 2020', 7),
(3, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 'Информатика ЕГЭ до 2021', 5),
(4, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 'Информатика ЕГЭ 2021', 5);

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

INSERT INTO studhub.public.courses (id, created, last_modified, course_status, subject, student_id, active_specification_id) VALUES
(1, '2020-01-01 12:00:00', '2020-01-01 12:00:00', 'ACTIVE', 5, 2, 4),
(2, '2020-01-01 12:00:00', '2020-01-01 12:00:00', 'ACTIVE', 5, 14, 4);

INSERT INTO studhub.public.lessons (id, created, last_modified, start_date_time, status, topic, course_id) VALUES
(1, '2020-01-01 12:00:00', '2020-01-01 12:00:00', '2020-01-01 12:00:00', 'SCHEDULED', 'Topic 1', 1);

INSERT INTO studhub.public.homework (id, created, last_modified, deadline, description, course_id, lesson_id) VALUES
(1, '2020-01-01 13:00:00', '2020-01-01 13:00:00', '2023-02-01 12:00:00', 'Basics of linear algebra.', 1, 1),
(2, '2019-02-07 12:00:00', '2019-02-07 12:00:00', '2020-02-07 12:00:00', 'Basics of linear algebra 2.', 1, 1),
(3, '2020-01-01 13:00:00', '2020-01-01 13:00:00', '2020-07-14 12:00:00', 'Basics of linear algebra 3.', 1, 1),
(4, '2020-01-01 13:00:00', '2020-01-01 13:00:00', '2020-10-21 12:00:00', 'Basics of linear algebra 4.', 1, 1),
(5, '2020-01-01 13:00:00', '2020-01-01 13:00:00', '2020-10-28 12:00:00', 'Basics of linear algebra 4.', 1, 1),
(6, '2020-01-01 13:00:00', '2020-01-01 13:00:00', '2020-11-12 12:00:00', 'Basics of linear algebra 5.', 1, 1),
(7, '2020-01-01 13:00:00', '2020-01-01 13:00:00', '2020-11-28 12:00:00', 'Basics of linear algebra 6.', 1, 1);

INSERT INTO studhub.public.problems (id, created, last_modified, formulation, problem_type) VALUES
(1, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 'Find sum of 2 and 3.', 'short_answer_problem'),
(2, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 'Find 10 modulo 3.', 'short_answer_problem'),
(3, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 'What is the capital of Great Britain?', 'short_answer_problem'),
(4, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 'Какова алгоритмическая сложность алгоритма бинарного поиска?', 'choice_problem'),
(5, '2020-01-01 13:00:00', '2020-01-01 13:00:00', '10^2?', 'short_answer_problem'),
(6, '2020-01-01 13:00:00', '2020-01-01 13:00:00', '11^2?', 'short_answer_problem'),
(7, '2020-01-01 13:00:00', '2020-01-01 13:00:00', '15^2 + 3?', 'short_answer_problem'),
(8, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 'Сколько единиц в двоичной записи числа 41?', 'short_answer_problem');

INSERT INTO studhub.public.short_answer_problem (id, correct_answer) VALUES
(1, '5'),
(2, '1'),
(3, 'London'),
(5, '100'),
(6, '121'),
(7, '228'),
(8, '3');

INSERT INTO studhub.public.choice_problem (id) VALUES
(4);

INSERT INTO studhub.public.homework_problems (id, created, last_modified, homework_id, problem_id, number_in_homework, required, max_attempts) VALUES
(1, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 1, 1, 1, true, 2),
(2, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 1, 2, 2, true, 20),
(3, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 1, 3, 3, true, 100),
(4, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 1, 4, 4, true, 10),
(5, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 2, 5, 1, true, 10),
(6, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 2, 6, 2, true, 10),
(7, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 3, 6, 2, true, 10),
(8, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 4, 7, 2, true, 10),
(9, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 5, 7, 2, true, 10),
(10, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 6, 7, 2, true, 10),
(11, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 1, 8, 5, true, 10);

INSERT INTO studhub.public.choice_problem_answers (id, created, last_modified, choice_problem_id, text, correct) VALUES
(1, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 4, 'O(n)', false),
(2, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 4, 'O(log n)', true),
(3, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 4, 'O(n^2)', false),
(4, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 4, 'O(n!)', false);

INSERT INTO studhub.public.ref_verdict (id, created, last_modified, code, transcription, description) VALUES
(1, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 'OK', 'OK', 'Решение зачтено.'),
(2, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 'WA', 'Wrong answer', 'Неверный ответ.'),
(3, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 'CE', 'Compilation error', 'Ошибка компиляции.'),
(4, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 'PE', 'Presentation error', 'Ошибка представления (ответ не соответствует запрашиваемому формату).'),
(5, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 'TL', 'Time limit', 'Превышен установленный лимит времени.'),
(6, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 'ML', 'Memory limit', 'Превышен установленный лимит памяти.'),
(7, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 'RE', 'Runtime error', 'Ошибка времени выполнения.'),
(8, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 'SE', 'Server error', 'Внутренняя ошибка сервера. Статус решения неизвестен, решение будет перетестировано.');

INSERT INTO studhub.public.submissions (id, created, last_modified, answer, verdict_id, homework_problem_id) VALUES
(1, '2020-01-01 13:00:00', '2020-01-01 13:00:00', '3', 2, 1),
(2, '2020-01-01 14:00:00', '2020-01-01 13:00:00', '1', 2, 1),
(3, '2020-01-01 15:00:00', '2020-01-01 13:00:00', '3', 2, 2),
(4, '2020-01-01 13:00:00', '2020-01-01 13:00:00', '111', 2, 5),
(5, '2020-01-01 13:00:00', '2020-01-01 13:00:00', '111', 1, 6),
(6, '2020-01-01 13:00:00', '2020-01-01 13:00:00', '111', 1, 7),
(7, '2020-01-01 13:00:00', '2020-01-01 13:00:00', '111', 1, 7),
(8, '2020-01-01 13:00:00', '2020-01-01 13:00:00', '111', 2, 5),
(9, '2020-01-01 16:00:00', '2020-01-01 13:00:00', '33', 2, 11),
(10, '2020-01-01 17:00:00', '2020-01-01 13:00:00', '3', 1, 11);

INSERT INTO studhub.public.problem_codes (id, created, last_modified, specification_id, index_in_specification, description) VALUES
(1, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 3, 1, 'Позиционные системы счисления'),
(2, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 3, 2, 'Таблицы истинности'),
(3, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 3, 3, 'Информационные модели (графы)'),
(4, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 4, 1, 'Информационные модели (графы)'),
(5, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 4, 2, 'Таблицы истинности'),
(6, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 4, 3, 'Реляционные базы данных'),
(7, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 4, 4, 'Кодирование и декодирование символьной информации'),
(8, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 4, 5, 'Исполнители'),
(9, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 4, 6, 'Анализ программы с циклом'),
(10, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 4, 7, 'Кодирование, декодирование, передача текста и мультимедиа'),
(11, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 4, 8, 'Перебор слов'),
(12, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 4, 9, 'Excel-таблицы'),
(13, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 4, 10, 'Поиск средствами ОС или текстового редактора'),
(14, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 4, 11, 'Вычисление количества информации (пароли, автомобильные номера, и т.д.)'),
(15, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 4, 12, 'Исполнитель с циклом (Редактор, Чертежник, Робот)'),
(16, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 4, 13, 'Подсчёт количества путей в ориентированном графе'),
(17, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 4, 14, 'Позиционные системы счисления'),
(18, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 4, 15, 'Алгебра логики с параметром'),
(19, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 4, 16, 'Рекусивные функции и реккурентные вычисления'),
(20, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 4, 17, 'Программирование: однопроходный алгоритм по числовому промежутку'),
(21, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 4, 18, 'Динамическое программирование: сборщик монет'),
(22, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 4, 19, 'Выигрышная стратегия 1'),
(23, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 4, 20, 'Выигрышная стратегия 2'),
(24, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 4, 21, 'Выигрышная стратегия 3'),
(25, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 4, 22, 'Циклический алгоритм, перебирающий цифры числа'),
(26, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 4, 23, 'Динамика: подсчёт количества программ'),
(27, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 4, 24, 'Программирование: перебор символов текстового файла'),
(28, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 4, 25, 'Программирование: обработка числовых последовательностей'),
(29, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 4, 26, 'Программирование: сортировка'),
(30, '2020-01-01 13:00:00', '2020-01-01 13:00:00', 4, 27, 'Программирование: анализ числовых последовательностей');

INSERT INTO studhub.public.problem_code_mapping (problem_id, problem_code_id) VALUES
(8, 1),
(8, 14);