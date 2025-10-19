package management;

import entities.Room;
import entities.Booking;
import java.util.*;

public class Hotel {
    private List<Room> rooms;
    private List<Booking> bookings;
    private int nextBookingId = 1;

    public Hotel() {
        rooms = new ArrayList<>();
        bookings = new ArrayList<>();
        // Example: Add some rooms
        rooms.add(new Room(101, "Standard", 1000));
        rooms.add(new Room(102, "Deluxe", 2000));
        rooms.add(new Room(201, "Suite", 3500));
    }

    public List<Room> searchAvailableRooms(String category) {
        List<Room> available = new ArrayList<>();
        for (Room room : rooms) {
            if (room.isAvailable() && room.getCategory().equalsIgnoreCase(category)) {
                available.add(room);
            }
        }
        return available;
    }

    public Booking bookRoom(int roomNumber, String customerEmail, String checkIn, String checkOut) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber && room.isAvailable()) {
                room.setAvailable(false);
                Booking booking = new Booking(nextBookingId++, roomNumber, customerEmail, checkIn, checkOut, room.getPrice());
                bookings.add(booking);
                return booking;
            }
        }
        return null;
    }

    public boolean cancelBooking(int bookingId) {
        for (Booking booking : bookings) {
            if (booking.getBookingId() == bookingId && booking.isActive()) {
                booking.cancel();
                for (Room room : rooms) {
                    if (room.getRoomNumber() == booking.getRoomNumber()) {
                        room.setAvailable(true);
                        break;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public List<Booking> getBookingsByCustomer(String customerEmail) {
        List<Booking> result = new ArrayList<>();
        for (Booking booking : bookings) {
            if (booking.getCustomerEmail().equalsIgnoreCase(customerEmail)) {
                result.add(booking);
            }
        }
        return result;
    }
}
