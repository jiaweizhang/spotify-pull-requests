package data.data_accessors;

import data.IndividualPlaylist;
import org.jooq.Record;

import static db.tables.IndividualPlaylists.INDIVIDUAL_PLAYLISTS;

public class IndividualPlaylistAccessor extends Accessor {

    public IndividualPlaylistAccessor(){
        super();
    }

    public void addIndividualPlaylist(String playlistID, String ownerID, String masterPlaylistID) {
        myQuery.insertInto(INDIVIDUAL_PLAYLISTS, INDIVIDUAL_PLAYLISTS.SPOTIFY_PLAYLIST_ID,INDIVIDUAL_PLAYLISTS.OWNER_ID, INDIVIDUAL_PLAYLISTS.MASTER_ID)
                .values(playlistID, ownerID, masterPlaylistID).execute();
    }
    public boolean isExist(String playlistID) {
        return myQuery.select().from(INDIVIDUAL_PLAYLISTS).where(INDIVIDUAL_PLAYLISTS.SPOTIFY_PLAYLIST_ID.equal(playlistID)).fetchOne() != null;
    }



    public IndividualPlaylist retrieveIndividualPlaylist(String playlistID){
       Record record = myQuery.select().from(INDIVIDUAL_PLAYLISTS).where(INDIVIDUAL_PLAYLISTS.SPOTIFY_PLAYLIST_ID.equal(playlistID)).fetchOne();
       IndividualPlaylist playlist = new IndividualPlaylist(
               record.getValue(INDIVIDUAL_PLAYLISTS.SPOTIFY_PLAYLIST_ID),
               record.getValue(INDIVIDUAL_PLAYLISTS.OWNER_ID),
               record.getValue(INDIVIDUAL_PLAYLISTS.MASTER_ID)
       );
       return playlist;
    }
}
