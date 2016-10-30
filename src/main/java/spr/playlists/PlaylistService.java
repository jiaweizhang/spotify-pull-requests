package spr.playlists;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.wrapper.spotify.Api;
import com.wrapper.spotify.methods.PlaylistCreationRequest;
import com.wrapper.spotify.methods.PlaylistRequest;
import com.wrapper.spotify.methods.UserPlaylistsRequest;
import com.wrapper.spotify.models.Playlist;
import com.wrapper.spotify.models.SimplePlaylist;
import data.Contributor;
import data.PlaylistPr;
import org.json.JSONObject;
import spr.exceptions.WrapperException;
import spr.requestmodels.CreatePlaylistRequest;
import spr.requestmodels.JoinPlaylistRequest;
import spr.requestmodels.VoteRequest;
import spr.std.Service;
import spr.std.models.StdRequest;
import spr.std.models.StdResponse;
import spr.std.models.StdResponseWithBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by jiaweizhang on 10/29/2016.
 */

@org.springframework.stereotype.Service
public class PlaylistService extends Service {

    private static List<SimplePlaylist> getPlaylists(String spotifyId, Api api) {
        final UserPlaylistsRequest request = api.getPlaylistsForUser(spotifyId).build();
        try {
            return request.get()
                    .getItems()
                    .stream()
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new WrapperException();
        }
    }

    private static Playlist getPlaylistById(String spotifyId, String playlistId, Api api) {
        final PlaylistRequest request = api.getPlaylist(spotifyId, playlistId).build();

        try {
            return request.get();
        } catch (Exception e) {
            throw new WrapperException();
        }
    }

    private static Playlist createPlaylist(String spotifyId, String title, Api api) {
        final PlaylistCreationRequest request = api.createPlaylist(spotifyId, title)
                .publicAccess(true)
                .build();

        try {
            return request.get();
        } catch (Exception e) {
            throw new WrapperException();
        }
    }

    public static JSONObject createCollaborativePlaylist(String spotifyId, String title, String authHeader) {
        HttpRequestWithBody http =
                Unirest.post("https://api.spotify.com/v1/users/" + spotifyId + "/playlists");

        try {
            HttpResponse<JsonNode> httpResponse = http
                    .header("Content-Type", "application/json")
                    .header("user-agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36")
                    .header("Authorization", authHeader)
                    .body("{\"name\":\"" + title + "\", \"public\":false, \"collaborative\":true}")
                    .asJson();

            JsonNode response = httpResponse.getBody();
            return response.getObject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new WrapperException();
        }
    }

    public StdResponse createPlaylist(CreatePlaylistRequest createPlaylistRequest) {
        // create playlist
        Playlist playlist = createPlaylist(createPlaylistRequest.spotifyId,
                createPlaylistRequest.playlistName,
                createPlaylistRequest.api);

        // create playlist_pr
        JSONObject playlistPR = createCollaborativePlaylist(createPlaylistRequest.spotifyId,
                createPlaylistRequest.playlistName,
                createPlaylistRequest.accessToken);

        // create playlist in database
        data.Playlist dataPlaylist = new data.Playlist(playlist.getId(),
                playlist.getName(),
                createPlaylistRequest.spotifyId,
                createPlaylistRequest.threshold);
        playlistAccessor.addPlaylist(dataPlaylist);

        // create playlist_pr in database
        data.PlaylistPr dataPlaylistPR = new data.PlaylistPr(
                playlistPR.getString("id"),
                playlistPR.getString("name"),
                createPlaylistRequest.spotifyId,
                playlist.getName());
        playlistPrAccessor.insert(dataPlaylistPR);

        // join playlist as a contributor
        Contributor contributor = new Contributor(dataPlaylist.playlistId,
                dataPlaylistPR.playlistId,
                createPlaylistRequest.spotifyId);
        contributorAccessor.addContributor(contributor);

        Map<String, Object> map = new HashMap<>();
        map.put("playlist", playlist);
        map.put("playlistPR", playlistPR);

        // return new CreatePlaylistResponse
        return new StdResponseWithBody(200, true, "Successfully created collaborative playlist", map);
    }

    public StdResponse joinPlaylist(JoinPlaylistRequest joinPlaylistRequest) {
        // the playlist ID is a pr
        PlaylistPr playlistPr = playlistPrAccessor.getPlaylistPr(joinPlaylistRequest.playlistId);

        String ownerId = playlistPr.ownerId;
        Api ownerApi = getApi(ownerId);

        Playlist ownerPlaylist = getPlaylistById(ownerId, playlistPr.parentPlaylistId, ownerApi);
        Playlist ownerPlaylistPR = getPlaylistById(ownerId, playlistPr.playlistId, ownerApi);

        // join playlist as a contributor
        Contributor contributor = new Contributor(playlistPr.parentPlaylistId,
                playlistPr.playlistId,
                joinPlaylistRequest.spotifyId);
        contributorAccessor.addContributor(contributor);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("playlist", ownerPlaylist); //TODO
        map.put("playlistPR", ownerPlaylistPR); // TODO

        // return new JoinPlaylistResponse
        return new StdResponseWithBody(200, true, "Successfully joined collaborative playlist", map);
    }

    public StdResponse vote(VoteRequest voteRequest) {
        // add the vote

        // determine if vote causes song to pass threshold

        // return new VoteResponse
        return null;
    }

    public StdResponse getPlaylists(StdRequest stdRequest) {
        // return simply every playlist
        return null;
    }

    public StdResponse getPlaylistById(StdRequest stdRequest, String playlistId) {
        Playlist playlist = getPlaylistById(stdRequest.spotifyId, playlistId, stdRequest.api);
        return new StdResponseWithBody(200, true, "Successfully retrieved", playlist);
    }
}