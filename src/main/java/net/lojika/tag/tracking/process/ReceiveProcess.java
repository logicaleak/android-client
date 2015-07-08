package net.lojika.tag.tracking.process;

import net.lojika.tag.tracking.*;
import net.lojika.tag.tracking.model.Location;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ozum on 08.07.2015.
 */
public class ReceiveProcess extends GenericRunnable implements Runnable {

    private TcpClient tcpClient;
    private LocationTrackingClient locationTrackingClient;
    private LocationTrackingDataManager locationTrackingDataManager;
    private int receiveWindow;

    public ReceiveProcess(TcpClient tcpClient, LocationTrackingClient locationTrackingClient, int receiveWindow) {
        this.receiveWindow = receiveWindow;
        this.tcpClient = tcpClient;
        this.locationTrackingClient = locationTrackingClient;
        this.locationTrackingDataManager = new LocationTrackingDataManagerImpl(receiveWindow);
    }

    public void run() {
        LocationTrackingData locationTrackingData = null;
        try {
            byte[] readData = tcpClient.read();
            locationTrackingData = locationTrackingDataManager.resolve(readData);

            if (locationTrackingData.getErrorCode() == ErrorCodes.NO_ERROR) {
                switch (locationTrackingData.getCommand()) {
                    case Command.ACCEPT:
                        System.out.println("Connection Established");
                        locationTrackingClient.onConnectionEstablished();
                        break;
                    case Command.REJECT:
                        locationTrackingClient.onConnectionFailed(locationTrackingData.getErrorCode(), "Error in rejected");
                        break;
                    case Command.LOCATION:
                        System.out.println("Location arrived");
                        Location location = new Location(
                                locationTrackingData.getTripId(),
                                locationTrackingData.getUserId(),
                                locationTrackingData.getLat(),
                                locationTrackingData.getLon()
                        );
                        locationTrackingClient.onLocationArrive(location);
                        break;
                }
            } else {
                locationTrackingClient.onError(locationTrackingData.getErrorCode(), "Unknown error From server");
            }


        } catch (IOException e) {
            e.printStackTrace();
            locationTrackingClient.onError(locationTrackingData.getErrorCode(), "Could not read");
        }

    }
}
