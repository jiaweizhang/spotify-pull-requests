package data.data_accessors;

import data.MasterPlaylist;
import data.Song;
import org.jooq.Record;
import org.jooq.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static db.tables.MasterContributors.MASTER_CONTRIBUTORS;
import static db.tables.MasterPlaylists.MASTER_PLAYLISTS;
import static db.tables.MasterSongs.MASTER_SONGS;

/**
 * Created by Ankit on 10/29/2016.
 */
public class MasterPlaylistAccessor extends Accessor {

    public MasterPlaylistAccessor() {
        super();
    }

    /*
    create master playlist
     */
    public void create(String playlistID, String ownerID, int threshold) {
        myQuery.insertInto(MASTER_PLAYLISTS, MASTER_PLAYLISTS.PLAYLIST_ID, MASTER_PLAYLISTS.OWNER_ID, MASTER_PLAYLISTS.THRESHOLD).values(playlistID, ownerID, threshold).execute();
    }

    public boolean isExist(String playlistID) {
        return myQuery.select().from(MASTER_PLAYLISTS).where(MASTER_PLAYLISTS.PLAYLIST_ID.equal(playlistID)).fetchOne() != null;
    }

    public MasterPlaylist retrieve(String playlistID) {
        Record masterPlaylistResult = myQuery.select().from(MASTER_PLAYLISTS).where(MASTER_PLAYLISTS.PLAYLIST_ID.equal(playlistID)).fetchOne();
        Result<Record> masterSongResult = myQuery.select().from(MASTER_SONGS).where(MASTER_SONGS.PLAYLIST_ID.equal(playlistID)).fetch();
        Result<Record> masterContributors = myQuery.select().from(MASTER_CONTRIBUTORS).where(MASTER_CONTRIBUTORS.PLAYLIST_ID.equal(playlistID)).fetch();

        String listID = masterPlaylistResult.getValue(MASTER_PLAYLISTS.PLAYLIST_ID);
        String ownerID = masterPlaylistResult.getValue(MASTER_PLAYLISTS.OWNER_ID);
        String name = masterPlaylistResult.getValue(MASTER_PLAYLISTS.PLAYLIST_NAME);
        int threshold = masterPlaylistResult.getValue(MASTER_PLAYLISTS.THRESHOLD);
        List<Song> songList = new ArrayList<Song>();
        List<String> collabs = new ArrayList<String>();
        for (Record record : masterSongResult) {
            int id = record.getValue(MASTER_SONGS.ID);
            String contributor = record.getValue(MASTER_SONGS.CONTRIBUTOR);
            String songID = record.getValue(MASTER_SONGS.SONG_ID);
            Song song = new Song(id, listID, contributor, songID);
            songList.add(song);
        }
        for (Record record : masterContributors) {
            collabs.add(record.getValue(MASTER_CONTRIBUTORS.COLLAB_ID));
        }
        return new MasterPlaylist(listID, ownerID, threshold, songList, collabs, name);
    }

    public List<MasterPlaylist> getPlaylistIDFromUser(String spotifyUserId){
        List<String> playlistIDs = myQuery.select(MASTER_CONTRIBUTORS.PLAYLIST_ID).from(MASTER_CONTRIBUTORS).where(MASTER_CONTRIBUTORS.COLLAB_ID.equal(spotifyUserId))
                .fetch().stream().map(record -> record.getValue(MASTER_CONTRIBUTORS.PLAYLIST_ID)).collect(Collectors.toList());
        Result<Record> playlistRecord = myQuery.select().from(MASTER_PLAYLISTS).where(MASTER_PLAYLISTS.PLAYLIST_ID.in(playlistIDs)).fetch();
        return playlistRecord.stream().map(
                record -> new MasterPlaylist(
                        record.getValue(MASTER_PLAYLISTS.PLAYLIST_ID),
                        record.getValue(MASTER_PLAYLISTS.OWNER_ID),
                        record.getValue(MASTER_PLAYLISTS.THRESHOLD),
                        record.getValue(MASTER_PLAYLISTS.PLAYLIST_NAME)
                )
        ).collect(Collectors.toList());
    }

    public void addUserToPlaylist(String playlistID, String collabID) {
        myQuery.insertInto(MASTER_CONTRIBUTORS, MASTER_CONTRIBUTORS.PLAYLIST_ID, MASTER_CONTRIBUTORS.COLLAB_ID)
                .values(playlistID, collabID).execute();
    }

}
