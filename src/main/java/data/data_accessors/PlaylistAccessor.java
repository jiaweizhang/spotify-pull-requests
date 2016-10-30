package data.data_accessors;

import data.Playlist;
import db.tables.Playlists;

public class PlaylistAccessor extends Accessor {

    public PlaylistAccessor() {
        super();
    }

    public boolean isExist(String playlistId) {
        return myQuery.select().from(Playlists.PLAYLISTS).where(Playlists.PLAYLISTS.PLAYLIST_ID.equal(playlistId)).fetchOne() != null;
    }

    public void addPlaylist(Playlist playlist) {
        myQuery.insertInto(Playlists.PLAYLISTS, Playlists.PLAYLISTS.PLAYLIST_ID, Playlists.PLAYLISTS.PLAYLIST_NAME, Playlists.PLAYLISTS.OWNER_ID, Playlists.PLAYLISTS.THRESHOLD)
                .values(playlist.playlistId, playlist.playlistName, playlist.ownerId, playlist.threshold).execute();
    }
}
