package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class main_Class extends JFrame implements ActionListener {
    JButton b1, b2, b3, b4, b5, b6, b7, b8;
    String pin;

    main_Class(String pin) {
        this.pin = pin;

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/atm2.png"));
        Image i2 = i1.getImage().getScaledInstance(1550, 830, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l3 = new JLabel(i3);
        l3.setBounds(0, 0, 1550, 830);
        add(l3);

        JLabel label = new JLabel("Please Select Your Transaction");
        label.setBounds(430, 180, 700, 35);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("System", Font.BOLD, 28));
        l3.add(label);

        int rewardPoints = RewardSystem.getRewardPoints(pin);
        JLabel rewardLabel = new JLabel("Your Reward Points: " + rewardPoints);
        rewardLabel.setForeground(Color.WHITE);
        rewardLabel.setFont(new Font("System", Font.BOLD, 16));
        rewardLabel.setBounds(460, 230, 300, 35); // Adjust Y position to fit well
        l3.add(rewardLabel);

        b1 = new JButton("DEPOSIT");
        b1.setForeground(Color.WHITE);
        b1.setBackground(new Color(65, 125, 128));
        b1.setBounds(410, 274, 150, 35);
        b1.addActionListener(this);
        l3.add(b1);

        b2 = new JButton("CASH WITHDRAWL");
        b2.setForeground(Color.WHITE);
        b2.setBackground(new Color(65, 125, 128));
        b2.setBounds(700, 274, 150, 35);
        b2.addActionListener(this);
        l3.add(b2);

        b3 = new JButton("FAST CASH");
        b3.setForeground(Color.WHITE);
        b3.setBackground(new Color(65, 125, 128));
        b3.setBounds(410, 318, 150, 35);
        b3.addActionListener(this);
        l3.add(b3);

        b4 = new JButton("MINI STATEMENT");
        b4.setForeground(Color.WHITE);
        b4.setBackground(new Color(65, 125, 128));
        b4.setBounds(700, 318, 150, 35);
        b4.addActionListener(this);
        l3.add(b4);

        b5 = new JButton("PIN CHANGE");
        b5.setForeground(Color.WHITE);
        b5.setBackground(new Color(65, 125, 128));
        b5.setBounds(410, 362, 150, 35);
        b5.addActionListener(this);
        l3.add(b5);

        b6 = new JButton("BALANCE ENQUIRY");
        b6.setForeground(Color.WHITE);
        b6.setBackground(new Color(65, 125, 128));
        b6.setBounds(700, 362, 150, 35);
        b6.addActionListener(this);
        l3.add(b6);

        b7 = new JButton("EXIT");
        b7.setForeground(Color.WHITE);
        b7.setBackground(new Color(65, 125, 128));
        b7.setBounds(700, 406, 150, 35);
        b7.addActionListener(this);
        l3.add(b7);

        b8 = new JButton("REDEEM REWARDS");
        b8.setForeground(Color.WHITE);
        b8.setBackground(new Color(65, 125, 128));
        b8.setBounds(700, 450, 200, 35);
        b8.addActionListener(this);
        l3.add(b8);

        setLayout(null);
        setSize(1550, 1080);
        setLocation(0, 0);
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) {
            new Deposit(pin);
            setVisible(false);
        } else if (e.getSource() == b2) {
            new Withdrawl(pin);
            setVisible(false);
        } else if (e.getSource() == b3) {
            new FastCash(pin);
            setVisible(false);
        } else if (e.getSource() == b4) {
            new mini(pin);
        } else if (e.getSource() == b5) {
            new Pin(pin);
            setVisible(false);
        } else if (e.getSource() == b6) {
            new BalanceEnquriy(pin);
            setVisible(false);
        } else if (e.getSource() == b7) {
            System.exit(0);
        } else if (e.getSource() == b8) { // Redeem rewards button logic
            int points = RewardSystem.getRewardPoints(pin);
            if (points < 100) { // Set minimum points required
                JOptionPane.showMessageDialog(null, "You need at least 100 reward points to redeem.");
                return;
            }

            int redeemAmount = Integer.parseInt(JOptionPane.showInputDialog("Enter points to redeem (1 point = ₹1):"));
            if (redeemAmount > points) {
                JOptionPane.showMessageDialog(null, "Insufficient reward points!");
                return;
            }

            boolean success = RewardSystem.redeemPointsForCash(pin, redeemAmount);
            if (success) {
                JOptionPane.showMessageDialog(null, "Successfully redeemed " + redeemAmount + " points! ₹" + redeemAmount + " added to your balance.");
            } else {
                JOptionPane.showMessageDialog(null, "Successfully redeemed " + redeemAmount + " points! " + "you will get a discount coupon worth ₹" + redeemAmount+ " soon.");
            }
        }
    }
}