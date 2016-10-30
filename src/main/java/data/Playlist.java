package data;


public class Playlist {
    public String playlistId;
    public String playlistName;
    public String ownerId;
    public int threshold;

    public Playlist(String playlistId, String playlistName, String ownerId, int threshold) {
        this.playlistId = playlistId;
        this.playlistName = playlistName;
        this.ownerId = ownerId;
        this.threshold = threshold;
    }
}
