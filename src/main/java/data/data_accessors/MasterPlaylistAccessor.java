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
    public void create(MasterPlaylist masterPlaylist) {
        for (Song song : masterPlaylist.mySongs) {
            myQuery.insertInto(MASTER_SONGS, MASTER_SONGS.ID, MASTER_SONGS.PLAYLIST_ID, MASTER_SONGS.CONTRIBUTOR, MASTER_SONGS.SONG_ID).values(song.myID, song.myPlaylistID, song.myContributor, song.mySpotifySongID).execute();
        }
        for (String collab : masterPlaylist.myCollabs) {
            myQuery.insertInto(MASTER_CONTRIBUTORS, MASTER_CONTRIBUTORS.PLAYLIST_ID, MASTER_CONTRIBUTORS.COLLAB_ID).values(masterPlaylist.myPlaylistID, collab).execute();
        }
        myQuery.insertInto(MASTER_PLAYLISTS, MASTER_PLAYLISTS.PLAYLIST_ID, MASTER_PLAYLISTS.OWNER_ID, MASTER_PLAYLISTS.THRESHOLD).values(masterPlaylist.myPlaylistID, masterPlaylist.myOwnerID, masterPlaylist.myThreshold).execute();
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

    public void addUserToPlaylist(String playlistID, String collabID) {
        myQuery.insertInto(MASTER_CONTRIBUTORS, MASTER_CONTRIBUTORS.PLAYLIST_ID, MASTER_CONTRIBUTORS.COLLAB_ID)
                .values(playlistID, collabID).execute();
    }

}
