INSERT INTO studhub.public.roles VALUES
    (1, now(), now(), 'ROLE_USER'), (2, now(), now(), 'ROLE_STUDENT'),  (3, now(), now(), 'ROLE_ADMIN')
    ON CONFLICT DO NOTHING;

INSERT INTO studhub.public.users VALUES
    (1, now(), now(), 'Dmitry', 'Kochetkov', '1234', 'ENABLED', 'admin')
    ON CONFLICT DO NOTHING;

INSERT INTO studhub.public.user_roles VALUES
    (1, 3)
    ON CONFLICT DO NOTHING;

INSERT INTO studhub.public.ref_courses VALUES
    (1, now(), now(), 'Программирование на C++'),
    (2, now(), now(), 'Программирование на Pascal'),
    (3, now(), now(), 'Программирование на Java'),
    (4, now(), now(), 'Олимпиадное программирование'),
    (5, now(), now(), 'Информатика (ЕГЭ)'),
    (6, now(), now(), 'Математика (ЕГЭ)'),
    (7, now(), now(), 'Информатика (ОГЭ)'),
    (8, now(), now(), 'Математика (ОГЭ)'),
    (9, now(), now(), 'Математический анализ'),
    (10, now(), now(), 'Линейная алгебра')
    ON CONFLICT DO NOTHING;