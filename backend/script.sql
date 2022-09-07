GRANT ALL PRIVILEGES ON DATABASE twitchflixdb TO fcd;

DO
$$
    DECLARE
        r RECORD;
    BEGIN
        FOR r IN (SELECT tablename FROM pg_tables WHERE schemaname = current_schema())
            LOOP
                EXECUTE 'DROP TABLE ' || quote_ident(r.tablename) || ' CASCADE';
            END LOOP;
    END
$$;

create table if not exists movie
(
    movie_id     serial
        primary key,
    title        varchar(100) not null,
    release_year integer,
    genre        varchar(100)[],
    duration     integer,
    link         varchar(100) not null,
    link_pic     varchar(200)
);

alter table movie
    owner to fcd;

create table if not exists users
(
    user_id  serial
        primary key,
    username varchar(50) UNIQUE,
    password varchar(100)
);

alter table users
    owner to fcd;

create table if not exists streams
(
    stream_id serial
        primary key,
    stream_username varchar(50) UNIQUE,
    stream_title varChar(50)
);

alter table streams
    owner to fcd;


INSERT INTO movie
VALUES (1,
        'Popeye the Sailor Meets Ali Baba’s Forty Thieves',
        1937,
        ARRAY ['Comedy','Animation'],
        1018,
        'https://storage.googleapis.com/movies-pdm/PopeyeAliBaba_512kb.mp4',
        'https://upload.wikimedia.org/wikipedia/en/thumb/5/58/PopeyeSailor_MeetsAliBabas_FortyThieves-onesheet.jpg/220px-PopeyeSailor_MeetsAliBabas_FortyThieves-onesheet.jpg'),
        (2,
        'Charlie Chaplin’s ”The Vagabond”',
        1916,
        ARRAY ['Comedy'],
        1483,
        'https://storage.googleapis.com/movies-pdm/CC_1916_07_10_TheVagabond.mp4',
        'https://upload.wikimedia.org/wikipedia/commons/thumb/c/cb/Charlie_Chaplin_Covers_the_World.jpg/200px-Charlie_Chaplin_Covers_the_World.jpg'),
       (4,
        'Night of the Living Dead',
        1968,
        ARRAY ['Sci-Fi','Horror'],
        5717,
        'https://storage.googleapis.com/movies-pdm/night_of_the_living_dead_512kb.mp4',
        'https://upload.wikimedia.org/wikipedia/en/thumb/9/91/Night_of_the_Living_Dead_%281968%29_poster.jpg/220px-Night_of_the_Living_Dead_%281968%29_poster.jpg')
ON CONFLICT DO NOTHING;

INSERT INTO movie VALUES
(3,
    'The Letter, Lego movie',
    2003,
    ARRAY ['Animation','LEGO'],
    390,
    'https://storage.googleapis.com/movies-pdm/tl_512kb.mp4')
ON CONFLICT DO NOTHING;

INSERT INTO users(username,password)
VALUES ('abcdef','cfb0bcd73aed79792efd31e5191cd112'),
       ('asdflkjh','cfb0bcd73aed79792efd31e5191cd112'),
       ('up201810097','cfb0bcd73aed79792efd31e5191cd112'),
       ('FCUP-DCC','cfb0bcd73aed79792efd31e5191cd112')
ON CONFLICT DO NOTHING;

SELECT username
FROM users
WHERE username = 'fcd';
SELECT *
FROM users