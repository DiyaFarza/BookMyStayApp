/**
 * Use Case 2: Basic Room Types & Static Availability
 *
 * Demonstrates abstraction, inheritance, polymorphism,
 * and simple availability variables.
 *
 * @author Jai Aaditya
 * @version 2.1
 */

// Abstract Room class
abstract class Room {

    protected String roomType;
    protected int beds;
    protected double price;

    public Room(String roomType, int beds, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.price = price;
    }

    public void displayRoomDetails() {
        System.out.println("Room Type : " + roomType);
        System.out.println("Beds      : " + beds);
        System.out.println("Price     : ₹" + price);
    }
}

// Single Room class
class SingleRoom extends Room {

    public SingleRoom() {
        super("Single Room", 1, 2000);
    }
}

// Double Room class
class DoubleRoom extends Room {

    public DoubleRoom() {
        super("Double Room", 2, 3500);
    }
}

// Suite Room class
class SuiteRoom extends Room {

    public SuiteRoom() {
        super("Suite Room", 3, 6000);
    }
}

// Main class (Application entry point)
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("====================================");
        System.out.println("        Book My Stay Application    ");
        System.out.println("             Version 2.1            ");
        System.out.println("====================================");

        // Polymorphic room objects
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Static availability variables
        int singleAvailable = 10;
        int doubleAvailable = 6;
        int suiteAvailable = 3;

        System.out.println("\n--- Room Details ---");

        single.displayRoomDetails();
        System.out.println("Available : " + singleAvailable);

        System.out.println();

        doubleRoom.displayRoomDetails();
        System.out.println("Available : " + doubleAvailable);

        System.out.println();

        suite.displayRoomDetails();
        System.out.println("Available : " + suiteAvailable);

        System.out.println("\nSystem execution completed.");
    }
}
