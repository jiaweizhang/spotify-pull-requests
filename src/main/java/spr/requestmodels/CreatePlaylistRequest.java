package spr.requestmodels;

import spr.std.models.StdRequest;

/**
 * Created by jiaweizhang on 10/29/2016.
 */
public class CreatePlaylistRequest extends StdRequest {
    public String playlistName;
    public int threshold;
}
