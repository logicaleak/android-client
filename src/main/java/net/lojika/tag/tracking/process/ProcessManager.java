package net.lojika.tag.tracking.process;

/**
 * Created by ozum on 08.07.2015.
 */
public class ProcessManager {
    public static void spawnProcess(Runnable runnable) {
        new GenericThread(runnable).start();
    }
}
