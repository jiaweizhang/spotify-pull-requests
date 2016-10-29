package data;

/**
 * Created by nesh170 on 10/29/2016.
 */
public class Votes {
    public int myID; //references the song(id)
    public String myVotedBy; //username of the user who voted
    public boolean myVote;

    public Votes(int id, String votedBy, boolean vote) {
        myID = id;
        myVotedBy = votedBy;
        myVote = vote;
    }
}
