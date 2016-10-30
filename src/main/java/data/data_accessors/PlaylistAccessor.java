package data.data_accessors;

import data.Playlist;

import static db.tables.Playlists.PLAYLISTS;

public class PlaylistAccessor extends Accessor {

    public PlaylistAccessor() {
        super();
    }

    public boolean isExist(String playlistId) {
        return myQuery.select().from(PLAYLISTS).where(PLAYLISTS.PLAYLIST_ID.equal(playlistId)).fetchOne() != null;
    }

    public void addPlaylist(Playlist playlist) {
        myQuery.insertInto(PLAYLISTS, PLAYLISTS.PLAYLIST_ID, PLAYLISTS.PLAYLIST_NAME, PLAYLISTS.OWNER_ID, PLAYLISTS.THRESHOLD)
                .values(playlist.playlistId, playlist.playlistName, playlist.ownerId, playlist.threshold).execute();
    }
}
