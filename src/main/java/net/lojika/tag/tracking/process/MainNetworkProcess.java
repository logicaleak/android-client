package net.lojika.tag.tracking.process;

import net.lojika.tag.tracking.ErrorCodes;
import net.lojika.tag.tracking.LocationTrackingClient;
import net.lojika.tag.tracking.TcpClient;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by ozum on 08.07.2015.
 */
public class MainNetworkProcess extends GenericRunnable implements Runnable {

    private TcpClient tcpClient;
    private String address;
    private int port;
    private int receiveWindow;
    private LocationTrackingClient locationTrackingClient;

    public MainNetworkProcess(String address, int port, int receiveWindow, LocationTrackingClient locationTrackingClient) {
        this.address = address;
        this.port = port;
        this.receiveWindow = receiveWindow;
        this.locationTrackingClient = locationTrackingClient;

    }

    public void run() {
        try {

            tcpClient = new TcpClient(address, port, receiveWindow);
        } catch (IOException e) {
            //print it ?
            e.printStackTrace();
            locationTrackingClient.onConnectionFailed(ErrorCodes.CONNECTION_FAILED, "Initial connection has failed");
        }


    }



    public TcpClient getTcpClient() {
        return tcpClient;
    }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }
}
