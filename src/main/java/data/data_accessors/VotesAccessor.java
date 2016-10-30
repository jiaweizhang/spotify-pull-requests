package data.data_accessors;

import data.Request;
import data.Vote;
import org.jooq.Record;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static db.tables.Contributors.CONTRIBUTORS;
import static db.tables.Playlists.PLAYLISTS;
import static db.tables.PlaylistsPr.PLAYLISTS_PR;
import static db.tables.Requests.REQUESTS;
import static db.tables.VoteTable.VOTE_TABLE;

public class VotesAccessor extends Accessor {

    public VotesAccessor() {
        super();
    }

    public Map<Request, List<Vote>> getVotes(int requestId) {
        Request request = myQuery.select().from(REQUESTS).where(REQUESTS.REQUEST_ID.equal(requestId)).fetchOne()
                .map(record -> new Request(
                        record.getValue(REQUESTS.REQUEST_ID),
                        record.getValue(REQUESTS.PLAYLIST_ID),
                        record.getValue(REQUESTS.SPOTIFY_ID),
                        record.getValue(REQUESTS.SONG_ID)
                ));
        Record playlistInfo = myQuery.select(PLAYLISTS_PR.PLAYLIST_ID, PLAYLISTS_PR.PARENT_PLAYLIST_ID).from(PLAYLISTS_PR)
                .where(PLAYLISTS_PR.PLAYLIST_ID.equal(request.playlistId)).fetchOne();
        int threshold = myQuery.select(PLAYLISTS.THRESHOLD).from(PLAYLISTS)
                .where(PLAYLISTS.PLAYLIST_ID.equal(playlistInfo.getValue(PLAYLISTS_PR.PARENT_PLAYLIST_ID)))
                .fetchOne().getValue(PLAYLISTS.THRESHOLD);
        int numberOfContributors = myQuery.select().from(CONTRIBUTORS).where(CONTRIBUTORS.PLAYLIST_PR_ID
                .equal(playlistInfo.getValue(PLAYLISTS_PR.PLAYLIST_ID))).fetchCount();
        List<Vote> voteList = myQuery.select().from(VOTE_TABLE).where(VOTE_TABLE.REQUEST_ID.equal(request.requestId))
                .fetch().stream().map(voteRecord -> new Vote(
                        voteRecord.getValue(VOTE_TABLE.REQUEST_ID),
                        voteRecord.getValue(VOTE_TABLE.SPOTIFY_ID),
                        voteRecord.getValue(VOTE_TABLE.VOTE)
                )).collect(Collectors.toList());
        int numberOfPositiveVotesNeeded = threshold / 100 * numberOfContributors;
        int numberOfPositiveVotes = voteList.stream().filter(vote -> vote.vote).collect(Collectors.toList()).size();
        int numberOfNegativeVotes = voteList.stream().filter(vote -> !vote.vote).collect(Collectors.toList()).size();
        request.votesToApprove = numberOfPositiveVotesNeeded - numberOfPositiveVotes;
        request.votesToDecline = numberOfPositiveVotesNeeded - numberOfNegativeVotes;
        Map<Request, List<Vote>> outputMap = new HashMap<>();
        outputMap.put(request, voteList);
        return outputMap;

    }


    public int addVote(Vote vote) {
        return myQuery.insertInto(VOTE_TABLE, VOTE_TABLE.REQUEST_ID, VOTE_TABLE.SPOTIFY_ID, VOTE_TABLE.VOTE)
                .values(vote.requestId, vote.spotifyId, vote.vote).execute();
    }

    public int deleteVote(int requestId) {
        return myQuery.delete(VOTE_TABLE)
                .where(VOTE_TABLE.REQUEST_ID.equal(requestId)).execute();
    }
}
