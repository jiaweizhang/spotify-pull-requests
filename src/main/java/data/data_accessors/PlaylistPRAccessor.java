package data.data_accessors;

import data.PlaylistPR;
import db.tables.PlaylistsPr;
/**
 * Created by Ankit on 10/29/2016.
 */
public class PlaylistPRAccessor extends Accessor {

    public PlaylistPRAccessor() {
        super();
    }

    public void insert(PlaylistPR playlistPR) {
        myQuery.insertInto(PlaylistsPr.PLAYLISTS_PR, PlaylistsPr.PLAYLISTS_PR.PLAYLIST_ID, PlaylistsPr.PLAYLISTS_PR.PLAYLIST_NAME, PlaylistsPr.PLAYLISTS_PR.OWNER_ID, PlaylistsPr.PLAYLISTS_PR.PARENT_PLAYLIST_ID)
                .values(playlistPR.playlistId, playlistPR.playlistName, playlistPR.ownerId, playlistPR.parentPlaylistId).execute();
    }
}
