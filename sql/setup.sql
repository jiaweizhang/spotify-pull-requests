/* drop all tables and functions */
DROP SCHEMA public CASCADE;
CREATE SCHEMA public;

/* Users table */
CREATE TABLE IF NOT EXISTS users (
  spotify_userid     VARCHAR(50),
  email              VARCHAR(255),
  authorization_code VARCHAR(255) NOT NULL,
  refresh_token      VARCHAR(255) NOT NULL,
  access_token       VARCHAR(255),
  CONSTRAINT PK_users PRIMARY KEY (spotify_userid),
  CONSTRAINT UQ_users UNIQUE (email, authorization_code, refresh_token, access_token)
);


/* PR Playlists table */
CREATE TABLE IF NOT EXISTS master_playlists (
  playlist_id VARCHAR(50), --this is the master playlist id on spotify (ie LIT AF)
  owner_id    VARCHAR(50),
  threshold   INTEGER CHECK (threshold >= 0 AND threshold <= 100), --this is the threshold for each song
  CONSTRAINT FK_master_playlists_owner_id FOREIGN KEY (owner_id) REFERENCES users (spotify_userid)
);

CREATE TABLE IF NOT EXISTS master_songs (
  id          SERIAL,
  playlist_id VARCHAR(50),
  contributor VARCHAR(50),
  song_id     VARCHAR(50),
  CONSTRAINT PK_master_songs PRIMARY KEY (id),
  CONSTRAINT FK_master_songs_playlist_id FOREIGN KEY (playlist_id) REFERENCES master_playlist (playlist_id),
  CONSTRAINT FK_master_songs_contributor FOREIGN KEY (contributor) REFERENCES users (spotify_userid)
);

/* Individual PR Playlists table */
CREATE TABLE IF NOT EXISTS individual_playlists (
  spotify_playlist_id VARCHAR(50),
  owner_id            VARCHAR(50),
  master_id           VARCHAR(50),
  CONSTRAINT PK_individual_playlists PRIMARY KEY (spotify_playlist_id),
  CONSTRAINT FK_individual_playlists_master_id FOREIGN KEY (master_id) REFERENCES master_playlists (playlist_id)
);


/* Vote table */
CREATE TABLE IF NOT EXISTS vote_table (
  song_id  INTEGER,
  voted_by VARCHAR(50),
  vote     BOOLEAN,
  CONSTRAINT FK_vote_table_song_id FOREIGN KEY (song_id) REFERENCES master_songs (id),
  CONSTRAINT FK_vote_table_voted_by FOREIGN KEY (voted_by) REFERENCES users (spotify_userid)
);

/* Upvote/downvote table */