package data;

import java.util.List;

public class MasterPlaylist {

    public String myPlaylistID;
    public String myOwnerID;
    public String myName;
    public int myThreshold;
    public List<Song> mySongs;
    public List<String> myCollabs;


    public MasterPlaylist(String playlistID, String ownerID, int threshold, List<Song> songs, List<String> collabs, String name) {
        myPlaylistID = playlistID;
        myOwnerID = ownerID;
        myThreshold = threshold;
        mySongs = songs;
        myCollabs = collabs;
        myName = name;
    }

    public MasterPlaylist(String playlistID, String ownerID, int threshold, String name) {
        myPlaylistID = playlistID;
        myOwnerID = ownerID;
        myThreshold = threshold;
        myName = name;
    }

}