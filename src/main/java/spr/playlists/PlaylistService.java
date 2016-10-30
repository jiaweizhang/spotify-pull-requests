package spr.playlists;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.wrapper.spotify.Api;
import com.wrapper.spotify.methods.AddTrackToPlaylistRequest;
import com.wrapper.spotify.methods.PlaylistRequest;
import com.wrapper.spotify.methods.UserPlaylistsRequest;
import com.wrapper.spotify.models.Playlist;
import com.wrapper.spotify.models.SimplePlaylist;
import data.Contributor;
import data.PlaylistPr;
import data.Request;
import data.Vote;
import org.json.JSONObject;
import spr.exceptions.WrapperException;
import spr.requestmodels.CreatePlaylistRequest;
import spr.requestmodels.JoinPlaylistRequest;
import spr.requestmodels.VoteRequest;
import spr.std.Service;
import spr.std.models.StdRequest;
import spr.std.models.StdResponse;
import spr.std.models.StdResponseWithBody;

import java.util.*;
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

    private static JSONObject createPlaylist(String spotifyId, String title, String authHeader) {
        HttpRequestWithBody http =
                Unirest.post("https://api.spotify.com/v1/users/" + spotifyId + "/playlists");

        System.out.println("PL title: " + title);
        try {
            HttpResponse<JsonNode> httpResponse = http
                    .header("Content-Type", "application/json")
                    .header("user-agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36")
                    .header("Authorization", authHeader)
                    .body("{\"name\":\"" + title + "\", \"public\":true}")
                    .asJson();

            JsonNode response = httpResponse.getBody();
            return response.getObject();
        } catch (Exception e) {
            e.printStackTrace();
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
        System.out.println("beginning of create playlist title: " + createPlaylistRequest.playlistTitle);
        // create playlist
        JSONObject playlist = createPlaylist(createPlaylistRequest.spotifyId,
                createPlaylistRequest.playlistTitle,
                createPlaylistRequest.accessToken);

        // create playlist_pr
        JSONObject playlistPR = createCollaborativePlaylist(createPlaylistRequest.spotifyId,
                createPlaylistRequest.playlistTitle + "_PR",
                createPlaylistRequest.accessToken);

        // create playlist in database
        data.Playlist dataPlaylist = new data.Playlist(playlist.getString("id"),
                playlist.getString("name"),
                createPlaylistRequest.spotifyId,
                createPlaylistRequest.threshold);
        playlistAccessor.addPlaylist(dataPlaylist);

        // create playlist_pr in database
        data.PlaylistPr dataPlaylistPR = new data.PlaylistPr(
                playlistPR.getString("id"),
                playlistPR.getString("name"),
                createPlaylistRequest.spotifyId,
                playlist.getString("id"));
        playlistPrAccessor.insert(dataPlaylistPR);

        // join playlist as a contributor
        Contributor contributor = new Contributor(dataPlaylist.playlistId,
                dataPlaylistPR.playlistId,
                createPlaylistRequest.spotifyId);
        contributorAccessor.addContributor(contributor);

        try {
            Map<String, Object> pl1 = new ObjectMapper().readValue(playlist.toString(), HashMap.class);
            Map<String, Object> pl2 = new ObjectMapper().readValue(playlistPR.toString(), HashMap.class);

            Map<String, Object> map = new HashMap<>();
            map.put("playlist", pl1);
            map.put("playlistPR", pl2);

            // return new CreatePlaylistResponse
            return new StdResponseWithBody(200, true, "Successfully created collaborative playlist", map);
        } catch (Exception e) {
            e.printStackTrace();
            throw new WrapperException();
        }
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
        Vote vote = new Vote(voteRequest.requestId, voteRequest.spotifyId, !voteRequest.approve);
        System.out.println(voteRequest.requestId);
        System.out.println(voteRequest.spotifyId);
        votesAccessor.addVote(vote);

        // determine if vote causes song to pass threshold
        Map<Request, List<Vote>> voteMap = votesAccessor.getVotes(voteRequest.requestId);

        Request request = voteMap.keySet().stream().collect(Collectors.toList()).get(0);
        List<Vote> votes = voteMap.get(request);

        PlaylistPr playlistPr = playlistPrAccessor.getPlaylistPr(request.playlistId);
        Api ownerApi = getApi(playlistPr.ownerId);

        if (request.votesToApprove <= 0) {
            // approve
            // add song to non-PR
            addSongToPlaylist(playlistPr.ownerId, request.songId, playlistPr.parentPlaylistId, ownerApi);
            votesAccessor.deleteVote(request.requestId);
            requestAccessor.deleteRequest(request.requestId);
        } else if (request.votesToDecline < 0) {
            // decline
            votesAccessor.deleteVote(request.requestId);
            requestAccessor.deleteRequest(request.requestId);
        }

        // return new VoteResponse
        return new StdResponse(200, true, "Successfully voted");
    }

    public StdResponse getPlaylists(StdRequest stdRequest) {
        List<PlaylistPr> playlistPrList = contributorAccessor.getPlaylistPrBasedOnContributor(stdRequest.spotifyId);
        return new StdResponseWithBody(200, true, "Successfully returned playlists", playlistPrList);
    }

    public void addSongToPlaylist(String spotifyId, String songId, String playlistId, Api api) {
        final List<String> tracksToAdd = Collections.singletonList("spotify:track:" + songId);

        final AddTrackToPlaylistRequest request = api.addTracksToPlaylist(spotifyId, playlistId, tracksToAdd)
                .build();

        try {
            request.get();
        } catch (Exception e) {
            e.printStackTrace();
            throw new WrapperException();
        }
    }

    public StdResponse getPlaylistPRById(StdRequest stdRequest, String playlistId) {
        // refresh the requests related to the PR
        Playlist playlist = getPlaylistById(stdRequest.spotifyId, playlistId, stdRequest.api);

        System.out.println("dkfjdkfjd");
        playlist.getTracks().getItems().forEach(System.out::println);

        Set<String> requests = requestAccessor.returnRequests(playlistId).stream()
                .map(r -> r.songId).collect(Collectors.toSet());

        System.out.println("dlkfjdlfkdjdlfkjdlfkdjf");
        System.out.println(Arrays.toString(requests.toArray()));

        List<Request> toAdd = playlist.getTracks().getItems().stream()
                .filter(t -> !requests.contains(t.getTrack().getId()))
                .map(t -> new Request(-1, playlist.getId(), t.getAddedBy().getId(), t.getTrack().getId()))
                .collect(Collectors.toList());

        System.out.println("funsies");
        for (Request request : toAdd) {
            requestAccessor.addRequest(request);
            System.out.println(request.requestId);
        }

        List<Request> requestList = requestAccessor.returnRequests(playlistId);
        return new StdResponseWithBody(200, true, "Successfully retrieved", requestList);

    }
}