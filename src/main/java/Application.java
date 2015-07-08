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
        B b = new B(5);
        A oldA = b.getA();
        System.out.println("oldA" + oldA.a);

        b.setAinA(10);

        System.out.println("newA" + oldA.a);
    }
}
