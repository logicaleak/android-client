package net.lojika.tag.tracking.process;

import net.lojika.tag.tracking.network.TcpClient;

/**
 * Created by ozum on 08.07.2015.
 */
public class SharedConnectionData {
    private boolean connected;
    private volatile String tripId;
    private volatile String userId;
    private volatile String token;

    private TcpClient tcpClient;

    public volatile GenericThread receiveThread;
    public volatile GenericThread mainNetworkThread;

    

    public synchronized void setTcpClient(TcpClient tcpClient) {
        this.tcpClient = tcpClient;
    }

    public synchronized TcpClient getTcpClient() {
        return tcpClient;
    }

    public synchronized boolean getConnected() {
        return connected;
    }

    public synchronized void setConnected(boolean connected) {
        this.connected = connected;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
