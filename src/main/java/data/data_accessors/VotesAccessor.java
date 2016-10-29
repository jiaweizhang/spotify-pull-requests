package data.data_accessors;

import data.Song;
import data.Vote;
import org.jooq.Record;
import org.jooq.Result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static db.tables.MasterPlaylists.MASTER_PLAYLISTS;
import static db.tables.MasterContributors.MASTER_CONTRIBUTORS;
import static db.tables.MasterSongs.MASTER_SONGS;
import static db.tables.VoteTable.VOTE_TABLE;

public class VotesAccessor extends Accessor {

    public VotesAccessor(){
        super();
    }

    public Map<Song,List<Vote>> getVotesFromPlaylist(String playlistID){
        Record playlistRecord = myQuery.select(MASTER_PLAYLISTS.PLAYLIST_ID,MASTER_PLAYLISTS.THRESHOLD).from(MASTER_PLAYLISTS).where(MASTER_PLAYLISTS.PLAYLIST_ID.equal(playlistID)).fetchOne();
        int numberOfCollabs = myQuery.select().from().where(MASTER_CONTRIBUTORS.PLAYLIST_ID.equal(playlistRecord.getValue(MASTER_PLAYLISTS.PLAYLIST_ID))).fetchCount();
        Result<Record> songsRecord = myQuery.select().from(MASTER_SONGS).where(MASTER_SONGS.PLAYLIST_ID.equal(playlistID)).fetch();
        List<Integer> songIDs = songsRecord.stream().map(record -> record.getValue(MASTER_SONGS.ID)).collect(Collectors.toList());
        Map<Integer,Result<Record>> voteRecord = myQuery.select().from(VOTE_TABLE).where(VOTE_TABLE.SONG_ID.in(songIDs)).fetch().intoGroups(VOTE_TABLE.SONG_ID);
        List<Song> songs = songsRecord.stream().map(record -> new Song(
                record.getValue(MASTER_SONGS.ID),
                record.getValue(MASTER_SONGS.PLAYLIST_ID),
                record.getValue(MASTER_SONGS.CONTRIBUTOR),
                record.getValue(MASTER_SONGS.SONG_ID)
        )).collect(Collectors.toList());
        Map<Song,List<Vote>> songVoteMap = new HashMap<>();
        songs.forEach(song -> {
            if (voteRecord.containsKey(song.myID)) {
                createVoteList(song,voteRecord.get(song.myID),playlistRecord.getValue(MASTER_PLAYLISTS.THRESHOLD),numberOfCollabs, songVoteMap);
            }
        });
        return songVoteMap;
    }

    private void createVoteList(Song song, Result<Record> records, int threshold, int numberOfCollabs, Map<Song,List<Vote>> songVoteMap) {
        int successValue = threshold/100 * numberOfCollabs;
        List<Vote> voteList = records.stream().map(record -> new Vote(
                record.getValue(VOTE_TABLE.SONG_ID),
                record.getValue(VOTE_TABLE.VOTED_BY),
                record.getValue(VOTE_TABLE.VOTE)
        )).collect(Collectors.toList());
        int positiveVotes = voteList.stream().filter(vote -> vote.myVote).collect(Collectors.toList()).size();
        int negativeVotes = voteList.stream().filter(vote -> !vote.myVote).collect(Collectors.toList()).size();
        song.votesTillAccept = successValue - positiveVotes;
        song.votesTillDecline = successValue - negativeVotes;
        songVoteMap.put(song,voteList);
    }
}
