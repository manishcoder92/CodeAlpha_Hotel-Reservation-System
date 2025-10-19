package gui;

import management.Hotel;
import entities.Room;
import entities.Booking;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class HotelManagementUI extends JFrame {
    private Hotel hotel;

    public HotelManagementUI() {
        hotel = new Hotel();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Grand Palace Hotel - Reservation System");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create header
        add(createHeaderPanel(), BorderLayout.NORTH);

        // Create tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Book Room", createBookingPanel());
        tabbedPane.addTab("My Bookings", createViewBookingsPanel());
        tabbedPane.addTab("All Rooms", createRoomsPanel());

        add(tabbedPane, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(41, 128, 185));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // Custom drawn logo
        JPanel logoPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Draw hotel building
                g2d.setColor(Color.WHITE);
                g2d.fillRect(10, 20, 60, 40);
                g2d.fillRect(20, 10, 40, 20);
                g2d.fillRect(30, 5, 20, 10);

                // Draw windows
                g2d.setColor(new Color(41, 128, 185));
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 2; j++) {
                        g2d.fillRect(15 + i * 15, 25 + j * 10, 8, 6);
                    }
                }

                // Draw door
                g2d.setColor(new Color(231, 76, 60));
                g2d.fillRect(35, 45, 10, 15);
            }
        };
        logoPanel.setPreferredSize(new Dimension(80, 70));
        logoPanel.setOpaque(false);
        headerPanel.add(logoPanel, BorderLayout.WEST);

        // Hotel name
        JPanel namePanel = new JPanel();
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.Y_AXIS));
        namePanel.setOpaque(false);

        JLabel hotelName = new JLabel("Grand Palace Hotel");
        hotelName.setFont(new Font("Serif", Font.BOLD, 32));
        hotelName.setForeground(Color.WHITE);
        hotelName.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel tagline = new JLabel("Where Luxury Meets Comfort");
        tagline.setFont(new Font("Arial", Font.ITALIC, 16));
        tagline.setForeground(new Color(236, 240, 241));
        tagline.setAlignmentX(Component.CENTER_ALIGNMENT);

        namePanel.add(Box.createVerticalGlue());
        namePanel.add(hotelName);
        namePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        namePanel.add(tagline);
        namePanel.add(Box.createVerticalGlue());

        headerPanel.add(namePanel, BorderLayout.CENTER);

        return headerPanel;
    }

    private JPanel createBookingPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Form panel
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // ALL INPUT FIELDS
        JTextField nameField = new JTextField(15);
        JTextField ageField = new JTextField(15);
        JTextField emailField = new JTextField(15);
        JTextField phoneField = new JTextField(15);

        String[] categories = {"Standard", "Deluxe", "Super Deluxe", "Suite", "Presidential Suite"};
        JComboBox<String> categoryCombo = new JComboBox<>(categories);

        JTextField roomField = new JTextField(15);
        JTextField checkInField = new JTextField("DD/MM/YYYY", 15);
        JTextField checkOutField = new JTextField("DD/MM/YYYY", 15);

        // Date picker functionality
        addDatePicker(checkInField);
        addDatePicker(checkOutField);

        JButton searchButton = new JButton("Search Available Rooms");
        JButton bookButton = new JButton("Confirm Booking");
        JTextArea resultArea = new JTextArea(6, 35);
        resultArea.setEditable(false);
        resultArea.setBorder(BorderFactory.createTitledBorder("Results"));

        // Style buttons
        searchButton.setBackground(new Color(52, 152, 219));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFont(new Font("Arial", Font.BOLD, 12));

        bookButton.setBackground(new Color(39, 174, 96));
        bookButton.setForeground(Color.WHITE);
        bookButton.setFont(new Font("Arial", Font.BOLD, 12));

        // Add all components with * (asterisk) for required fields
        gbc.anchor = GridBagConstraints.WEST;

        // NAME FIELD - ROW 0
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel nameLabel = new JLabel("Full Name*:");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(nameLabel, gbc);
        gbc.gridx = 1;
        panel.add(nameField, gbc);

        // AGE FIELD - ROW 1
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel ageLabel = new JLabel("Age*:");
        ageLabel.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(ageLabel, gbc);
        gbc.gridx = 1;
        panel.add(ageField, gbc);

        // EMAIL FIELD - ROW 2
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel emailLabel = new JLabel("Email*:");
        emailLabel.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(emailLabel, gbc);
        gbc.gridx = 1;
        panel.add(emailField, gbc);

        // PHONE FIELD - ROW 3
        gbc.gridx = 0; gbc.gridy = 3;
        JLabel phoneLabel = new JLabel("Phone Number*:");
        phoneLabel.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(phoneLabel, gbc);
        gbc.gridx = 1;
        panel.add(phoneField, gbc);

        // CATEGORY FIELD - ROW 4
        gbc.gridx = 0; gbc.gridy = 4;
        JLabel categoryLabel = new JLabel("Room Category*:");
        categoryLabel.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(categoryLabel, gbc);
        gbc.gridx = 1;
        panel.add(categoryCombo, gbc);

        // ROOM NUMBER FIELD - ROW 5
        gbc.gridx = 0; gbc.gridy = 5;
        JLabel roomLabel = new JLabel("Preferred Room Number*:");
        roomLabel.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(roomLabel, gbc);
        gbc.gridx = 1;
        panel.add(roomField, gbc);

        // CHECK-IN DATE - ROW 6
        gbc.gridx = 0; gbc.gridy = 6;
        JLabel checkinLabel = new JLabel("Check-in Date*:");
        checkinLabel.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(checkinLabel, gbc);
        gbc.gridx = 1;
        panel.add(checkInField, gbc);

        // CHECK-OUT DATE - ROW 7
        gbc.gridx = 0; gbc.gridy = 7;
        JLabel checkoutLabel = new JLabel("Check-out Date*:");
        checkoutLabel.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(checkoutLabel, gbc);
        gbc.gridx = 1;
        panel.add(checkOutField, gbc);

        // BUTTONS - ROW 8
        gbc.gridx = 0; gbc.gridy = 8;
        panel.add(searchButton, gbc);
        gbc.gridx = 1;
        panel.add(bookButton, gbc);

        // RESULTS AREA - ROW 9
        gbc.gridx = 0; gbc.gridy = 9; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.BOTH;
        panel.add(new JScrollPane(resultArea), gbc);

        mainPanel.add(panel, BorderLayout.CENTER);

        // SEARCH BUTTON ACTION
        searchButton.addActionListener(e -> {
            String category = (String) categoryCombo.getSelectedItem();
            List<Room> available = hotel.searchAvailableRooms(category);
            if (available.isEmpty()) {
                resultArea.setText("No rooms available in " + category + " category.\n\nPlease try a different category or check other dates.");
            } else {
                StringBuilder sb = new StringBuilder("Available " + category + " Rooms:\n");
                sb.append("=".repeat(50)).append("\n\n");
                for (Room r : available) {
                    sb.append("Room ").append(r.getRoomNumber())
                            .append(" - Rs.").append(String.format("%,d", (int)r.getPrice())).append(" per night\n");
                }
                sb.append("\nTip: Note down your preferred room number for booking!");
                resultArea.setText(sb.toString());
            }
        });

        // STRICT BOOKING VALIDATION
        bookButton.addActionListener(e -> {
            // Get all field values and trim whitespace
            String name = nameField.getText().trim();
            String age = ageField.getText().trim();
            String email = emailField.getText().trim();
            String phone = phoneField.getText().trim();
            String roomNumStr = roomField.getText().trim();
            String checkIn = checkInField.getText().trim();
            String checkOut = checkOutField.getText().trim();

            // STRICT VALIDATION - Check every single field
            StringBuilder missingFields = new StringBuilder();

            if (name.isEmpty()) {
                missingFields.append("• Full Name\n");
            }
            if (age.isEmpty()) {
                missingFields.append("• Age\n");
            }
            if (email.isEmpty()) {
                missingFields.append("• Email\n");
            }
            if (phone.isEmpty()) {
                missingFields.append("• Phone Number\n");
            }
            if (roomNumStr.isEmpty()) {
                missingFields.append("• Room Number\n");
            }
            if (checkIn.isEmpty() || checkIn.equals("DD/MM/YYYY")) {
                missingFields.append("• Check-in Date\n");
            }
            if (checkOut.isEmpty() || checkOut.equals("DD/MM/YYYY")) {
                missingFields.append("• Check-out Date\n");
            }

            // If any field is missing, show error and STOP
            if (missingFields.length() > 0) {
                JOptionPane.showMessageDialog(this,
                        "Please fill the following required fields:\n\n" + missingFields.toString(),
                        "Missing Required Information",
                        JOptionPane.ERROR_MESSAGE);
                return; // DO NOT PROCEED WITH BOOKING
            }

            // Additional validations
            // Email validation
            if (!email.contains("@") || !email.contains(".") || email.length() < 5) {
                JOptionPane.showMessageDialog(this, "Please enter a valid email address!", "Invalid Email", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Age validation
            try {
                int ageInt = Integer.parseInt(age);
                if (ageInt < 18 || ageInt > 100) {
                    JOptionPane.showMessageDialog(this, "Age must be between 18 and 100 years!", "Invalid Age", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid age (numbers only)!", "Invalid Age", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Phone validation
            if (phone.length() < 10) {
                JOptionPane.showMessageDialog(this, "Please enter a valid phone number (at least 10 digits)!", "Invalid Phone", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Room number validation
            try {
                int roomNum = Integer.parseInt(roomNumStr);
                if (roomNum < 100 || roomNum > 999) {
                    JOptionPane.showMessageDialog(this, "Room number should be between 100-999!", "Invalid Room Number", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // ALL VALIDATIONS PASSED - PROCEED WITH BOOKING
                Booking booking = hotel.bookRoom(roomNum, email, checkIn, checkOut);
                if (booking != null) {
                    resultArea.setText("🎉 BOOKING CONFIRMED! 🎉\n" +
                            "=".repeat(50) + "\n\n" +
                            "Guest Name: " + name + "\n" +
                            "Age: " + age + " years\n" +
                            "Email: " + email + "\n" +
                            "Phone: " + phone + "\n" +
                            "Booking ID: " + booking.getBookingId() + "\n" +
                            "Room: " + booking.getRoomNumber() + " (" + categoryCombo.getSelectedItem() + ")\n" +
                            "Check-in: " + booking.getCheckInDate() + "\n" +
                            "Check-out: " + booking.getCheckOutDate() + "\n" +
                            "Total Amount: Rs." + String.format("%,d", (int)booking.getTotalAmount()) + "\n\n" +
                            "Thank you for choosing Grand Palace Hotel!\n" +
                            "Please save your Booking ID for future reference.");

                    // Clear all fields after successful booking
                    clearAllFields(nameField, ageField, emailField, phoneField, roomField, checkInField, checkOutField);

                } else {
                    resultArea.setText("❌ BOOKING FAILED!\n\n" +
                            "Room " + roomNum + " is not available for the selected dates.\n\n" +
                            "Please:\n" +
                            "1. Search for available rooms first\n" +
                            "2. Choose a different room number\n" +
                            "3. Try different dates");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid room number (numbers only)!", "Invalid Room Number", JOptionPane.ERROR_MESSAGE);
            }
        });

        return mainPanel;
    }

    // CLEAR ALL FIELDS AFTER SUCCESSFUL BOOKING
    private void clearAllFields(JTextField... fields) {
        for (JTextField field : fields) {
            if (field.getText().equals("DD/MM/YYYY")) {
                continue; // Keep date placeholder
            }
            field.setText("");
        }
    }

    private void addDatePicker(JTextField dateField) {
        dateField.setForeground(Color.GRAY);

        dateField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (dateField.getText().equals("DD/MM/YYYY")) {
                    dateField.setText("");
                    dateField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (dateField.getText().trim().isEmpty()) {
                    dateField.setText("DD/MM/YYYY");
                    dateField.setForeground(Color.GRAY);
                }
            }
        });
    }

    private JPanel createViewBookingsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField emailField = new JTextField(20);
        JButton viewButton = new JButton("View My Bookings");
        viewButton.setBackground(new Color(52, 152, 219));
        viewButton.setForeground(Color.WHITE);
        viewButton.setFont(new Font("Arial", Font.BOLD, 12));

        JTextArea bookingsArea = new JTextArea(15, 40);
        bookingsArea.setEditable(false);
        bookingsArea.setFont(new Font("Monaco", Font.PLAIN, 12));

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("Enter Email*: "));
        topPanel.add(emailField);
        topPanel.add(viewButton);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(bookingsArea), BorderLayout.CENTER);

        viewButton.addActionListener(e -> {
            String email = emailField.getText().trim();
            if (email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter your email address!", "Email Required", JOptionPane.WARNING_MESSAGE);
                emailField.requestFocus();
                return;
            }

            if (!email.contains("@") || !email.contains(".") || email.length() < 5) {
                JOptionPane.showMessageDialog(this, "Please enter a valid email address!", "Invalid Email", JOptionPane.WARNING_MESSAGE);
                emailField.requestFocus();
                return;
            }

            List<Booking> bookings = hotel.getBookingsByCustomer(email);
            if (bookings.isEmpty()) {
                bookingsArea.setText("No bookings found for: " + email + "\n\n" +
                        "You haven't made any bookings yet.\n" +
                        "Visit the 'Book Room' tab to make your first reservation!");
            } else {
                StringBuilder sb = new StringBuilder("BOOKING HISTORY FOR: " + email.toUpperCase() + "\n");
                sb.append("=".repeat(60)).append("\n\n");

                for (int i = 0; i < bookings.size(); i++) {
                    Booking b = bookings.get(i);
                    sb.append("BOOKING #").append(i + 1).append("\n");
                    sb.append("Booking ID: ").append(b.getBookingId()).append("\n");
                    sb.append("Room Number: ").append(b.getRoomNumber()).append("\n");
                    sb.append("Check-in: ").append(b.getCheckInDate()).append("\n");
                    sb.append("Check-out: ").append(b.getCheckOutDate()).append("\n");
                    sb.append("Amount: Rs.").append(String.format("%,d", (int)b.getTotalAmount())).append("\n");
                    sb.append("Status: ").append(b.isActive() ? "ACTIVE" : "CANCELLED").append("\n");
                    sb.append("-".repeat(40)).append("\n\n");
                }
                bookingsArea.setText(sb.toString());
            }
        });

        return panel;
    }

    private JPanel createRoomsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        String[] columns = {"Room Number", "Category", "Price (Rs.)", "Features", "Availability"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        model.addRow(new Object[]{101, "Standard", "1,000", "Basic amenities, WiFi", "Available"});
        model.addRow(new Object[]{102, "Deluxe", "2,000", "Premium amenities, City view", "Available"});
        model.addRow(new Object[]{103, "Super Deluxe", "2,500", "Luxury amenities, Balcony", "Available"});
        model.addRow(new Object[]{201, "Suite", "3,500", "Separate living area, Premium view", "Available"});
        model.addRow(new Object[]{301, "Presidential Suite", "5,000", "Ultimate luxury, Butler service", "Available"});

        JTable table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(52, 152, 219));
        table.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(table);

        JLabel titleLabel = new JLabel("All Available Rooms", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HotelManagementUI());
    }
}
