package entities;

    public class Room {
        private int roomNumber;
        private String category; // Standard, Deluxe, Suite
        private boolean isAvailable;
        private double price;

        public Room(int roomNumber, String category, double price) {
            this.roomNumber = roomNumber;
            this.category = category;
            this.price = price;
            this.isAvailable = true;
        }

        public int getRoomNumber() { return roomNumber; }
        public String getCategory() { return category; }
        public boolean isAvailable() { return isAvailable; }
        public double getPrice() { return price; }

        public void setAvailable(boolean available) { this.isAvailable = available; }
    }


