package data;

public class Song {
    public int myID;
    public String myPlaylistID;
    public String myContributor;
    public String mySpotifySongID;

    public Song(int id, String playlistID, String contributor, String songID) {
        myID = id;
        myPlaylistID = playlistID;
        myContributor = contributor;
        mySpotifySongID = songID;
    }
}
