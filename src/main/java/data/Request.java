package data;

/**
 * Created by Ankit on 10/29/2016.
 */
public class Request {
    public int requestId;
    public String playlistId;
    public String spotifyId;
    public String songId;
    public int votesToApprove;
    public int votesToDecline;

    public Request(int requestId, String playlistId, String spotifyId, String songId) {
        this.requestId = requestId;
        this.playlistId = playlistId;
        this.spotifyId = spotifyId;
        this.songId = songId;
    }


}
