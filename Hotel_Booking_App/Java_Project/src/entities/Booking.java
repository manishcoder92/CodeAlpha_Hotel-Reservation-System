package entities;

public class Booking {
    private int bookingId;
    private int roomNumber;
    private String customerEmail;
    private String checkInDate;
    private String checkOutDate;
    private double totalAmount;
    private boolean isActive;

    public Booking(int bookingId, int roomNumber, String customerEmail, String checkInDate, String checkOutDate, double totalAmount) {
        this.bookingId = bookingId;
        this.roomNumber = roomNumber;
        this.customerEmail = customerEmail;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalAmount = totalAmount;
        this.isActive = true;
    }

    public int getBookingId() { return bookingId; }
    public int getRoomNumber() { return roomNumber; }
    public String getCustomerEmail() { return customerEmail; }
    public String getCheckInDate() { return checkInDate; }
    public String getCheckOutDate() { return checkOutDate; }
    public double getTotalAmount() { return totalAmount; }
    public boolean isActive() { return isActive; }

    public void cancel() { this.isActive = false; }
}
