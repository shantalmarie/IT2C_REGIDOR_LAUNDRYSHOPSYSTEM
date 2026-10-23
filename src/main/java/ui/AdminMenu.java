package com.laundry.ui;

import com.laundry.dao.UserDao;
import com.laundry.model.User;
import java.util.List;
import java.util.Scanner;

public class AdminMenu {
    private final UserDao userDao;
    private final Scanner scanner;

    public AdminMenu(UserDao userDao, Scanner scanner) {
        this.userDao = userDao;
        this.scanner = scanner;
    }

    public void show() {
        while (true) {
            System.out.println("\n========================================");
            System.out.println("ADMINISTRATOR MENU");
            System.out.println("========================================");
            System.out.println("1. View All System Users");
            System.out.println("2. View Pending Registrations");
            System.out.println("3. Approve User Account");
            System.out.println("4. Add New User");
            System.out.println("5. Update User");
            System.out.println("6. Delete User");
            System.out.println("7. Logout");
            System.out.print("Choose an option: ");

            if (!scanner.hasNextInt()) {
                scanner.nextLine();
                System.out.println("Invalid option.");
                continue;
            }
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    viewAllSystemUsers();
                    break;
                case 2:
                    viewPendingRegistrations();
                    break;
                case 3:
                    approveUserAccount();
                    break;
                case 4:
                    addUserFlow();
                    break;
                case 5:
                    updateUserFlow();
                    break;
                case 6:
                    deleteUserFlow();
                    break;
                case 7:
                    System.out.println("-> Logged out successfully.");
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private void printUserTable(List<User> users) {
        String format = "%-5s | %-15s | %-20s | %-10s%n";
        System.out.println("-".repeat(60));
        System.out.printf(format, "ID", "Username", "Role", "Status");
        System.out.println("-".repeat(60));
        for (User u : users) {
            System.out.printf(format, u.userId(), u.username(), u.role(), u.status());
        }
        System.out.println("-".repeat(60));
    }

    private void viewAllSystemUsers() {
        List<User> users = userDao.getAllUsers();
        System.out.println("\n--- ALL SYSTEM USERS ---");
        if (users.isEmpty()) {
            System.out.println("No users found.");
            return;
        }
        printUserTable(users);
    }

    private void viewPendingRegistrations() {
        List<User> pendingUsers = userDao.getPendingUsers();
        System.out.println("\n--- PENDING REGISTRATIONS ---");
        if (pendingUsers.isEmpty()) {
            System.out.println("No pending registrations found.");
            return;
        }
        printUserTable(pendingUsers);
    }

    private void approveUserAccount() {
        List<User> pendingUsers = userDao.getPendingUsers();
        System.out.println("\n--- APPROVE USER ACCOUNT ---");
        if (pendingUsers.isEmpty()) {
            System.out.println("No pending accounts to approve.");
            return;
        }
        printUserTable(pendingUsers);

        System.out.print("Enter Target User ID to approve (or enter 0 to cancel): ");
        if (!scanner.hasNextInt()) {
            scanner.nextLine();
            System.out.println("-> Invalid input. Action cancelled.");
            return;
        }
        int targetId = scanner.nextInt();
        scanner.nextLine();

        if (targetId == 0) {
            System.out.println("-> Approval action cancelled.");
            return;
        }

        boolean exists = pendingUsers.stream().anyMatch(u -> u.userId() == targetId);
        if (!exists) {
            System.out.println("-> Error: User ID does not exist in the pending list.");
            return;
        }

        boolean success = userDao.approveUser(targetId);
        if (success) {
            System.out.println("-> Success: User account status updated to 'APPROVED'.");
        } else {
            System.out.println("-> Failed to approve user account.");
        }
    }

    private void addUserFlow() {
        System.out.println("\n--- ADD NEW USER ---");
        System.out.print("Enter full name: ");
        String fullName = scanner.nextLine();
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Select Role [1] Administrator | [2] Staff | [3] Laundry Attendant: ");

        if (!scanner.hasNextInt()) {
            scanner.nextLine();
            System.out.println("-> Invalid input. Action cancelled.");
            return;
        }
        int roleChoice = scanner.nextInt();
        scanner.nextLine();

        String role = switch (roleChoice) {
            case 1 -> "Administrator";
            case 2 -> "Staff";
            default -> "Laundry Attendant";
        };

        System.out.print("Select Status [1] APPROVED | [2] PENDING: ");
        if (!scanner.hasNextInt()) {
            scanner.nextLine();
            System.out.println("-> Invalid input. Action cancelled.");
            return;
        }
        int statusChoice = scanner.nextInt();
        scanner.nextLine();
        String status = (statusChoice == 1) ? "APPROVED" : "PENDING";

        boolean success = userDao.addUser(fullName, username, password, role, status);
        if (success) {
            System.out.println("-> Success: New user added successfully!");
        } else {
            System.out.println("-> Failed to add user. Username may already exist.");
        }
    }

    private void updateUserFlow() {
        List<User> users = userDao.getAllUsers();
        System.out.println("\n--- UPDATE USER ---");
        if (users.isEmpty()) {
            System.out.println("No users found to update.");
            return;
        }
        printUserTable(users);

        System.out.print("Enter User ID to update (or enter 0 to cancel): ");
        if (!scanner.hasNextInt()) {
            scanner.nextLine();
            System.out.println("-> Invalid input. Action cancelled.");
            return;
        }
        int userId = scanner.nextInt();
        scanner.nextLine();

        if (userId == 0) {
            System.out.println("-> Update action cancelled.");
            return;
        }

        boolean exists = users.stream().anyMatch(u -> u.userId() == userId);
        if (!exists) {
            System.out.println("-> Error: User ID does not exist.");
            return;
        }

        System.out.print("Enter new full name: ");
        String fullName = scanner.nextLine();
        System.out.print("Enter new username: ");
        String username = scanner.nextLine();
        System.out.print("Select Role [1] Administrator | [2] Staff | [3] Laundry Attendant: ");

        if (!scanner.hasNextInt()) {
            scanner.nextLine();
            System.out.println("-> Invalid input. Action cancelled.");
            return;
        }
        int roleChoice = scanner.nextInt();
        scanner.nextLine();

        String role = switch (roleChoice) {
            case 1 -> "Administrator";
            case 2 -> "Staff";
            default -> "Laundry Attendant";
        };

        System.out.print("Select Status [1] APPROVED | [2] PENDING: ");
        if (!scanner.hasNextInt()) {
            scanner.nextLine();
            System.out.println("-> Invalid input. Action cancelled.");
            return;
        }
        int statusChoice = scanner.nextInt();
        scanner.nextLine();
        String status = (statusChoice == 1) ? "APPROVED" : "PENDING";

        boolean success = userDao.updateUser(userId, fullName, username, role, status);
        if (success) {
            System.out.println("-> Success: User updated successfully!");
        } else {
            System.out.println("-> Failed to update user.");
        }
    }

    private void deleteUserFlow() {
        List<User> users = userDao.getAllUsers();
        System.out.println("\n--- DELETE USER ---");
        if (users.isEmpty()) {
            System.out.println("No users found to delete.");
            return;
        }
        printUserTable(users);

        System.out.print("Enter User ID to delete (or enter 0 to cancel): ");
        if (!scanner.hasNextInt()) {
            scanner.nextLine();
            System.out.println("-> Invalid input. Action cancelled.");
            return;
        }
        int userId = scanner.nextInt();
        scanner.nextLine();

        if (userId == 0) {
            System.out.println("-> Deletion action cancelled.");
            return;
        }

        boolean exists = users.stream().anyMatch(u -> u.userId() == userId);
        if (!exists) {
            System.out.println("-> Error: User ID does not exist.");
            return;
        }

        boolean success = userDao.deleteUser(userId);
        if (success) {
            System.out.println("-> Success: User deleted successfully!");
        } else {
            System.out.println("-> Failed to delete user.");
        }
    }
}