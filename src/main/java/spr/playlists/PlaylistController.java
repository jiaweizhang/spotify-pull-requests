package spr.playlists;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import spr.requestmodels.CreatePlaylistRequest;
import spr.requestmodels.JoinPlaylistRequest;
import spr.requestmodels.VoteRequest;
import spr.std.Controller;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by jiaweizhang on 10/29/2016.
 */

@RestController
@RequestMapping("/api")
public class PlaylistController extends Controller {

    @Autowired
    private PlaylistService playlistService;

    @RequestMapping(value = "/playlist",
            method = RequestMethod.POST,
            headers = {"Content-type=application/json"})
    @ResponseBody
    public ResponseEntity createPlaylist(CreatePlaylistRequest createPlaylistRequest,
                                         HttpServletRequest httpServletRequest) {
        pre(createPlaylistRequest, httpServletRequest);
        return wrap(playlistService.createPlaylist(createPlaylistRequest));
    }

    @RequestMapping(value = "/invite",
            method = RequestMethod.POST,
            headers = {"Content-type=application/json"})
    @ResponseBody
    public ResponseEntity joinPlaylist(JoinPlaylistRequest joinPlaylistRequest, HttpServletRequest httpServletRequest) {
        pre(joinPlaylistRequest, httpServletRequest);
        return wrap(playlistService.joinPlaylist(joinPlaylistRequest));
    }

    @RequestMapping(value = "/vote",
            method = RequestMethod.POST,
            headers = {"Content-type=application/json"})
    @ResponseBody
    public ResponseEntity vote(VoteRequest voteRequest, HttpServletRequest httpServletRequest) {
        pre(voteRequest, httpServletRequest);
        return wrap(playlistService.vote(voteRequest));
    }
}
