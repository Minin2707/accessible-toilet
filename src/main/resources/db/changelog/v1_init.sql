-- Расширение PostGIS (для индексов/гео-функций)
CREATE EXTENSION IF NOT EXISTS postgis;

-- Пользователи
CREATE TABLE IF NOT EXISTS users (
                                     id              UUID            PRIMARY KEY,
                                     display_name    VARCHAR(120)    NOT NULL,
    avatar_url      VARCHAR(512),
    rating          INTEGER         NOT NULL DEFAULT 0,
    created_at      TIMESTAMPTZ     NOT NULL DEFAULT now()
    );

-- Места (туалеты)
CREATE TABLE IF NOT EXISTS places (
                                      id                   UUID            PRIMARY KEY,
                                      title                VARCHAR(120)    NOT NULL,
    description          VARCHAR(2000),
    -- для удобной работы в JPA (MVP) храним lat/lon как DOUBLE:
    lat                  DOUBLE PRECISION NOT NULL CHECK (lat BETWEEN -90 AND 90),
    lon                  DOUBLE PRECISION NOT NULL CHECK (lon BETWEEN -180 AND 180),
    -- а также генерим GEOGRAPHY для быстрых гео-запросов (индекс и ST_DWithin):
    location             GEOGRAPHY(POINT, 4326) GENERATED ALWAYS AS
(ST_SetSRID(ST_MakePoint(lon, lat), 4326)::geography) STORED,
    status               VARCHAR(16)     NOT NULL DEFAULT 'PENDING',  -- PENDING/ACTIVE/REJECTED
    confirmations_count  INTEGER         NOT NULL DEFAULT 0,
    rejections_count     INTEGER         NOT NULL DEFAULT 0,
    avg_stars            DOUBLE PRECISION NOT NULL DEFAULT 0,
    ratings_count        INTEGER         NOT NULL DEFAULT 0,
    author_id            UUID            NOT NULL REFERENCES users(id),
    created_at           TIMESTAMPTZ     NOT NULL DEFAULT now(),
    updated_at           TIMESTAMPTZ,
    CONSTRAINT chk_place_status CHECK (status IN ('PENDING','ACTIVE','REJECTED'))
    );

-- Индексы: гео и статус
CREATE INDEX IF NOT EXISTS idx_places_location_gist ON places USING GIST (location);
CREATE INDEX IF NOT EXISTS idx_places_status ON places (status);

-- Медиа
CREATE TABLE IF NOT EXISTS place_media (
                                           id          UUID            PRIMARY KEY,
                                           place_id    UUID            NOT NULL REFERENCES places(id) ON DELETE CASCADE,
    s3_key      VARCHAR(512)    NOT NULL,
    mime        VARCHAR(100),
    width       INTEGER,
    height      INTEGER,
    created_at  TIMESTAMPTZ     NOT NULL DEFAULT now()
    );
CREATE INDEX IF NOT EXISTS idx_media_place ON place_media(place_id);

-- Верификации (1 решение на юзера)
CREATE TABLE IF NOT EXISTS place_verifications (
                                                   id           UUID         PRIMARY KEY,
                                                   place_id     UUID         NOT NULL REFERENCES places(id) ON DELETE CASCADE,
    reviewer_id  UUID         NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    decision     VARCHAR(8)   NOT NULL,     -- APPROVE / REJECT
    comment      VARCHAR(500),
    created_at   TIMESTAMPTZ  NOT NULL DEFAULT now(),
    CONSTRAINT uq_verification UNIQUE (place_id, reviewer_id)
    );
CREATE INDEX IF NOT EXISTS idx_ver_place    ON place_verifications(place_id);
CREATE INDEX IF NOT EXISTS idx_ver_reviewer ON place_verifications(reviewer_id);

-- Оценки мест
CREATE TABLE IF NOT EXISTS place_ratings (
                                             id         UUID          PRIMARY KEY,
                                             place_id   UUID          NOT NULL REFERENCES places(id) ON DELETE CASCADE,
    user_id    UUID          NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    stars      INTEGER       NOT NULL CHECK (stars BETWEEN 1 AND 5),
    comment    VARCHAR(1000),
    created_at TIMESTAMPTZ   NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ,
    CONSTRAINT uq_rating UNIQUE (place_id, user_id)
    );
CREATE INDEX IF NOT EXISTS idx_rating_place ON place_ratings(place_id);
CREATE INDEX IF NOT EXISTS idx_rating_user  ON place_ratings(user_id);

-- Денормализованные счётчики для рейтинга пользователя (по желанию)
CREATE TABLE IF NOT EXISTS user_stats (
                                          user_id               UUID    PRIMARY KEY REFERENCES users(id) ON DELETE CASCADE,
    places_added_approved INTEGER NOT NULL DEFAULT 0,
    approvals_given       INTEGER NOT NULL DEFAULT 0,
    rejections_given      INTEGER NOT NULL DEFAULT 0,
    updated_at            TIMESTAMPTZ NOT NULL DEFAULT now()
    );
