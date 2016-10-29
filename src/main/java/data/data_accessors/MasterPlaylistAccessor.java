package data.data_accessors;

import data.MasterPlaylist;
import data.Song;
import org.jooq.Record;
import org.jooq.Result;

import java.util.List;
import java.util.ArrayList;
import static db.tables.MasterPlaylists.MASTER_PLAYLISTS;
import static db.tables.MasterSongs.MASTER_SONGS;
import static db.tables.MasterContributors.MASTER_CONTRIBUTORS;
/**
 * Created by Ankit on 10/29/2016.
 */
public class MasterPlaylistAccessor extends Accessor {

    public MasterPlaylistAccessor () {
        super();
    }
    /*
    create master playlist
     */
    public void create(String playlistID, String ownerID, int threshold, List<Song> songsToAdd, List<String> collabs) {
        myQuery.select().from(MASTER_PLAYLISTS).fetch();
    }

    public MasterPlaylist retrieve(String playlistID) {
        Record masterPlaylistResult = myQuery.select().from(MASTER_PLAYLISTS).where(MASTER_PLAYLISTS.PLAYLIST_ID.equal(playlistID)).fetchOne();
        Result<Record> masterSongResult = myQuery.select().from(MASTER_SONGS).where(MASTER_SONGS.PLAYLIST_ID.equal(playlistID)).fetch();
        Result<Record> masterContributors = myQuery.select().from(MASTER_CONTRIBUTORS).where(MASTER_CONTRIBUTORS.PLAYLIST_ID.equal(playlistID)).fetch();

        String listID = masterPlaylistResult.getValue(MASTER_PLAYLISTS.PLAYLIST_ID);
        String ownerID = masterPlaylistResult.getValue(MASTER_PLAYLISTS.OWNER_ID);
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
        MasterPlaylist playlistToReturn = new MasterPlaylist(listID, ownerID, threshold, songList, collabs);
        return playlistToReturn;
    }

}
