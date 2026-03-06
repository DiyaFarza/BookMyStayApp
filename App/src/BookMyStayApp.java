/**
 * Use Case 5: Booking Request (First-Come-First-Served)
 *
 * Demonstrates how booking requests are stored using a Queue
 * to maintain arrival order.
 *
 * @author Jai Aaditya
 * @version 5.1
 */

import java.util.LinkedList;
import java.util.Queue;

// Reservation class representing a booking request
class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void displayReservation() {
        System.out.println("Guest: " + guestName + " | Requested Room: " + roomType);
    }
}

// Booking Request Queue
class BookingRequestQueue {

    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    // Add booking request
    public void addRequest(Reservation reservation) {
        requestQueue.add(reservation);
        System.out.println("Booking request added for " + reservation.getGuestName());
    }

    // Display queued requests
    public void displayRequests() {

        System.out.println("\n--- Booking Requests in Queue ---");

        for (Reservation r : requestQueue) {
            r.displayReservation();
        }
    }
}

// Main class
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("====================================");
        System.out.println("       Book My Stay Application     ");
        System.out.println("           Version 5.1              ");
        System.out.println("====================================");

        // Initialize booking queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Guests submit booking requests
        bookingQueue.addRequest(new Reservation("Amit", "Single Room"));
        bookingQueue.addRequest(new Reservation("Priya", "Double Room"));
        bookingQueue.addRequest(new Reservation("Rahul", "Suite Room"));

        // Display queued booking requests
        bookingQueue.displayRequests();

        System.out.println("\nAll requests stored in arrival order (FIFO).");
        System.out.println("No inventory allocation performed yet.");
    }
}