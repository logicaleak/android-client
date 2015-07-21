package net.lojika.tag.tracking.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by ozum on 08.07.2015.
 */
public class TcpClient {
    private Socket socket;

    private int receiveWindow;



    public TcpClient(String address, int port, int receiveWindow, int connection_timeout) throws IOException {
        this.receiveWindow = receiveWindow;

        this.socket = new Socket();
        socket.connect(new InetSocketAddress(address, port), connection_timeout);
        System.out.println("Connection established");
    }

    public void send(byte[] data) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(data, 0, this.receiveWindow);
    }

    public byte[] read() throws IOException {
        InputStream inputStream = socket.getInputStream();

        byte[] readData = new byte[receiveWindow];

        //Blocks
        int result = inputStream.read(readData, 0, receiveWindow);

        if (result == -1) {
            throw new IOException("Connection Closed");
        }

        return readData;
    }

    public void close() throws IOException {
        if (socket.isConnected()) {
            socket.close();
        }

    }
}
