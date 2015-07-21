package net.lojika.tag.tracking.process;

import net.lojika.tag.tracking.constant.ErrorCodes;
import net.lojika.tag.tracking.data.LocationTrackingDataManager;
import net.lojika.tag.tracking.data.LocationTrackingDataManagerImpl;
import net.lojika.tag.tracking.network.TcpClient;
import net.lojika.tag.tracking.output.LocationTrackingClient;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

/**
 * Created by ozum on 08.07.2015.
 */
public class MainNetworkProcess extends GenericRunnable implements Runnable {

    private String address;
    private int port;
    private int receiveWindow;
    private LocationTrackingClient locationTrackingClient;
    private LocationTrackingDataManager locationTrackingDataManager;

    //will be used later
    private String userId;
    private String tripId;
    private String token;

    private int retries;
    private int timeout;

    private ReceiveProcess receiveProcess;

    private SharedConnectionData sharedConnectionData;

    public MainNetworkProcess(String address, int port, int receiveWindow, LocationTrackingClient locationTrackingClient,
                              String userId, String token, String tripId, SharedConnectionData sharedConnectionData, int retries, int timeout) {
        this.address = address;
        this.port = port;
        this.receiveWindow = receiveWindow;
        this.locationTrackingClient = locationTrackingClient;
        this.sharedConnectionData = sharedConnectionData;
        this.locationTrackingDataManager = new LocationTrackingDataManagerImpl(receiveWindow);

        this.userId = userId;
        this.tripId = tripId;
        this.token = token;

        this.retries = retries;
        this.timeout = timeout;
    }

    public void run() {
        try {
            System.out.println("Connection is being started");
            while(retries > 0) {
                try {
                    this.sharedConnectionData.setTcpClient(new TcpClient(this.address, this.port, this.receiveWindow, timeout));
                    break;
                } catch (SocketTimeoutException e) {
                    this.retries = this.retries - 1;
                }
            }
            TcpClient tcpClient = this.sharedConnectionData.getTcpClient();

            byte[] startOperationBytes = locationTrackingDataManager.makeStartOperationData(userId, token, tripId);
            tcpClient.send(startOperationBytes);

            //If a pre created receiveThread exists, interrupt it. No need for it to work...
            if (this.sharedConnectionData.receiveThread != null) {
                this.sharedConnectionData.receiveThread.interrupt();
            }

            this.receiveProcess = new ReceiveProcess(tcpClient, this.locationTrackingClient, this.receiveWindow, this.sharedConnectionData);
            sharedConnectionData.receiveThread = new GenericThread(this.receiveProcess);
            sharedConnectionData.receiveThread.start();

        } catch (IOException e) {
            //print it ?
            e.printStackTrace();
            locationTrackingClient.onConnectionFailed(ErrorCodes.CONNECTION_FAILED, "Initial connection has failed");
        }


    }

}
