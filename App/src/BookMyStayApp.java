/**
 * Use Case 3: Centralized Room Inventory Management
 *
 * Demonstrates how a HashMap can be used to maintain
 * a centralized inventory of room availability.
 *
 * @author Jai Aaditya
 * @version 3.1
 */

import java.util.HashMap;

// Inventory management class
class RoomInventory {

    // HashMap storing room type and availability
    private HashMap<String, Integer> inventory;

    // Constructor initializes inventory
    public RoomInventory() {
        inventory = new HashMap<>();

        inventory.put("Single Room", 10);
        inventory.put("Double Room", 6);
        inventory.put("Suite Room", 3);
    }

    // Method to get availability of a room type
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Method to update availability
    public void updateAvailability(String roomType, int newCount) {
        inventory.put(roomType, newCount);
    }

    // Display full inventory
    public void displayInventory() {
        System.out.println("\n--- Current Room Inventory ---");

        for (String roomType : inventory.keySet()) {
            System.out.println(roomType + " : " + inventory.get(roomType));
        }
    }
}

// Main class
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("====================================");
        System.out.println("       Book My Stay Application     ");
        System.out.println("           Version 3.1              ");
        System.out.println("====================================");

        // Initialize inventory system
        RoomInventory inventory = new RoomInventory();

        // Display current inventory
        inventory.displayInventory();

        // Example update
        System.out.println("\nUpdating inventory for Single Room...");

        inventory.updateAvailability("Single Room", 8);

        // Display updated inventory
        inventory.displayInventory();

        System.out.println("\nInventory system execution completed.");
    }
}
