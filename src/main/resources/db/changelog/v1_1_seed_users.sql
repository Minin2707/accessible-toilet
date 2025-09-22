-- seed demo users for local/dev
INSERT INTO users (id, display_name, avatar_url, rating)
VALUES
    ('11111111-1111-1111-1111-111111111111','Alice',NULL,0),
    ('22222222-2222-2222-2222-222222222222','Bob',NULL,0),
    ('33333333-3333-3333-3333-333333333333','Carol',NULL,0)
    ON CONFLICT (id) DO NOTHING;