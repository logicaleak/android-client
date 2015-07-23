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
                    int counter = 0;
                    while (true) {
                        counter++;
                        Thread.sleep(200);
                        sendLocation(30d, 30d);
                        if (counter == 5) {
                            endConnection();
                            break;
                        }

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
