
class BookingSystem {

    private final boolean[] booked = new boolean[10];
    private int bookingsMade = 0;

    // Synchronized method prevents double booking
    public synchronized void book(int seat) {

        if (seat < 0 || seat >= booked.length) {
            System.out.println("Invalid seat: " + seat);
            return;
        }

        if (!booked[seat]) {
            booked[seat] = true;
            bookingsMade++;
            System.out.println(
                Thread.currentThread().getName()
                + " booked seat " + seat
            );
        } else {
            System.out.println(
                Thread.currentThread().getName()
                + " could not book seat " + seat
            );
        }
    }

    public int getBookingsMade() {
        return bookingsMade;
    }
}

public class SeatBooking {

    public static void main(String[] args)
            throws InterruptedException {

        BookingSystem system = new BookingSystem();
        Thread[] customers = new Thread[20];

        System.out.println("CINEMA SEAT BOOKING SYSTEM");

        // 20 customers compete for the same 10 seats
        for (int i = 0; i < customers.length; i++) {
            final int seat = i % 10;

            customers[i] = new Thread(
                () -> system.book(seat),
                "Customer-" + (i + 1)
            );

            customers[i].start();
        }

        for (Thread customer : customers) {
            customer.join();
        }

        System.out.println("\nSeats sold: "
                + system.getBookingsMade());

        System.out.println("Available seats: "
                + (10 - system.getBookingsMade()));
    }
}
