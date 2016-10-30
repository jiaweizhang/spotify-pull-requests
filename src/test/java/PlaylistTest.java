import com.google.gson.JsonObject;
import junit.framework.TestCase;
import org.json.JSONObject;
import org.junit.Test;
import spr.playlists.PlaylistService;

/**
 * Created by jiaweizhang on 10/29/2016.
 */
public class PlaylistTest extends TestCase {

    @Test
    public void testCreateCollaborativePlaylist() {
        JSONObject jsonObject= PlaylistService.createCollaborativePlaylist("brainyasian",
                "verycoolplaylist",
                "Bearer BQDIUaLH7-hfoqfVb1yqNtgzafKu1Q6ShVDW0-t6XzRUW7atgIse6L3_vfuYs7wCr25SWGdLWb4q0DypbjmoDRyCfYFkBWiTWMUYBf6A_N0V6M_8bhHmQ8JiZMIABGB1q6TacCj__kqFxmbmVL9U09OXmSKAATeTlp1izEV8YC1B1aUVwusN3bCMMZQ8uoR8g7LNdPAIMpw9JvULSJTp-3PSCZMRKYkLakpDNjM-kNiTqwNC-hw4g0pfZbx5yZfZ"
        );

        System.out.println(jsonObject.toString(4));
    }
}
