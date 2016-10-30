package data;


public class PlaylistPR {

    public String playlistId;
    public String playlistName;
    public String ownerId;
    public String parentPlaylistId;

    public PlaylistPR(String playlistId, String playlistName, String ownerId, String parentPlaylistId) {
        this.playlistId = playlistId;
        this.playlistName = playlistName;
        this.ownerId = ownerId;
        this.parentPlaylistId = parentPlaylistId;
    }
}
