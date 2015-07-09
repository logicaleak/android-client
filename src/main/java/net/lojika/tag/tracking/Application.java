package net.lojika.tag.tracking;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ozum on 09.07.2015.
 */
public class Application {
    public static void main(String[] args) {
        List<LocationImpl> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            LocationImpl location = new LocationImpl();
            location.connectAndSubscribe("127.0.0.1", 5001, "aaaaaaaaaaaaaaaaaaaaaaaa", "tttttttttttttttt", "bbbbbbbbbbbbbbbbbbbbbbbb");
            list.add(location);
        }

        for (int i = 0; i < 50; i++) {
            LocationImpl location = new LocationImpl();
            location.connectAndSubscribe("127.0.0.1", 5001, "aaaaaaaaaaaaaaaaaaaaaaa1", "tttttttttttttttt", "cccccccccccccccccccccccc");
            list.add(location);
        }

        for (int i = 0; i < 50; i++) {
            LocationImpl location = new LocationImpl();
            location.connectAndSubscribe("127.0.0.1", 5001, "aaaaaaaaaaaaaaaaaaaaaaa2", "tttttttttttttttt", "dddddddddddddddddddddddd");
            list.add(location);
        }

        for (int i = 0; i < 50; i++) {
            LocationImpl location = new LocationImpl();
            location.connectAndSubscribe("127.0.0.1", 5001, "aaaaaaaaaaaaaaaaaaaaaaa3", "tttttttttttttttt", "eeeeeeeeeeeeeeeeeeeeeeee");
            list.add(location);
        }

    }
}
