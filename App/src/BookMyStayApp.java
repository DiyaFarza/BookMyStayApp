import java.util.*;

/*
Use Case 8: Booking History & Reporting
Stores confirmed reservations and allows admins to generate reports.
*/

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

    public void displayReservation() {
        System.out.println(
                "Reservation ID: " + reservationId +
                        ", Guest: " + guestName +
                        ", Room Type: " + roomType
        );
    }
}

class BookingHistory {

    // Stores reservations in insertion order
    private List<Reservation> reservationList;

    public BookingHistory() {
        reservationList = new ArrayList<>();
    }

    // Add confirmed reservation
    public void addReservation(Reservation reservation) {
        reservationList.add(reservation);
        System.out.println("Reservation stored in history: " + reservation.getReservationId());
    }

    // Retrieve reservation list
    public List<Reservation> getReservations() {
        return reservationList;
    }
}

class BookingReportService {

    // Generate report from booking history
    public void generateReport(List<Reservation> reservations) {

        System.out.println("\n---- Booking History Report ----");

        if (reservations.isEmpty()) {
            System.out.println("No bookings available.");
            return;
        }

        for (Reservation reservation : reservations) {
            reservation.displayReservation();
        }

        System.out.println("\nTotal Bookings: " + reservations.size());
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        BookingHistory bookingHistory = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        // Simulating confirmed bookings
        Reservation r1 = new Reservation("RES101", "Amit", "Deluxe");
        Reservation r2 = new Reservation("RES102", "Rahul", "Suite");
        Reservation r3 = new Reservation("RES103", "Priya", "Standard");

        // Add to booking history
        bookingHistory.addReservation(r1);
        bookingHistory.addReservation(r2);
        bookingHistory.addReservation(r3);

        // Admin requests booking report
        reportService.generateReport(bookingHistory.getReservations());
    }
}