
class BoundedBuffer {

    private final int[] buffer = new int[5];
    private int in = 0;
    private int out = 0;
    private int count = 0;

    public synchronized void put(int token)
            throws InterruptedException {

        while (count == buffer.length) {
            wait();
        }

        buffer[in] = token;
        in = (in + 1) % buffer.length;
        count++;

        System.out.println("Produced: " + token);

        notifyAll();
    }

    public synchronized int take()
            throws InterruptedException {

        while (count == 0) {
            wait();
        }

        int token = buffer[out];
        out = (out + 1) % buffer.length;
        count--;

        System.out.println("Consumed: " + token);

        notifyAll();

        return token;
    }
}

public class ProducerConsumer {

    public static void main(String[] args)
            throws InterruptedException {

        BoundedBuffer buffer = new BoundedBuffer();

        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    buffer.put(i);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    buffer.take();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        System.out.println("PRODUCER-CONSUMER SYSTEM");

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();

        System.out.println("\nAll items processed successfully.");
    }
}
