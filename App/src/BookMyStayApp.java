import java.util.*;

/*
 Use Case 7: Add-On Service Selection
 Demonstrates adding optional services to an existing reservation
 without modifying core booking logic.
*/

class AddOnService {

    private String serviceName;
    private double cost;

    public AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }
}

class AddOnServiceManager {

    // Map<ReservationID, List of Services>
    private Map<String, List<AddOnService>> reservationServices;

    public AddOnServiceManager() {
        reservationServices = new HashMap<>();
    }

    // Add service to reservation
    public void addService(String reservationId, AddOnService service) {

        reservationServices.putIfAbsent(reservationId, new ArrayList<>());

        reservationServices.get(reservationId).add(service);

        System.out.println("Service added: " + service.getServiceName() +
                " for Reservation ID: " + reservationId);
    }

    // Calculate total cost of services
    public double calculateTotalCost(String reservationId) {

        double total = 0;

        List<AddOnService> services = reservationServices.get(reservationId);

        if (services != null) {
            for (AddOnService service : services) {
                total += service.getCost();
            }
        }

        return total;
    }

    // Display services
    public void displayServices(String reservationId) {

        List<AddOnService> services = reservationServices.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No services selected for reservation: " + reservationId);
            return;
        }

        System.out.println("\nServices for Reservation " + reservationId + ":");

        for (AddOnService service : services) {
            System.out.println("- " + service.getServiceName() +
                    " (Cost: ₹" + service.getCost() + ")");
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        AddOnServiceManager manager = new AddOnServiceManager();

        // Example Reservation ID
        String reservationId = "RES101";

        // Available services
        AddOnService breakfast = new AddOnService("Breakfast", 500);
        AddOnService airportPickup = new AddOnService("Airport Pickup", 1200);
        AddOnService spa = new AddOnService("Spa Access", 1500);

        // Guest selects services
        manager.addService(reservationId, breakfast);
        manager.addService(reservationId, airportPickup);
        manager.addService(reservationId, spa);

        // Display selected services
        manager.displayServices(reservationId);

        // Calculate additional cost
        double totalCost = manager.calculateTotalCost(reservationId);

        System.out.println("\nTotal Additional Cost: ₹" + totalCost);
    }
}