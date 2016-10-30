package data;

public class Vote {

    public int requestId; //references the requests(id)
    public String spotifyId; //username of the user who voted
    public boolean vote;

    public Vote(int requestId, String spotifyId, boolean vote) {
        this.requestId = requestId;
        this.spotifyId = spotifyId;
        this.vote = vote;
    }

}
