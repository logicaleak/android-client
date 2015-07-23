package net.lojika.tag.tracking;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ozum on 09.07.2015.
 */
public class Application {
    public static void main(String[] args) {
        String a = null;
        System.out.println(a);

        List<LocationImpl> list = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            LocationImpl location = new LocationImpl();
            location.connectAndSubscribe("dev.lojika.net", 5002, "55a54e4d0cf23052e530c31e", "efb60586-9f55-463d-ae42-4a508de0e882", "55af7e330cf2e5b476c59ac3",3 , 5000);

        }


    }
}
