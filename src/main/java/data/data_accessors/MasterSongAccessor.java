package data.data_accessors;

import data.Song;

import static db.tables.MasterSongs.MASTER_SONGS;
import static db.tables.VoteTable.VOTE_TABLE;

/**
 * Created by Ankit on 10/29/2016.
 */
public class MasterSongAccessor extends Accessor {

    public MasterSongAccessor() {
        super();
    }

    /*
    create master playlist
     */
    public void add(Song song) {
        myQuery.insertInto(MASTER_SONGS, MASTER_SONGS.PLAYLIST_ID, MASTER_SONGS.CONTRIBUTOR, MASTER_SONGS.SONG_ID)
                .values(song.myPlaylistID, song.myContributor, song.mySpotifySongID).execute();
    }

    public void delete(int myID) {
        myQuery.delete(MASTER_SONGS).where(MASTER_SONGS.ID.equal(myID)).execute();
        myQuery.delete(VOTE_TABLE).where(VOTE_TABLE.SONG_ID.equal(myID)).execute();
    }

    public boolean isExist(int id) {
        return myQuery.select().from(MASTER_SONGS).where(MASTER_SONGS.ID.equal(id)).fetchOne() != null;
    }

}
