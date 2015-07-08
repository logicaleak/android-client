import net.lojika.tag.tracking.model.Location;
import net.lojika.tag.tracking.output.LocationTrackingClient;

/**
 * Created by ozum on 08.07.2015.
 */
public class LocationTrackingImpl extends LocationTrackingClient {
    @Override
    public void onConnectionSuccessfullyEnded() {
        System.out.println("Connection Ended Successfully");
    }

    @Override
    public void onConnectionEstablished() {
        System.out.println("onConnectionEstablished");
    }

    @Override
    public void onConnectionFailed(int errorCode, String errorMessage) {
        System.out.println("onConnectionFailed");
    }

    @Override
    public void onLocationArrive(Location location) {
        System.out.println("onLocationArrive");
        System.out.println("Location lat : " + location.getLat() + " lon : " + location.getLng());
    }

    @Override
    public void onConnectionLost() {
        System.out.println("onConnectionLost");
    }

    @Override
    public void onError(int errorCode, String errorMessage) {
        System.out.println("onError");
    }
}
