package spr.playlists;

import com.wrapper.spotify.Api;
import com.wrapper.spotify.methods.PlaylistCreationRequest;
import com.wrapper.spotify.methods.PlaylistRequest;
import com.wrapper.spotify.methods.UserPlaylistsRequest;
import com.wrapper.spotify.models.Playlist;
import com.wrapper.spotify.models.SimplePlaylist;
import data.MasterPlaylist;
import spr.exceptions.MembershipException;
import spr.exceptions.WrapperException;
import spr.requestmodels.CreatePlaylistRequest;
import spr.requestmodels.JoinPlaylistRequest;
import spr.requestmodels.VoteRequest;
import spr.std.Service;
import spr.std.models.StdRequest;
import spr.std.models.StdResponse;
import spr.std.models.StdResponseWithBody;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by jiaweizhang on 10/29/2016.
 */

@org.springframework.stereotype.Service
public class PlaylistService extends Service {
    public StdResponse createPlaylist(CreatePlaylistRequest createPlaylistRequest) {
        // check that name does not exist (or individual playlist name)
        Set<String> playlistNames = getPlaylists(createPlaylistRequest.spotifyId, createPlaylistRequest.api);
        if (playlistNames.contains(createPlaylistRequest.playlistName)) {
            throw new IllegalArgumentException("Playlist name already taken");
        } else if (playlistNames.contains(createPlaylistRequest.playlistName + "_PR")) {
            throw new IllegalArgumentException("Playlist name_PR already taken");
        }

        // create new playlist
        Playlist playlist = createPlaylist(createPlaylistRequest.spotifyId,
                createPlaylistRequest.playlistName, createPlaylistRequest.api);

        // create individual playlist
        Playlist individualPlaylist = createPlaylist(createPlaylistRequest.spotifyId,
                createPlaylistRequest.playlistName + "_PR", createPlaylistRequest.api);

        // create master playlist in database
        masterPlaylistAccessor.create(playlist.getId(), createPlaylistRequest.spotifyId, createPlaylistRequest.threshold);

        // create individual playlist in database
        // TODO

        // join as member of playlist
        masterPlaylistAccessor.addUserToPlaylist(playlist.getId(), createPlaylistRequest.spotifyId);

        // return new CreatePlaylistResponse
        return new StdResponseWithBody(200, true, "Successfully created collaborative playlist", playlist);
    }

    public StdResponse joinPlaylist(JoinPlaylistRequest joinPlaylistRequest) {
        // check and make sure master exists
        if (!masterPlaylistAccessor.isExist(joinPlaylistRequest.playlistId)) {
            throw new IllegalArgumentException("playlist does not exist");
        }
        // check to make sure individual playlist name isn't taken
        MasterPlaylist masterPlaylist = masterPlaylistAccessor.retrieve(joinPlaylistRequest.playlistId);
        Playlist playlist = getPlaylistById(masterPlaylist.myOwnerID, masterPlaylist.myPlaylistID, joinPlaylistRequest.api);
        String playlistName = playlist.getName();

        Set<String> playlistNames = getPlaylists(joinPlaylistRequest.spotifyId, joinPlaylistRequest.api);
        if (playlistNames.contains(playlistName + "_PR")) {
            throw new IllegalArgumentException("Playlist name_PR already taken");
        }

        // check to make sure not already member
        if (masterPlaylist.myCollabs.contains(joinPlaylistRequest.spotifyId)) {
            throw new MembershipException();
        }

        // join as member of playlist
        masterPlaylistAccessor.addUserToPlaylist(joinPlaylistRequest.playlistId, joinPlaylistRequest.spotifyId);

        // create individual playlist
        // TODO

        // return new JoinPlaylistResponse
        return new StdResponseWithBody(200, true, "Successfully joined collaborative playlist", playlist);
    }


    public StdResponse vote(VoteRequest voteRequest) {
        // TODO
        // check that is part of the playlist

        // add the vote

        // determine if vote causes song to pass threshold

        // return new VoteResponse
        return null;
    }

    public StdResponse getPlaylists(StdRequest stdRequest) {


        return null;
    }

    public StdResponse getPlaylistById(StdRequest stdRequest, String playlistId) {


        return null;
    }

    private Set<String> getPlaylists(String spotifyId, Api api) {
        final UserPlaylistsRequest request = api.getPlaylistsForUser(spotifyId).build();
        try {
            return request.get()
                    .getItems()
                    .stream()
                    .map(SimplePlaylist::getName)
                    .collect(Collectors.toSet());

        } catch (Exception e) {
            throw new WrapperException();
        }
    }

    private Playlist getPlaylistById(String spotifyId, String playlistId, Api api) {
        final PlaylistRequest request = api.getPlaylist(spotifyId, playlistId).build();

        try {
            return request.get();
        } catch (Exception e) {
            throw new WrapperException();
        }
    }

    private Playlist createPlaylist(String spotifyId, String title, Api api) {
        final PlaylistCreationRequest request = api.createPlaylist(spotifyId, title)
                .publicAccess(true)
                .build();

        try {
            return request.get();
        } catch (Exception e) {
            throw new WrapperException();
        }
    }
}
