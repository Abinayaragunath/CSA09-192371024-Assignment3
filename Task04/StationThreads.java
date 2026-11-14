
class Station implements Runnable {

    private final String stationName;

    Station(String stationName) {
        this.stationName = stationName;
    }

    @Override
    public void run() {
        System.out.println(stationName + " started.");

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println(stationName + " interrupted.");
            return;
        }

        System.out.println(stationName + " completed.");
    }
}

public class StationThreads {

    public static void main(String[] args) {

        Thread t1 = new Thread(new Station("Station 1"));
        Thread t2 = new Thread(new Station("Station 2"));
        Thread t3 = new Thread(new Station("Station 3"));
        Thread t4 = new Thread(new Station("Station 4"));

        // Set priorities
        t1.setPriority(Thread.MIN_PRIORITY);
        t2.setPriority(Thread.NORM_PRIORITY);
        t3.setPriority(Thread.MAX_PRIORITY);
        t4.setPriority(Thread.NORM_PRIORITY);

        // Set one thread as daemon
        t4.setDaemon(true);

        System.out.println("Starting four stations...");

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Main thread interrupted.");
        }

        System.out.println("\nAll station threads finished.");

        System.out.println("Station 1 alive: " + t1.isAlive());
        System.out.println("Station 2 alive: " + t2.isAlive());
        System.out.println("Station 3 alive: " + t3.isAlive());
        System.out.println("Station 4 alive: " + t4.isAlive());

        System.out.println("Station 3 priority: " + t3.getPriority());
        System.out.println("Station 4 is daemon: " + t4.isDaemon());
    }
}
