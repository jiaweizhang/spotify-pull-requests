package spr.playlists;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spr.requestmodels.CreatePlaylistRequest;
import spr.requestmodels.JoinPlaylistRequest;
import spr.requestmodels.VoteRequest;
import spr.std.Controller;
import spr.std.models.StdRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by jiaweizhang on 10/29/2016.
 */

@RestController
@RequestMapping("/api")
public class PlaylistController extends Controller {

    @Autowired
    private PlaylistService playlistService;

    @RequestMapping(value = "/playlists",
            method = RequestMethod.POST,
            headers = {"Content-type=application/json"})
    @ResponseBody
    public ResponseEntity createPlaylist(CreatePlaylistRequest createPlaylistRequest,
                                         HttpServletRequest httpServletRequest) {
        System.out.println(createPlaylistRequest.playlistName);
        pre(createPlaylistRequest, httpServletRequest);
        return wrap(playlistService.createPlaylist(createPlaylistRequest));
    }

    @RequestMapping(value = "/join",
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

    @RequestMapping(value = "/playlists",
            method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getPlaylists(HttpServletRequest httpServletRequest) {
        StdRequest stdRequest = pre(httpServletRequest);
        return wrap(playlistService.getPlaylists(stdRequest));
    }

    @RequestMapping(value = "/playlist/{playlistId}",
            method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getPlaylistById(@PathVariable(value = "playlistId") String playlistId, HttpServletRequest httpServletRequest) {
        StdRequest stdRequest = pre(httpServletRequest);
        return wrap(playlistService.getPlaylistPRById(stdRequest, playlistId));
    }
}
