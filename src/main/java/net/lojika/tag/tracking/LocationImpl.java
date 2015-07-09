package net.lojika.tag.tracking;

import net.lojika.tag.tracking.model.Location;
import net.lojika.tag.tracking.output.LocationTrackingClient;

/**
 * Created by ozum on 09.07.2015.
 */
public class LocationImpl extends LocationTrackingClient {
    @Override
    public void onConnectionSuccessfullyEnded() {

    }

    @Override
    public void onConnectionEstablished() {
        System.out.println("Established");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        Thread.sleep(1000);
                        sendLocation(30d, 30d);
                    }
                } catch (Exception e) {

                }

            }
        }).start();
    }

    @Override
    public void onConnectionFailed(int errorCode, String errorMessage) {

    }

    @Override
    public void onLocationArrive(Location location) {

    }

    @Override
    public void onConnectionLost() {

    }

    @Override
    public void onError(int errorCode, String errorMessage) {

    }
}
