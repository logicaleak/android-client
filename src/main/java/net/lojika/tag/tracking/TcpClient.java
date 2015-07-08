package net.lojika.tag.tracking;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by ozum on 08.07.2015.
 */
public class TcpClient {
    private String address;
    private int port;
    private boolean connected;
    private Socket socket;

    private int receiveWindow;

    private byte[] readData;

    public TcpClient(String address, int port, int receiveWindow) throws UnknownHostException, IOException {
        this.address = address;
        this.port = port;
        this.receiveWindow = receiveWindow;

        this.connected = false;
        this.socket = new Socket(address, port);
        this.connected = true;

        readData = new byte[receiveWindow];
    }

    public void send(byte[] data) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(data, 0, this.receiveWindow);
    }

    public byte[] read() throws IOException {
        InputStream inputStream = socket.getInputStream();
        //Blocks
        inputStream.read(readData, 0, receiveWindow);

        //Assume it is returned by copy
        return readData;
    }
}
