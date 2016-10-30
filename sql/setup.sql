/* drop all tables and functions */
DROP SCHEMA public CASCADE;
CREATE SCHEMA public;

/* Users table */
CREATE TABLE IF NOT EXISTS users (
  spotify_id         VARCHAR(50)   NOT NULL,
  email              VARCHAR(255)  NOT NULL,
  authorization_code VARCHAR(4000) NOT NULL,
  refresh_token      VARCHAR(4000) NOT NULL,
  access_token       VARCHAR(4000),
  expiration         TIMESTAMP,
  CONSTRAINT PK_users PRIMARY KEY (spotify_id)
);

/* Playlists table */
CREATE TABLE IF NOT EXISTS playlists (
  playlist_id   VARCHAR(50)  NOT NULL, --this is the master playlist id on spotify (ie LIT AF)
  playlist_name VARCHAR(255) NOT NULL,
  owner_id      VARCHAR(50)  NOT NULL,
  threshold     INTEGER      NOT NULL
    CHECK (threshold >= 0 AND threshold <= 100  ), --this is the threshold for each song
  CONSTRAINT PK_playlists PRIMARY KEY (playlist_id),
  CONSTRAINT FK_playlists_owner_id FOREIGN KEY (owner_id) REFERENCES users (spotify_id)
);

/* Playlists PR table */
CREATE TABLE IF NOT EXISTS playlists_pr (
  playlist_id        VARCHAR(50)  NOT NULL,
  playlist_name      VARCHAR(255) NOT NULL,
  owner_id           VARCHAR(50)  NOT NULL,
  parent_playlist_id VARCHAR(50)  NOT NULL,
  CONSTRAINT PK_playlists_pr PRIMARY KEY (playlist_id),
  CONSTRAINT FK_playlists_pr_owner_id FOREIGN KEY (owner_id) REFERENCES users (spotify_id),
  CONSTRAINT FK_playlists_pr_parent_playlist_id FOREIGN KEY (parent_playlist_id) REFERENCES playlists (playlist_id)
);

CREATE TABLE IF NOT EXISTS requests (
  request_id  SERIAL      NOT NULL,
  playlist_id VARCHAR(50) NOT NULL,
  spotify_id  VARCHAR(50) NOT NULL,
  song_id     VARCHAR(50) NOT NULL,
  CONSTRAINT PK_requests PRIMARY KEY (request_id),
  CONSTRAINT FK_requests_playlist_id FOREIGN KEY (playlist_id) REFERENCES playlists_pr (playlist_id),
  CONSTRAINT FK_requests_spotify_id FOREIGN KEY (spotify_id) REFERENCES users (spotify_id)
);

/* Master contributors table */
CREATE TABLE IF NOT EXISTS contributors (
  playlist_id    VARCHAR(50) NOT NULL,
  playlist_pr_id VARCHAR(50) NOT NULL,
  spotify_id     VARCHAR(50) NOT NULL,
  CONSTRAINT FK_contributors_playlist_id FOREIGN KEY (playlist_id) REFERENCES playlists (playlist_id),
  CONSTRAINT FK_contributors_playlist_pr_id FOREIGN KEY (playlist_pr_id) REFERENCES playlists_pr (playlist_id),
  CONSTRAINT FK_contributors_spotify_id FOREIGN KEY (spotify_id) REFERENCES users (spotify_id)
);

/* Vote table */
CREATE TABLE IF NOT EXISTS vote_table (
  request_id INTEGER NOT NULL,
  spotify_id VARCHAR(50) NOT NULL,
  vote       BOOLEAN     NOT NULL,
  CONSTRAINT FK_vote_request_id FOREIGN KEY (request_id) REFERENCES requests (request_id),
  CONSTRAINT FK_vote_spotify_id FOREIGN KEY (spotify_id) REFERENCES users (spotify_id)
);
