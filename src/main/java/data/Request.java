package data;


public class Request {
    public int requestId;
    public String playlistId;
    public String spotifyId;
    public String songId;

    public Request(int requestId, String playlistId, String spotifyId, String songId) {
        this.requestId = requestId;
        this.playlistId = playlistId;
        this.spotifyId = spotifyId;
        this.songId = songId;
    }


}
