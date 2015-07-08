package net.lojika.tag.tracking;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Created by ozum on 08.07.2015.
 */
public class LocationTrackingDataManagerImpl implements LocationTrackingDataManager {
    private int receiveWindow;

    public LocationTrackingDataManagerImpl(int receiveWindow) {
        this.receiveWindow = receiveWindow;
    }

    public LocationTrackingData resolve(byte[] data) {
        LocationTrackingData locationTrackingData = new LocationTrackingData();

        int errorCode = data[0];
        int command = data[1];

        locationTrackingData.setErrorCode(errorCode);
        locationTrackingData.setCommand(command);

        switch (command) {
            case Command.LOCATION:
                byte[] userIdBytes = Arrays.copyOfRange(data, 2, 26);
                byte[] tripIdBytes = Arrays.copyOfRange(data, 26, 50);
                String userId = new String(userIdBytes);
                String tripId = new String(tripIdBytes);

                byte[] latBytes = Arrays.copyOfRange(data, 50, 58);
                byte[] lonBytes = Arrays.copyOfRange(data, 58, 66);
                double lat = ByteBuffer.wrap(latBytes).getDouble();
                double lon = ByteBuffer.wrap(lonBytes).getDouble();

                locationTrackingData.setLat(lat);
                locationTrackingData.setLon(lon);
                locationTrackingData.setUserId(userId);
                locationTrackingData.setTripId(tripId);
        }

        return locationTrackingData;
    }

    public byte[] makeStartOperationData(String token, String tripId) throws IOException {
        byte errorCodeByte = 1;
        byte commandByte = 1;
        byte[] tokenBytes = token.getBytes();
        byte[] tripIdBytes = tripId.getBytes();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(errorCodeByte);
        byteArrayOutputStream.write(commandByte);
        byteArrayOutputStream.write(tokenBytes);
        byteArrayOutputStream.write(tripIdBytes);


        return byteArrayOutputStream.toByteArray();

    }

    public byte[] makeLocationData(String userId, String tripId, double lat, double lon) throws IOException {
        byte errorCodeByte = 1;
        byte commandByte = 4;

        byte[] latBytes = new byte[8];
        byte[] lonBytes = new byte[8];

        ByteBuffer.wrap(latBytes).putDouble(lat);
        ByteBuffer.wrap(lonBytes).putDouble(lon);

        byte[] userIdBytes = userId.getBytes();
        byte[] tripIdBytes = tripId.getBytes();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(errorCodeByte);
        byteArrayOutputStream.write(commandByte);
        byteArrayOutputStream.write(userIdBytes);
        byteArrayOutputStream.write(tripIdBytes);
        byteArrayOutputStream.write(latBytes);
        byteArrayOutputStream.write(lonBytes);

        return byteArrayOutputStream.toByteArray();
    }
}
