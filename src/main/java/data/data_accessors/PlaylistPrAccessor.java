package data.data_accessors;

import data.PlaylistPr;

import java.util.List;
import java.util.stream.Collectors;

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

    public PlaylistPr getPlaylistPr(String playlistId) {
        return myQuery.select().from(PLAYLISTS_PR).where(PLAYLISTS_PR.PLAYLIST_ID.equal(playlistId)).fetchOne()
                .map(record -> new PlaylistPr(
                        record.getValue(PLAYLISTS_PR.PLAYLIST_ID),
                        record.getValue(PLAYLISTS_PR.PLAYLIST_NAME),
                        record.getValue(PLAYLISTS_PR.OWNER_ID),
                        record.getValue(PLAYLISTS_PR.PARENT_PLAYLIST_ID)
                ));
    }

    public List<String> getPlaylistPrId() {
        return myQuery.select(PLAYLISTS_PR.PLAYLIST_ID).from(PLAYLISTS_PR).fetch()
                .stream().map(record -> record.getValue(PLAYLISTS_PR.PLAYLIST_ID)).collect(Collectors.toList());
    }

    public int delete(String playlistId) {
        return myQuery.delete(PLAYLISTS_PR).where(PLAYLISTS_PR.PLAYLIST_ID.equal(playlistId)).execute();
    }


}
