INSERT INTO studhub.public.roles VALUES
    (1, now(), now(), 'ROLE_USER'), (2, now(), now(), 'ROLE_STUDENT'),  (3, now(), now(), 'ROLE_ADMIN')
    ON CONFLICT DO NOTHING;