import java.util.*;

/*
Use Case 11: Concurrent Booking Simulation (Thread Safety)
Demonstrates safe booking allocation under concurrent execution.
*/

/* Booking Request */
class BookingRequest {

    private String guestName;
    private String roomType;

    public BookingRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

/* Shared Booking Queue */
class BookingQueue {

    private Queue<BookingRequest> queue = new LinkedList<>();

    public synchronized void addRequest(BookingRequest request) {
        queue.add(request);
        System.out.println("Booking request added by " + request.getGuestName());
    }

    public synchronized BookingRequest getRequest() {
        return queue.poll();
    }
}

/* Inventory Service (Shared Resource) */
class InventoryService {

    private Map<String, Integer> inventory;

    public InventoryService() {
        inventory = new HashMap<>();
        inventory.put("Standard", 2);
        inventory.put("Deluxe", 1);
        inventory.put("Suite", 1);
    }

    public synchronized boolean allocateRoom(String roomType, String guestName) {

        int available = inventory.getOrDefault(roomType, 0);

        if (available > 0) {

            inventory.put(roomType, available - 1);

            System.out.println(
                    "Room allocated: " + roomType +
                            " to Guest: " + guestName +
                            " | Remaining: " + (available - 1)
            );

            return true;
        }

        System.out.println(
                "Booking failed for " + guestName +
                        " | No " + roomType + " rooms available"
        );

        return false;
    }

    public void displayInventory() {
        System.out.println("\nFinal Inventory State:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " rooms available: " + inventory.get(type));
        }
    }
}

/* Booking Processor Thread */
class BookingProcessor extends Thread {

    private BookingQueue queue;
    private InventoryService inventoryService;

    public BookingProcessor(BookingQueue queue, InventoryService inventoryService) {
        this.queue = queue;
        this.inventoryService = inventoryService;
    }

    public void run() {

        while (true) {

            BookingRequest request;

            synchronized (queue) {
                request = queue.getRequest();
            }

            if (request == null) {
                break;
            }

            inventoryService.allocateRoom(
                    request.getRoomType(),
                    request.getGuestName()
            );
        }
    }
}

/* Main Application */
public class BookMyStayApp {

    public static void main(String[] args) {

        BookingQueue bookingQueue = new BookingQueue();
        InventoryService inventoryService = new InventoryService();

        /* Simulating multiple guests submitting requests */
        bookingQueue.addRequest(new BookingRequest("Amit", "Standard"));
        bookingQueue.addRequest(new BookingRequest("Rahul", "Standard"));
        bookingQueue.addRequest(new BookingRequest("Priya", "Deluxe"));
        bookingQueue.addRequest(new BookingRequest("Neha", "Suite"));
        bookingQueue.addRequest(new BookingRequest("Karan", "Deluxe"));

        /* Concurrent booking processors */
        BookingProcessor t1 = new BookingProcessor(bookingQueue, inventoryService);
        BookingProcessor t2 = new BookingProcessor(bookingQueue, inventoryService);
        BookingProcessor t3 = new BookingProcessor(bookingQueue, inventoryService);

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        inventoryService.displayInventory();
    }
}