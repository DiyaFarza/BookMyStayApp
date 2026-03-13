import java.util.*;

/*
Use Case 9: Error Handling & Validation
Introduces validation and custom exception handling to maintain system reliability.
*/

/* Custom Exception */
class InvalidBookingException extends Exception {

    public InvalidBookingException(String message) {
        super(message);
    }
}

/* Reservation Class */
class Reservation {

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
}

/* Booking Validator */
class InvalidBookingValidator {

    private Set<String> validRoomTypes;

    public InvalidBookingValidator() {
        validRoomTypes = new HashSet<>();
        validRoomTypes.add("Standard");
        validRoomTypes.add("Deluxe");
        validRoomTypes.add("Suite");
    }

    public void validateRoomType(String roomType) throws InvalidBookingException {

        if (!validRoomTypes.contains(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }
    }
}

/* Inventory Service */
class InventoryService {

    private Map<String, Integer> roomInventory;

    public InventoryService() {

        roomInventory = new HashMap<>();
        roomInventory.put("Standard", 2);
        roomInventory.put("Deluxe", 1);
        roomInventory.put("Suite", 1);
    }

    public void allocateRoom(String roomType) throws InvalidBookingException {

        int available = roomInventory.getOrDefault(roomType, 0);

        if (available <= 0) {
            throw new InvalidBookingException("No rooms available for type: " + roomType);
        }

        roomInventory.put(roomType, available - 1);
    }

    public void displayInventory() {

        System.out.println("\nCurrent Inventory State:");
        for (String type : roomInventory.keySet()) {
            System.out.println(type + " rooms available: " + roomInventory.get(type));
        }
    }
}

/* Main Application */
public class BookMyStayApp {

    public static void main(String[] args) {

        InvalidBookingValidator validator = new InvalidBookingValidator();
        InventoryService inventoryService = new InventoryService();

        Reservation r1 = new Reservation("RES201", "Rahul", "Deluxe");
        Reservation r2 = new Reservation("RES202", "Anita", "Luxury"); // Invalid room type
        Reservation r3 = new Reservation("RES203", "Vikram", "Suite");

        List<Reservation> reservations = Arrays.asList(r1, r2, r3);

        for (Reservation reservation : reservations) {

            try {

                System.out.println("\nProcessing booking for " + reservation.getGuestName());

                validator.validateRoomType(reservation.getRoomType());

                inventoryService.allocateRoom(reservation.getRoomType());

                System.out.println("Booking confirmed for Reservation ID: "
                        + reservation.getReservationId());

            } catch (InvalidBookingException e) {

                System.out.println("Booking failed: " + e.getMessage());
            }
        }

        inventoryService.displayInventory();
    }
}