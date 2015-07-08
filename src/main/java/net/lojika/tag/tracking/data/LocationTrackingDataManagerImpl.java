package net.lojika.tag.tracking.data;

import net.lojika.tag.tracking.constant.Command;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Created by ozum on 08.07.2015.
 */
public class LocationTrackingDataManagerImpl implements LocationTrackingDataManager {
    private int receiveWindow;

    private static LocationTrackingDataManager locationTrackingDataManager;

    public static LocationTrackingDataManager getLocationTrackingDataManager(int receiveWindow) {
        if (locationTrackingDataManager == null) {
            locationTrackingDataManager = new LocationTrackingDataManagerImpl(receiveWindow);
            return locationTrackingDataManager;
        } else {
            return locationTrackingDataManager;
        }
    }

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
                float lat = ByteBuffer.wrap(latBytes).getFloat();
                float lon = ByteBuffer.wrap(lonBytes).getFloat();

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

        byte[] data = new byte[receiveWindow];
        ByteBuffer.wrap(data)
                .put(errorCodeByte)
                .put(commandByte)
                .put(tokenBytes)
                .put(tripIdBytes);

        return data;
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
