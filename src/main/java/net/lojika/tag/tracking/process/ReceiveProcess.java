package net.lojika.tag.tracking.process;

import net.lojika.tag.tracking.constant.Command;
import net.lojika.tag.tracking.constant.ErrorCodes;
import net.lojika.tag.tracking.data.LocationTrackingData;
import net.lojika.tag.tracking.data.LocationTrackingDataManager;
import net.lojika.tag.tracking.data.LocationTrackingDataManagerImpl;
import net.lojika.tag.tracking.model.Location;
import net.lojika.tag.tracking.network.TcpClient;
import net.lojika.tag.tracking.output.LocationTrackingClient;

import java.io.IOException;

/**
 * Created by ozum on 08.07.2015.
 */
public class ReceiveProcess extends GenericRunnable implements Runnable {

    private TcpClient tcpClient;
    private LocationTrackingClient locationTrackingClient;
    private LocationTrackingDataManager locationTrackingDataManager;
    private SharedConnectionData sharedConnectionData;



    public ReceiveProcess(TcpClient tcpClient, LocationTrackingClient locationTrackingClient, int receiveWindow, SharedConnectionData sharedConnectionData) {
        this.sharedConnectionData = sharedConnectionData;
        this.tcpClient = tcpClient;
        this.locationTrackingClient = locationTrackingClient;
        this.locationTrackingDataManager = new LocationTrackingDataManagerImpl(receiveWindow);

    }

    public void run() {
        LocationTrackingData locationTrackingData = null;
        while (true) {
            if (!Thread.interrupted()) {
                try {
                    byte[] readData = tcpClient.read();
                    locationTrackingData = locationTrackingDataManager.resolve(readData);

                    if (locationTrackingData.getErrorCode() == ErrorCodes.NO_ERROR) {
                        switch (locationTrackingData.getCommand()) {
                            case Command.ACCEPT:
                                System.out.println("Connection Established");
                                locationTrackingClient.onConnectionEstablished();
                                sharedConnectionData.setConnected(true);

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
                            case Command.CONNECTION_SUCCESSFULLY_ENDED:
                                System.out.println("Connection Ended");
                                locationTrackingClient.onConnectionSuccessfullyEnded();
                                break;
                        }
                    } else {
                        switch (locationTrackingData.getCommand()) {
                            case Command.REJECT:
                                locationTrackingClient.onConnectionFailed(locationTrackingData.getErrorCode(), "Error in rejected");
                                break;
                            default:
                                locationTrackingClient.onError(locationTrackingData.getErrorCode(), "Unknown error From server");
                                break;

                        }

                    }


                } catch (IOException e) {
                    e.printStackTrace();

                    if (!Thread.interrupted()) {
                        locationTrackingClient.onConnectionLost();
                    }

                    //End receive cycle
                    return;
                }
            } else { //If interrupted break the receive cycle
                break;
            }

        }


    }
}
