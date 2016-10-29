package spr.polling;

import spr.std.Service;
import spr.std.models.StdResponse;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by jiaweizhang on 10/29/2016.
 */

@org.springframework.stereotype.Service
public class PollingService extends Service {

    public StdResponse start() {
        ScheduledExecutorService execService = Executors.newScheduledThreadPool(1);
        execService.scheduleWithFixedDelay(() -> {
            // TODO
            // iterate through all individual playlists and add to master PR playlist
            
        }, 0, 60, TimeUnit.SECONDS);
        return new StdResponse(200, true, "Polling service started");

    }
}
