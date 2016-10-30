package data.data_accessors;
import data.Request;
import db.tables.Requests;
import org.jooq.Record;
import org.jooq.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ankit on 10/29/2016.
 */
public class RequestAccessor extends Accessor {

    public RequestAccessor() {
        super();
    }

    public void deleteRequest(int requestId) {
        myQuery.delete(Requests.REQUESTS).where(Requests.REQUESTS.REQUEST_ID.equal(requestId)).execute();
    }
    public boolean isExist(int requestId) {
        return myQuery.select().from(Requests.REQUESTS).where(Requests.REQUESTS.REQUEST_ID.equal(requestId)).fetchOne() != null;
    }

    public List<Request> returnRequests(String playlistId) {
        List<Request> requestList = new ArrayList<Request>();
        Result<Record> returnedRequests = myQuery.select().from(Requests.REQUESTS).where(Requests.REQUESTS.PLAYLIST_ID.equal(playlistId)).fetch();
        for (Record record : returnedRequests) {
            int id = record.getValue(Requests.REQUESTS.REQUEST_ID);
            String listId = record.getValue(Requests.REQUESTS.PLAYLIST_ID);
            String spotifyId = record.getValue(Requests.REQUESTS.SPOTIFY_ID);
            String songId = record.getValue(Requests.REQUESTS.SONG_ID);
            Request request = new Request(id, listId, spotifyId, songId);
            requestList.add(request);
        }
        return requestList;
    }
}
