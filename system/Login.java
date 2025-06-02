package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class Login extends JFrame implements ActionListener {
    JLabel label1, label2, label3;
    JTextField textField2;
    JPasswordField passwordField3;
    JButton button1, button2, button3;
    JLabel background;

    Login() {
        super("Bank Management System");

        // Get screen size
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();

        // Set frame properties
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setLayout(null);

        // Load and set background image
        ImageIcon bgIcon = new ImageIcon(ClassLoader.getSystemResource("icon/2940215.png"));
        Image bgImage = bgIcon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
        ImageIcon scaledBgIcon = new ImageIcon(bgImage);
        background = new JLabel(scaledBgIcon);
        background.setBounds(0, 0, width, height);
        add(background);

        // Create custom Close ("X") button
        JButton closeButton = new JButton("X");
        closeButton.setFont(new Font("Arial", Font.BOLD, 16));
        closeButton.setForeground(Color.WHITE);
        closeButton.setBackground(Color.RED);
        closeButton.setFocusPainted(false);
        closeButton.setBounds(width - 60, 10, 50, 30); // Top-right corner
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Exit application
            }
        });
        background.add(closeButton);

        // Load logo
        ImageIcon logoIcon = new ImageIcon(ClassLoader.getSystemResource("icon/bank.png"));
        Image logoImage = logoIcon.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        ImageIcon logo = new ImageIcon(logoImage);
        JLabel logoLabel = new JLabel(logo);
        logoLabel.setBounds(width/2 - 50, 20, 100, 100);
        background.add(logoLabel);

        // Load card icon
        ImageIcon cardIcon = new ImageIcon(ClassLoader.getSystemResource("icon/card.png"));
        Image cardImage = cardIcon.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        ImageIcon card = new ImageIcon(cardImage);
        JLabel cardLabel = new JLabel(card);
        cardLabel.setBounds(width/2 - 50, height/2 + 150, 100, 100);
        background.add(cardLabel);

        // Labels
        label1 = new JLabel("WELCOME TO ATM");
        label1.setForeground(Color.WHITE);
        label1.setFont(new Font("AvantGarde", Font.BOLD, 38));
        label1.setBounds(width/2 - 250, height/4, 500, 40);
        background.add(label1);

        label2 = new JLabel("Card No:");
        label2.setFont(new Font("Raleway", Font.BOLD, 28));
        label2.setForeground(Color.WHITE);
        label2.setBounds(width/2 - 250, height/4 + 80, 200, 30);
        background.add(label2);

        textField2 = new JTextField(15);
        textField2.setFont(new Font("Arial", Font.BOLD, 14));
        textField2.setBounds(width/2 - 50, height/4 + 80, 300, 30);
        background.add(textField2);

        label3 = new JLabel("PIN:");
        label3.setFont(new Font("Raleway", Font.BOLD, 28));
        label3.setForeground(Color.WHITE);
        label3.setBounds(width/2 - 250, height/4 + 140, 200, 30);
        background.add(label3);

        passwordField3 = new JPasswordField(15);
        passwordField3.setFont(new Font("Arial", Font.BOLD, 14));
        passwordField3.setBounds(width/2 - 50, height/4 + 140, 300, 30);
        background.add(passwordField3);

        // Buttons
        button1 = new JButton("SIGN IN");
        button1.setFont(new Font("Arial", Font.BOLD, 14));
        button1.setForeground(Color.WHITE);
        button1.setBackground(Color.BLUE);
        button1.setBounds(width/2 - 150, height/4 + 200, 120, 30);
        button1.addActionListener(this);
        background.add(button1);

        button2 = new JButton("CLEAR");
        button2.setFont(new Font("Arial", Font.BOLD, 14));
        button2.setForeground(Color.WHITE);
        button2.setBackground(Color.BLUE);
        button2.setBounds(width/2 + 30, height/4 + 200, 120, 30);
        button2.addActionListener(this);
        background.add(button2);

        button3 = new JButton("SIGN UP");
        button3.setFont(new Font("Arial", Font.BOLD, 14));
        button3.setForeground(Color.WHITE);
        button3.setBackground(Color.RED);
        button3.setBounds(width/2 - 150, height/4 + 250, 300, 30);
        button3.addActionListener(this);
        background.add(button3);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == button1) {
                Con c = new Con();
                String cardno = textField2.getText();
                String pin = passwordField3.getText();
                String q = "select * from login where card_number = '" + cardno + "' and pin = '" + pin + "'";
                ResultSet resultSet = c.statement.executeQuery(q);
                if (resultSet.next()) {
                    setVisible(false);
                    new main_Class(pin);
                } else {
                    JOptionPane.showMessageDialog(null, "Incorrect Card Number or PIN");
                }
            } else if (e.getSource() == button2) {
                textField2.setText("");
                passwordField3.setText("");
            } else if (e.getSource() == button3) {
                new Signup();
                setVisible(false);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}