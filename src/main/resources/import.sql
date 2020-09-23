INSERT INTO studhub.public.roles VALUES
    (1, now(), now(), 'ROLE_USER'), (2, now(), now(), 'ROLE_STUDENT'),  (3, now(), now(), 'ROLE_ADMIN')
    ON CONFLICT DO NOTHING;

INSERT INTO studhub.public.users VALUES
    (1, now(), now(), 'Dmitry', 'Kochetkov', '1234', 'ENABLED', 'admin')
    ON CONFLICT DO NOTHING;

INSERT INTO studhub.public.user_roles VALUES
    (1, 3)
    ON CONFLICT DO NOTHING;