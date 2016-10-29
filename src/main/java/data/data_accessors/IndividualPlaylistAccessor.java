package data.data_accessors;

import data.IndividualPlaylist;
import org.jooq.Record;

import java.util.List;

import static db.tables.IndividualPlaylists.INDIVIDUAL_PLAYLISTS;

public class IndividualPlaylistAccessor extends Accessor {

    public IndividualPlaylistAccessor(){
        super();
    }

    public List<IndividualPlaylist> retrieveIndividualPlaylist(String playlistID){
       Record record = myQuery.select().from(INDIVIDUAL_PLAYLISTS).where(INDIVIDUAL_PLAYLISTS.SPOTIFY_PLAYLIST_ID.equal(playlistID)).fetchOne();
       IndividualPlaylist playlist = new IndividualPlaylist(
               record.getValue(INDIVIDUAL_PLAYLISTS.SPOTIFY_PLAYLIST_ID),
               record.getValue(INDIVIDUAL_PLAYLISTS.OWNER_ID),
               record.getValue(INDIVIDUAL_PLAYLISTS.MASTER_ID)
       );
       return playlist;
    }
}
