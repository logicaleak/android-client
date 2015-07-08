package net.lojika.tag.tracking.model;

/**
 * Created by ozum on 08.07.2015.
 */
public class Location {
    private String tripId;
    private String userId;
    private double lat;
    private double lng;

    public Location(String tripId, String userId, double lat, double lng) {
        this.tripId = tripId;
        this.userId = userId;
        this.lat = lat;
        this.lng = lng;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
