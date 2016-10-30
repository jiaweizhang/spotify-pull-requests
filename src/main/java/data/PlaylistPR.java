package data;

/**
 * Created by Ankit on 10/29/2016.
 */
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
