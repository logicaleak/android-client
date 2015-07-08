import net.lojika.tag.tracking.output.LocationTrackingClient;

import java.util.Arrays;

/**
 * Created by ozum on 08.07.2015.
 */
public class Application {
    public static class A {
        int a;

        public A(int a) {
            this.a = a;
        }

        public void setA(int a) {
            this.a = a;
        }
    }

    public static class B {
        A a;

        public B(int k) {
            this.a = new A(k);
        }

        public A getA() {
            return a;
        }

        public void setAinA(int k) {
            this.a.setA(k);
        }
    }

    public static void main(String[] args) {
        LocationTrackingClient locationTrackingClient = new LocationTrackingImpl();
        locationTrackingClient.connectAndSubscribe("127.0.0.1", 5001, "aaaaaaaaaaaaaaaaaaaaaaaa", "tttttttttttttttt", "bbbbbbbbbbbbbbbbbbbbbbbb");

        Object object = new Object();
        try {

            synchronized (object) {

                object.wait();
            }
        } catch (Exception e) {

        }
    }
}
