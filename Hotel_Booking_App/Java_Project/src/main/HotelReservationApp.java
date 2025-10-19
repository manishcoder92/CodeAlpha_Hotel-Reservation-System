package main;

import gui.HotelManagementUI;

public class HotelReservationApp {
    public static void main(String[] args) {
        // Launch the GUI
        javax.swing.SwingUtilities.invokeLater(HotelManagementUI::new);
    }
}
