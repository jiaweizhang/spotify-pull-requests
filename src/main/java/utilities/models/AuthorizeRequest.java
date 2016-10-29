package utilities.models;

/**
 * Created by jiaweizhang on 10/29/2016.
 */
public class AuthorizeRequest {
    public String client_id;
    public String response_type;
    public String redirect_url;
    public String state;
    public String scope;

    public AuthorizeRequest() {
        client_id = "7f5795cf20ba45b08ca27a4a8f92cf23";
        response_type = "code";
        redirect_url = "http://easywebapi.com/redirect";
        state = "spr";
        scope = "playlist-read-private playlist-modify-public playlist-modify-private";
    }
}
