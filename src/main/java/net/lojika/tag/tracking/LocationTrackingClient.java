package net.lojika.tag.tracking;


import net.lojika.tag.tracking.model.Location;

/**
 * Created by ozum on 21.06.2015.
 */
abstract public class LocationTrackingClient {
    static {
        System.loadLibrary("location_tracking_android_client");
    }

    private String host;
    private short port;
    private String tripId;
    private String token;

    public String getTripId() {
        return tripId;
    }

    public void connectAndSubscribe(String host, String port, String userId, String token, String tripId) {

    }

    public void endConnection() {

    }

    public void sendLocation(double lat, double lng) {

    }


    public abstract void onConnectionSuccessfullyEnded();

    public abstract void onConnectionEstablished();

    public abstract void onConnectionFailed(int errorCode, String errorMessage);

    public abstract void onLocationArrive(Location location);

    public abstract void onConnectionLost();

    public abstract void onError(int errorCode, String errorMessage);



}
