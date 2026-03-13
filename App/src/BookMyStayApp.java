import java.util.*;

/*
Use Case 10: Booking Cancellation & Inventory Rollback
Implements safe booking cancellation and restores inventory using rollback logic.
*/

/* Reservation Class */
class Reservation {

    private String reservationId;
    private String roomType;
    private String roomId;
    private boolean active;

    public Reservation(String reservationId, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.roomType = roomType;
        this.roomId = roomId;
        this.active = true;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }

    public boolean isActive() {
        return active;
    }

    public void cancel() {
        active = false;
    }
}

/* Inventory Service */
class InventoryService {

    private Map<String, Integer> inventory;

    public InventoryService() {
        inventory = new HashMap<>();
        inventory.put("Standard", 2);
        inventory.put("Deluxe", 1);
        inventory.put("Suite", 1);
    }

    public void restoreRoom(String roomType) {
        inventory.put(roomType, inventory.getOrDefault(roomType, 0) + 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " rooms available: " + inventory.get(type));
        }
    }
}

/* Booking History */
class BookingHistory {

    private Map<String, Reservation> reservations;

    public BookingHistory() {
        reservations = new HashMap<>();
    }

    public void addReservation(Reservation reservation) {
        reservations.put(reservation.getReservationId(), reservation);
    }

    public Reservation getReservation(String reservationId) {
        return reservations.get(reservationId);
    }
}

/* Cancellation Service */
class CancellationService {

    private Stack<String> rollbackStack;
    private BookingHistory bookingHistory;
    private InventoryService inventoryService;

    public CancellationService(BookingHistory bookingHistory, InventoryService inventoryService) {
        this.bookingHistory = bookingHistory;
        this.inventoryService = inventoryService;
        rollbackStack = new Stack<>();
    }

    public void cancelBooking(String reservationId) {

        Reservation reservation = bookingHistory.getReservation(reservationId);

        if (reservation == null) {
            System.out.println("Cancellation failed: Reservation does not exist.");
            return;
        }

        if (!reservation.isActive()) {
            System.out.println("Cancellation failed: Reservation already cancelled.");
            return;
        }

        // Record rollback information
        rollbackStack.push(reservation.getRoomId());

        // Restore inventory
        inventoryService.restoreRoom(reservation.getRoomType());

        // Update reservation state
        reservation.cancel();

        System.out.println("Booking cancelled successfully for Reservation ID: " + reservationId);
        System.out.println("Released Room ID: " + rollbackStack.peek());
    }
}

/* Main Application */
public class BookMyStayApp {

    public static void main(String[] args) {

        InventoryService inventoryService = new InventoryService();
        BookingHistory bookingHistory = new BookingHistory();

        // Simulating confirmed bookings
        Reservation r1 = new Reservation("RES301", "Deluxe", "D101");
        Reservation r2 = new Reservation("RES302", "Suite", "S201");

        bookingHistory.addReservation(r1);
        bookingHistory.addReservation(r2);

        CancellationService cancellationService =
                new CancellationService(bookingHistory, inventoryService);

        // Guest cancels booking
        cancellationService.cancelBooking("RES301");

        // Invalid cancellation attempt
        cancellationService.cancelBooking("RES999");

        // Duplicate cancellation attempt
        cancellationService.cancelBooking("RES301");

        inventoryService.displayInventory();
    }
}