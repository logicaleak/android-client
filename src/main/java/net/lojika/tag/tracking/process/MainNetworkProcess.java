package net.lojika.tag.tracking.process;

import net.lojika.tag.tracking.constant.ErrorCodes;
import net.lojika.tag.tracking.data.LocationTrackingDataManager;
import net.lojika.tag.tracking.data.LocationTrackingDataManagerImpl;
import net.lojika.tag.tracking.network.TcpClient;
import net.lojika.tag.tracking.output.LocationTrackingClient;

import java.io.IOException;

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

    private ReceiveProcess receiveProcess;

    private SharedConnectionData sharedConnectionData;

    public MainNetworkProcess(String address, int port, int receiveWindow, LocationTrackingClient locationTrackingClient,
                              String userId, String token, String tripId, SharedConnectionData sharedConnectionData) {
        this.address = address;
        this.port = port;
        this.receiveWindow = receiveWindow;
        this.locationTrackingClient = locationTrackingClient;
        this.sharedConnectionData = sharedConnectionData;
        this.locationTrackingDataManager = new LocationTrackingDataManagerImpl(receiveWindow);

        this.userId = userId;
        this.tripId = tripId;
        this.token = token;
    }

    public void run() {
        try {
            System.out.println("Connection is being started");
            this.sharedConnectionData.setTcpClient(new TcpClient(this.address, this.port, this.receiveWindow));
            TcpClient tcpClient = this.sharedConnectionData.getTcpClient();

            byte[] startOperationBytes = locationTrackingDataManager.makeStartOperationData(userId, token, tripId);
            tcpClient.send(startOperationBytes);

            this.receiveProcess = new ReceiveProcess(tcpClient, this.locationTrackingClient, this.receiveWindow, this.sharedConnectionData);
            GenericThread receiveThread = new GenericThread(this.receiveProcess);
            receiveThread.start();

        } catch (IOException e) {
            //print it ?
            e.printStackTrace();
            locationTrackingClient.onConnectionFailed(ErrorCodes.CONNECTION_FAILED, "Initial connection has failed");
        }


    }

}
