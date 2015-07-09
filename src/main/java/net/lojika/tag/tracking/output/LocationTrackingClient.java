package net.lojika.tag.tracking.output;


import net.lojika.tag.tracking.data.LocationTrackingDataManager;
import net.lojika.tag.tracking.data.LocationTrackingDataManagerImpl;
import net.lojika.tag.tracking.network.TcpClient;
import net.lojika.tag.tracking.constant.ErrorCodes;
import net.lojika.tag.tracking.model.Location;
import net.lojika.tag.tracking.process.GenericThread;
import net.lojika.tag.tracking.process.MainNetworkProcess;
import net.lojika.tag.tracking.process.ProcessManager;
import net.lojika.tag.tracking.process.SharedConnectionData;

import java.io.IOException;

/**
 * Created by ozum on 21.06.2015.
 */
abstract public class LocationTrackingClient {

    public LocationTrackingClient() {
        this.locationTrackingDataManager = LocationTrackingDataManagerImpl.getLocationTrackingDataManager(100);
        this.sharedConnectionData = new SharedConnectionData();
    }


    private LocationTrackingDataManager locationTrackingDataManager;

    private SharedConnectionData sharedConnectionData;

    public String getTripId() {
        return sharedConnectionData.getTripId();
    }

    public void connectAndSubscribe(String host, int port, String userId, String token, String tripId) {
        ProcessManager.spawnProcess(new Runnable() {
            @Override
            public void run() {
                sharedConnectionData.setConnected(false);
            }
        });

        MainNetworkProcess mainNetworkProcess = new MainNetworkProcess(host, port, 100, this, userId, token, tripId, sharedConnectionData);
        GenericThread mainNetworkThread = new GenericThread(mainNetworkProcess);
        mainNetworkThread.start();


        this.sharedConnectionData.setTripId(tripId);
        this.sharedConnectionData.setToken(token);
        this.sharedConnectionData.setUserId(userId);

    }

    public void endConnection() {

        ProcessManager.spawnProcess(new Runnable() {
            @Override
            public void run() {
                try {
                    TcpClient tcpClient = sharedConnectionData.getTcpClient();
                    if (tcpClient != null) {

                        tcpClient.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    onError(ErrorCodes.NOT_CONNECTED, "Error in endConnection, not connected");
                }
            }
        });


    }

    public void sendLocation(final double lat, final double lng) {

        ProcessManager.spawnProcess(new Runnable() {
            @Override
            public void run() {
                try {
                    TcpClient tcpClient = sharedConnectionData.getTcpClient();
                    synchronized (sharedConnectionData) {
                        if (tcpClient != null) {
                            byte[] data =
                                    locationTrackingDataManager.makeLocationData(sharedConnectionData.getUserId(), sharedConnectionData.getTripId(), lat, lng);
                            tcpClient.send(data);
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    onError(ErrorCodes.NOT_CONNECTED, "Error in sendLocation, not connected");
                }
            }
        });

    }


    public abstract void onConnectionSuccessfullyEnded();

    public abstract void onConnectionEstablished();

    public abstract void onConnectionFailed(int errorCode, String errorMessage);

    public abstract void onLocationArrive(Location location);

    public abstract void onConnectionLost();

    public abstract void onError(int errorCode, String errorMessage);



}
