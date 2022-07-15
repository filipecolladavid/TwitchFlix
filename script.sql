
GRANT ALL PRIVILEGES ON DATABASE twitchflixdb TO fcd;

DO $$ DECLARE
    r RECORD;
BEGIN
    FOR r IN (SELECT tablename FROM pg_tables WHERE schemaname = current_schema()) LOOP
            EXECUTE 'DROP TABLE ' || quote_ident(r.tablename) || ' CASCADE';
        END LOOP;
END $$;

create table if not exists movie
(
    movie_id     serial
        primary key,
    title        varchar(100) not null,
    release_year integer,
    genre        varchar(100)[],
    duration     integer,
    link         varchar(100) not null
);

alter table movie
    owner to fcd;

create table if not exists users
(
    user_id  serial
        primary key,
    username varchar(50) UNIQUE
);

alter table users
    owner to fcd;

create table if not exists stream
(
    stream_id integer not null
        primary key,
    user_id   integer not null
        references users
);

alter table stream
    owner to fcd;


INSERT INTO movie VALUES(
                          1,
                          'Popeye the Sailor Meets Ali Baba’s Forty Thieves',
                          1937,
                          ARRAY ['Comedy','Animation'],
                          1018,
                          'https://storage.cloud.google.com/movies-pdm/PopeyeAliBaba_512kb.mp4'
),
                          (
                          2,
                          'Charlie Chaplin’s ”The Vagabond”',
                          1916,
                          ARRAY ['Comedy'],
                          1483,
                          'https://storage.cloud.google.com/movies-pdm/CC_1916_07_10_TheVagabond.mp4'
),
                         (
                         3,
                         'The Letter, Lego movie',
                         2003,
                         ARRAY ['Animation','LEGO'],
                         390,
                         'https://storage.cloud.google.com/movies-pdm/tl_512kb.mp4'
),
                         (
                          4,
                          'Night of the Living Dead',
                          1968,
                          ARRAY ['Sci-Fi','Horror'],
                          5717,
                          'https://storage.cloud.google.com/movies-pdm/night_of_the_living_dead_512kb.mp4'
                        )
ON CONFLICT DO NOTHING;

INSERT INTO users(username) VALUES(
                         'abcdef'
                        ),
                        (
                         'asdflkjh'
                        ),
                        (
                         'up201810097'
                        ),
                        (
                         'FCUP-DCC'
                        )
ON CONFLICT DO NOTHING;

INSERT INTO users(username) VALUES ('sdfslkdfj');

SELECT username FROM users WHERE username = 'fcd';
SELECT * FROM users