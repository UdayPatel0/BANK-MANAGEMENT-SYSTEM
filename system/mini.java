package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;

public class mini extends JFrame implements ActionListener {
    String pin;
    JButton button;

    mini(String pin) {
        this.pin = pin;
        getContentPane().setBackground(new Color(255, 204, 204));  // ✅ Your original pink background
        setSize(400, 600);
        setLocation(20, 20);
        setLayout(null);

        // Title
        JLabel label2 = new JLabel("Statement");
        label2.setFont(new Font("System", Font.BOLD, 15));
        label2.setBounds(150, 20, 200, 20);
        add(label2);

        // Card number display
        JLabel label3 = new JLabel();
        label3.setBounds(20, 80, 350, 20);
        add(label3);

        // Transaction statements (inside scroll pane)
        JLabel label1 = new JLabel();
        label1.setVerticalAlignment(JLabel.TOP);
        label1.setOpaque(true);
        label1.setBackground(new Color(255, 204, 204));  // Match background

        JScrollPane scrollPane = new JScrollPane(label1);
        scrollPane.setBounds(20, 140, 350, 200);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        add(scrollPane);

        // Balance label
        JLabel label4 = new JLabel();
        label4.setBounds(20, 400, 300, 20);
        add(label4);

        // Fetch card number
        try {
            Con c = new Con();
            ResultSet rs = c.statement.executeQuery("SELECT * FROM login WHERE pin = '" + pin + "'");
            while (rs.next()) {
                String card = rs.getString("card_number");
                label3.setText("Card Number: " + card.substring(0, 4) + "XXXXXXXX" + card.substring(12));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Fetch last 6 transactions
        try {
            int balance = 0;
            Con c = new Con();
            ResultSet rs = c.statement.executeQuery("SELECT * FROM bank WHERE pin = '" + pin + "'");

            ArrayList<String> transactions = new ArrayList<>();
            while (rs.next()) {
                String date = rs.getString("date");
                String type = rs.getString("type");
                String amount = rs.getString("amount");

                String entry = date + "&nbsp;&nbsp;&nbsp;&nbsp;" + type + "&nbsp;&nbsp;&nbsp;&nbsp;" + amount + "<br><br>";
                transactions.add(entry);

                // Calculate balance
                if (type.equals("Deposit")) {
                    balance += Integer.parseInt(amount);
                } else {
                    balance -= Integer.parseInt(amount);
                }
            }

            // Get last 6 transactions only
            int total = transactions.size();
            int start = Math.max(total - 6, 0);
            StringBuilder sb = new StringBuilder("<html>");
            for (int i = start; i < total; i++) {
                sb.append(transactions.get(i));
            }
            sb.append("</html>");
            label1.setText(sb.toString());

            label4.setText("Your Total Balance is Rs " + balance);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Exit button
        button = new JButton("Exit");
        button.setBounds(20, 500, 100, 25);
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.addActionListener(this);
        add(button);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        setVisible(false);
    }

    public static void main(String[] args) {
        new mini("");
    }
}