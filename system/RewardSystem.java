package bank.management.system;

import java.sql.*;

public class RewardSystem {

    // Method to add reward points
    public static void addRewardPoints(String pin, int points) {
        try {
            Con c = new Con();
            String query = "INSERT INTO rewards (pin, points) VALUES (?, ?) ON DUPLICATE KEY UPDATE points = points + ?";
            PreparedStatement stmt = c.statement.getConnection().prepareStatement(query);
            stmt.setString(1, pin);
            stmt.setInt(2, points);
            stmt.setInt(3, points);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to get the total reward points of a user
    public static int getRewardPoints(String pin) {
        int points = 0;
        try {
            Con c = new Con();
            String query = "SELECT points FROM rewards WHERE pin = ?";
            PreparedStatement stmt = c.statement.getConnection().prepareStatement(query);
            stmt.setString(1, pin);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                points = rs.getInt("points");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return points;
    }


    public static void updateRewardPoints(String pin, int newPoints) {
        try {
            Con c = new Con();
            PreparedStatement stmt = c.statement.getConnection().prepareStatement(
                    "UPDATE rewards SET points = ? WHERE pin = ?"
            );
            stmt.setInt(1, newPoints);
            stmt.setString(2, pin);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                System.out.println("Error: No rows updated in rewards table!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to redeem reward points for cash
    public static boolean redeemPointsForCash(String pin, int redeemAmount) {
        System.out.println("Redeem Request: PIN=" + pin + ", Redeem Amount=" + redeemAmount);

        int currentPoints = getRewardPoints(pin);
        System.out.println("Current Reward Points: " + currentPoints);

        if (redeemAmount > currentPoints) {
            System.out.println("Not enough points to redeem!");
            return false;
        }

        int updatedPoints = currentPoints - redeemAmount;
        System.out.println("Updated Reward Points after redemption: " + updatedPoints);

        try {
            Con c = new Con();

            // Update reward points
            PreparedStatement updateStmt = c.statement.getConnection().prepareStatement(
                    "UPDATE rewards SET points = ? WHERE pin = ?"
            );
            updateStmt.setInt(1, updatedPoints);
            updateStmt.setString(2, pin);
            int rowsUpdated = updateStmt.executeUpdate();
            System.out.println("Rows affected in rewards table: " + rowsUpdated);

            if (rowsUpdated == 0) {
                System.out.println("Error: Reward points not updated!");
                return false;
            }

            // Update bank balance
            PreparedStatement balanceStmt = c.statement.getConnection().prepareStatement(
                    "UPDATE bank SET balance = balance + ? WHERE pin = ?"
            );
            balanceStmt.setInt(1, redeemAmount);
            balanceStmt.setString(2, pin);
            int balanceUpdated = balanceStmt.executeUpdate();
            System.out.println("Rows affected in bank table: " + balanceUpdated);

            if (balanceUpdated == 0) {
                System.out.println("Error: Balance not updated!");
                return false;
            }

            System.out.println("Redemption successful!");
            return true; // Successfully redeemed

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}