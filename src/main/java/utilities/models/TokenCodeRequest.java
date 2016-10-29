package utilities.models;

/**
 * Created by jiaweizhang on 10/29/2016.
 */
public class TokenCodeRequest {
    public String grant_type;
    public String code;
    public String authorization_header;

    public TokenCodeRequest(String code) {
        this.code = code;
        grant_type = "authorization_code";
        authorization_header = "Basic <N2Y1Nzk1Y2YyMGJhNDViMDhjYTI3YTRhOGY5MmNmMjM=:MThmNGI4ZDMxZjZlNDVmMWI2Y2U0YTYwNmQyYTU4YWU=>";
    }
}
