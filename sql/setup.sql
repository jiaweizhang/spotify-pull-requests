/* drop all tables and functions */
DROP SCHEMA public CASCADE;
CREATE SCHEMA public;

/* Users table */
CREATE TABLE users (
  spotify_userid     VARCHAR(50) PRIMARY KEY,
  email              VARCHAR(255),
  authorization_code VARCHAR(255) NOT NULL,
  refresh_token      VARCHAR(255) NOT NULL,
  access_token       VARCHAR(255)
);


/* PR Playlists table */
CREATE TABLE master_playlists (
  playlist_id VARCHAR(50), --this is the master playlist id on spotify (ie LIT AF)
  owner_id    VARCHAR(50) REFERENCES users (spotify_userid),
  threshold   INTEGER CHECK (threshold >= 0 AND threshold <= 100) --this is the threshold for each song
);

CREATE TABLE master_songs (
  id          SERIAL PRIMARY KEY,
  playlist_id VARCHAR(50) REFERENCES master_playlists (playlist_id),
  contributor VARCHAR(50) REFERENCES users (spotify_userid),
  song_id     VARCHAR(50)
);

/* Individual PR Playlists table */
CREATE TABLE individual_playlists (
  spotify_playlist_id VARCHAR(50) PRIMARY KEY,
  owner_id            VARCHAR(50),
  master_id           VARCHAR(50) REFERENCES master_playlists (playlist_id)
);


/* Vote table */
CREATE TABLE vote_table (
  song_id  INTEGER REFERENCES master_songs (id),
  voted_by VARCHAR(50) REFERENCES users (spotify_userid),
  vote     BOOLEAN
);

/* Upvote/downvote table */