package net.lojika.tag.tracking.data;

import java.io.IOException;

/**
 * Created by ozum on 08.07.2015.
 */
public interface LocationTrackingDataManager {
    LocationTrackingData resolve(byte[] data);
    byte[] makeStartOperationData(String userId, String token, String tripId) throws IOException;
    byte[] makeLocationData(String userId, String tripId, double lat, double lon) throws IOException;
}
