package data;

import java.util.List;

public class IndividualPlaylist {
    public String myPlaylistID;
    public String myOwnerID;
    public String myMasterID;
    private List<Song> mySongs;

    public IndividualPlaylist(String playlistID, String ownerID, String masterID){
        myPlaylistID = playlistID;
        myOwnerID = ownerID;
        myMasterID = masterID;
    }

    public void setListOfSongs(List<Song> songs){
        mySongs = songs;
    }
}
