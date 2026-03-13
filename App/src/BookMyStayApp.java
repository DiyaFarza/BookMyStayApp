import java.io.*;
import java.util.*;

/*
Use Case 12: Data Persistence & System Recovery
Demonstrates saving and restoring booking system state using serialization.
*/

/* Reservation Class */
class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;

    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public String toString() {
        return reservationId + " | " + guestName + " | " + roomType;
    }
}

/* System State Snapshot */
class SystemState implements Serializable {

    private static final long serialVersionUID = 1L;

    Map<String, Integer> inventory;
    List<Reservation> reservations;

    public SystemState(Map<String, Integer> inventory, List<Reservation> reservations) {
        this.inventory = inventory;
        this.reservations = reservations;
    }
}

/* Persistence Service */
class PersistenceService {

    private static final String FILE_NAME = "booking_system_state.dat";

    public void saveState(SystemState state) {

        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            out.writeObject(state);
            System.out.println("System state saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving system state: " + e.getMessage());
        }
    }

    public SystemState loadState() {

        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            SystemState state = (SystemState) in.readObject();
            System.out.println("System state restored from file.");
            return state;

        } catch (FileNotFoundException e) {

            System.out.println("No saved state found. Starting fresh system.");

        } catch (IOException | ClassNotFoundException e) {

            System.out.println("Error loading saved state. Starting with safe defaults.");
        }

        return null;
    }
}

/* Inventory Service */
class InventoryService {

    Map<String, Integer> inventory = new HashMap<>();

    public InventoryService() {
        inventory.put("Standard", 2);
        inventory.put("Deluxe", 1);
        inventory.put("Suite", 1);
    }

    public void displayInventory() {
        System.out.println("\nInventory State:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " rooms available: " + inventory.get(type));
        }
    }
}

/* Booking History */
class BookingHistory {

    List<Reservation> reservations = new ArrayList<>();

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
        System.out.println("Reservation added: " + reservation.getReservationId());
    }

    public void displayReservations() {

        System.out.println("\nBooking History:");

        if (reservations.isEmpty()) {
            System.out.println("No reservations found.");
            return;
        }

        for (Reservation r : reservations) {
            System.out.println(r);
        }
    }
}

/* Main Application */
public class BookMyStayApp {

    public static void main(String[] args) {

        PersistenceService persistenceService = new PersistenceService();

        InventoryService inventoryService = new InventoryService();
        BookingHistory bookingHistory = new BookingHistory();

        /* Attempt to recover previous state */
        SystemState restoredState = persistenceService.loadState();

        if (restoredState != null) {
            inventoryService.inventory = restoredState.inventory;
            bookingHistory.reservations = restoredState.reservations;
        }

        /* Simulating new booking */
        Reservation r1 = new Reservation("RES401", "Arjun", "Deluxe");
        bookingHistory.addReservation(r1);

        /* Display current system state */
        bookingHistory.displayReservations();
        inventoryService.displayInventory();

        /* Save state before shutdown */
        SystemState stateSnapshot =
                new SystemState(inventoryService.inventory, bookingHistory.reservations);

        persistenceService.saveState(stateSnapshot);

        System.out.println("\nSystem shutdown simulation complete.");
    }
}