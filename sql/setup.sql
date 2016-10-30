/* drop all tables and functions */
DROP SCHEMA public CASCADE;
CREATE SCHEMA public;

/* Users table */
CREATE TABLE IF NOT EXISTS users (
  spotify_userid     VARCHAR(50)  NOT NULL,
  email              VARCHAR(255) NOT NULL,
  authorization_code VARCHAR(4000) NOT NULL,
  refresh_token      VARCHAR(4000) NOT NULL,
  access_token       VARCHAR(4000),
  expiration         TIMESTAMP,
  CONSTRAINT PK_users PRIMARY KEY (spotify_userid)
);


/* PR Playlists table */
CREATE TABLE IF NOT EXISTS master_playlists (
  playlist_id VARCHAR(50) NOT NULL, --this is the master playlist id on spotify (ie LIT AF)
  playlist_name VARCHAR(255) NOT NULL,
  owner_id    VARCHAR(50) NOT NULL,
  threshold   INTEGER     NOT NULL
    CHECK (threshold >= 0 AND threshold <= 100  ), --this is the threshold for each song
  CONSTRAINT PK_master_playlists PRIMARY KEY (playlist_id),
  CONSTRAINT FK_master_playlists_owner_id FOREIGN KEY (owner_id) REFERENCES users (spotify_userid)
);

CREATE TABLE IF NOT EXISTS master_songs (
  id          SERIAL      NOT NULL,
  playlist_id VARCHAR(50) NOT NULL,
  contributor VARCHAR(50) NOT NULL,
  song_id     VARCHAR(50) NOT NULL,
  CONSTRAINT PK_master_songs PRIMARY KEY (id),
  CONSTRAINT FK_master_songs_playlist_id FOREIGN KEY (playlist_id) REFERENCES master_playlists (playlist_id),
  CONSTRAINT FK_master_songs_contributor FOREIGN KEY (contributor) REFERENCES users (spotify_userid)
);

/* Master contributors table */
CREATE TABLE IF NOT EXISTS master_contributors (
  playlist_id VARCHAR(50) NOT NULL,
  collab_id VARCHAR(50) NOT NULL,
  CONSTRAINT FK_master_contributors_playlist_id FOREIGN KEY (playlist_id) REFERENCES master_playlists(playlist_id),
  CONSTRAINT FK_master_contributors_collab_id FOREIGN KEY (collab_id) REFERENCES users(spotify_userid)
);

/* Individual PR Playlists table */
CREATE TABLE IF NOT EXISTS individual_playlists (
  spotify_playlist_id VARCHAR(50) NOT NULL,
  owner_id            VARCHAR(50) NOT NULL,
  master_id           VARCHAR(50) NOT NULL,
  CONSTRAINT PK_individual_playlists PRIMARY KEY (spotify_playlist_id),
  CONSTRAINT FK_individual_playlists_master_id FOREIGN KEY (master_id) REFERENCES master_playlists (playlist_id)
);


/* Vote table */
CREATE TABLE IF NOT EXISTS vote_table (
  song_id  INTEGER     NOT NULL,
  voted_by VARCHAR(50) NOT NULL,
  vote     BOOLEAN     NOT NULL,
  CONSTRAINT FK_vote_table_song_id FOREIGN KEY (song_id) REFERENCES master_songs (id),
  CONSTRAINT FK_vote_table_voted_by FOREIGN KEY (voted_by) REFERENCES users (spotify_userid)
);



/* Upvote/downvote table */