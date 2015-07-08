package net.lojika.tag.tracking.model;

/**
 * Created by ozum on 08.07.2015.
 */
public class Location {
    private String tripId;
    private String userId;
    private float lat;
    private float lng;

    public Location(String tripId, String userId, float lat, float lng) {
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

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }
}
