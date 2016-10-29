package spr.playlists;

import spr.requestmodels.CreatePlaylistRequest;
import spr.requestmodels.JoinPlaylistRequest;
import spr.requestmodels.VoteRequest;
import spr.std.Service;
import spr.std.models.StdResponse;

/**
 * Created by jiaweizhang on 10/29/2016.
 */

@org.springframework.stereotype.Service
public class PlaylistService extends Service {
    public StdResponse createPlaylist(CreatePlaylistRequest createPlaylistRequest) {
        // TODO
        // check that name does not exist (or individual playlist name)

        // create new playlist

        // join as member of playlist

        // create individual playlist

        // return new CreatePlaylistResponse
        return null;
    }

    public StdResponse joinPlaylist(JoinPlaylistRequest joinPlaylistRequest) {
        // TODO
        // check to make sure individual playlist name isn't taken

        // check to make sure not already member

        // join as member of playlist

        // create individual playlist

        // return new JoinPlaylistResponse
        return null;
    }


    public StdResponse vote(VoteRequest voteRequest) {
        // TODO
        // check that is part of the playlist

        // add the vote

        // determine if vote causes song to pass threshold

        // return new VoteResponse
        return null;
    }
}
