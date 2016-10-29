package utilities.models;

/**
 * Created by jiaweizhang on 10/29/2016.
 */
public class TokenRefreshRequest {
    public String grant_type;
    public String refresh_token;
    public String authorization_header;

    public TokenRefreshRequest(String refresh_token) {
        this.refresh_token = refresh_token;
        grant_type = "refresh_token";
        authorization_header = "Basic <N2Y1Nzk1Y2YyMGJhNDViMDhjYTI3YTRhOGY5MmNmMjM=:MThmNGI4ZDMxZjZlNDVmMWI2Y2U0YTYwNmQyYTU4YWU=>";
    }
}
