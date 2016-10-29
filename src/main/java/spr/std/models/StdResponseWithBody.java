package spr.std.models;

/**
 * Created by jiaweizhang on 10/29/2016.
 */
public class StdResponseWithBody extends StdResponse {
    public Object body;

    public StdResponseWithBody(int status, boolean success, String message, Object body) {
        this.status = status;
        this.success = success;
        this.message = message;
        this.body = body;
    }
}
