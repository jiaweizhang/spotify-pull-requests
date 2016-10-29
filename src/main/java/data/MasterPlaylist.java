package data;

import java.util.List;

public class MasterPlaylist{

    public String myPlaylistID;
    public String myOwnerID;
    public int myThreshold;
    public List<Song> mySongs;


    public MasterPlaylist(String playlistID, String ownerID, int threshold, List<Song> songs){
        myPlaylistID = playlistID;
        myOwnerID = ownerID;
        myThreshold = threshold;
        mySongs = songs;
    }

}