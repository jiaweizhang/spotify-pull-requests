package data;

/**
 * Created by nesh170 on 10/29/2016.
 */
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
