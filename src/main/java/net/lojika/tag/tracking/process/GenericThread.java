package net.lojika.tag.tracking.process;

/**
 * Created by ozum on 08.07.2015.
 */
public class GenericThread extends Thread {
    public GenericThread(Runnable runnable) {
        super(runnable);
    }
}
