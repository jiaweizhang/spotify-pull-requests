package data.data_accessors;
import data.Request;
import static db.tables.Requests.REQUESTS;
import org.jooq.Record;
import org.jooq.Result;

import java.util.ArrayList;
import java.util.List;


public class RequestAccessor extends Accessor {

    public RequestAccessor() {
        super();
    }

    public void addRequest(Request request) {
        myQuery.insertInto(REQUESTS, REQUESTS.REQUEST_ID, REQUESTS.PLAYLIST_ID, REQUESTS.SPOTIFY_ID, REQUESTS.SONG_ID)
                .values(request.requestId, request.playlistId, request.spotifyId, request.songId).execute();
    }

    public void deleteRequest(int requestId) {
        myQuery.delete(REQUESTS).where(REQUESTS.REQUEST_ID.equal(requestId)).execute();
    }
    public boolean isExist(int requestId) {
        return myQuery.select().from(REQUESTS).where(REQUESTS.REQUEST_ID.equal(requestId)).fetchOne() != null;
    }
    public List<Request> returnRequests(String playlistId) {
        List<Request> requestList = new ArrayList<Request>();
        Result<Record> returnedRequests = myQuery.select().from(REQUESTS).where(REQUESTS.PLAYLIST_ID.equal(playlistId)).fetch();
        for (Record record : returnedRequests) {
            int id = record.getValue(REQUESTS.REQUEST_ID);
            String listId = record.getValue(REQUESTS.PLAYLIST_ID);
            String spotifyId = record.getValue(REQUESTS.SPOTIFY_ID);
            String songId = record.getValue(REQUESTS.SONG_ID);
            Request request = new Request(id, listId, spotifyId, songId);
            requestList.add(request);
        }
        return requestList;
    }
}
