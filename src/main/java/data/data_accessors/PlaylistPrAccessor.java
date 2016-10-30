package data.data_accessors;

import data.PlaylistPr;
import static db.tables.PlaylistsPr.PLAYLISTS_PR;

public class PlaylistPrAccessor extends Accessor {

    public PlaylistPrAccessor() {
        super();
    }

    public void insert(PlaylistPr playlistPr) {
        myQuery.insertInto(PLAYLISTS_PR, PLAYLISTS_PR.PLAYLIST_ID, PLAYLISTS_PR.PLAYLIST_NAME, PLAYLISTS_PR.OWNER_ID, PLAYLISTS_PR.PARENT_PLAYLIST_ID)
                .values(playlistPr.playlistId, playlistPr.playlistName, playlistPr.ownerId, playlistPr.parentPlaylistId).execute();
    }
    public boolean isExist(String playlistId) {
        return myQuery.select().from(PLAYLISTS_PR).where(PLAYLISTS_PR.PLAYLIST_ID.equal(playlistId)).fetchOne() != null;
    }

}
