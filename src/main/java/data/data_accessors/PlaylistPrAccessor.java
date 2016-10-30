package data.data_accessors;

import data.PlaylistPr;
import db.tables.PlaylistsPr;

public class PlaylistPrAccessor extends Accessor {

    public PlaylistPrAccessor() {
        super();
    }

    public void insert(PlaylistPr playlistPr) {
        myQuery.insertInto(PlaylistsPr.PLAYLISTS_PR, PlaylistsPr.PLAYLISTS_PR.PLAYLIST_ID, PlaylistsPr.PLAYLISTS_PR.PLAYLIST_NAME, PlaylistsPr.PLAYLISTS_PR.OWNER_ID, PlaylistsPr.PLAYLISTS_PR.PARENT_PLAYLIST_ID)
                .values(playlistPr.playlistId, playlistPr.playlistName, playlistPr.ownerId, playlistPr.parentPlaylistId).execute();
    }
    public boolean isExist(String playlistId) {
        return myQuery.select().from(PlaylistsPr.PLAYLISTS_PR).where(PlaylistsPr.PLAYLISTS_PR.PLAYLIST_ID.equal(playlistId)).fetchOne() != null;
    }

}
