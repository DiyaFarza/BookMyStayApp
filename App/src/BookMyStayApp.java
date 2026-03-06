/**
 * Use Case 6: Reservation Confirmation & Room Allocation
 *
 * Demonstrates safe room allocation, inventory updates,
 * and prevention of double-booking using Set and HashMap.
 *
 * @author Jai Aaditya
 * @version 6.1
 */

import java.util.*;

// Reservation class
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
}

// Booking Request Queue (FIFO)
class BookingRequestQueue {

    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    public void addRequest(Reservation r) {
        queue.add(r);
    }

    public Reservation getNextRequest() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

// Inventory Service
class InventoryService {

    private HashMap<String, Integer> inventory;

    public InventoryService() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decrementRoom(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }
}

// Booking Service
class BookingService {

    private InventoryService inventory;

    // Track allocated room IDs
    private Set<String> allocatedRooms;

    // Map room type → assigned room IDs
    private HashMap<String, Set<String>> roomAllocations;

    private int roomCounter = 1;

    public BookingService(InventoryService inventory) {
        this.inventory = inventory;
        allocatedRooms = new HashSet<>();
        roomAllocations = new HashMap<>();
    }

    public void processReservation(Reservation reservation) {

        String roomType = reservation.getRoomType();

        if (inventory.getAvailability(roomType) <= 0) {
            System.out.println("No rooms available for " + roomType +
                    " for guest " + reservation.getGuestName());
            return;
        }

        // Generate unique room ID
        String roomID = roomType.replace(" ", "").substring(0,3).toUpperCase()
                + roomCounter++;

        // Ensure uniqueness
        if (!allocatedRooms.contains(roomID)) {

            allocatedRooms.add(roomID);

            roomAllocations.putIfAbsent(roomType, new HashSet<>());
            roomAllocations.get(roomType).add(roomID);

            inventory.decrementRoom(roomType);

            System.out.println("Reservation confirmed for "
                    + reservation.getGuestName()
                    + " | Room Type: " + roomType
                    + " | Assigned Room ID: " + roomID);
        }
    }
}

// Main class
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("====================================");
        System.out.println("       Book My Stay Application     ");
        System.out.println("           Version 6.1              ");
        System.out.println("====================================");

        BookingRequestQueue queue = new BookingRequestQueue();
        InventoryService inventory = new InventoryService();
        BookingService bookingService = new BookingService(inventory);

        // Add booking requests
        queue.addRequest(new Reservation("Amit", "Single Room"));
        queue.addRequest(new Reservation("Priya", "Double Room"));
        queue.addRequest(new Reservation("Rahul", "Suite Room"));
        queue.addRequest(new Reservation("Neha", "Single Room"));

        System.out.println("\nProcessing Booking Requests...\n");

        // FIFO processing
        while (!queue.isEmpty()) {
            Reservation r = queue.getNextRequest();
            bookingService.processReservation(r);
        }

        System.out.println("\nAll reservations processed.");
    }
}