package com.laundry.ui;

import com.laundry.dao.UserDao;
import com.laundry.model.User;
import java.util.Scanner;

public class Authentication {
    private final UserDao userDao;
    private final Scanner scanner;

    public Authentication(UserDao userDao, Scanner scanner) {
        this.userDao = userDao;
        this.scanner = scanner;
    }

    public void start() {
        while (true) {
            System.out.println("========================================");
            System.out.println("LAUNDRY SHOP MANAGEMENT SYSTEM");
            System.out.println("========================================");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            if (!scanner.hasNextInt()) {
                scanner.nextLine();
                continue;
            }
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    loginFlow();
                    break;
                case 2:
                    registerFlow();
                    break;
                case 3:
                    System.out.println("Exiting application...");
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private void registerFlow() {
        System.out.print("Enter full name: ");
        String fullName = scanner.nextLine();
        System.out.print("Enter new username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Select Role [1] Administrator | [2] Staff | [3] Laundry Attendant: ");

        int roleChoice = scanner.nextInt();
        scanner.nextLine();

        String role = switch (roleChoice) {
            case 1 -> "Administrator";
            case 2 -> "Staff";
            default -> "Laundry Attendant";
        };

        boolean success = userDao.registerUser(fullName, username, password, role);
        if (success) {
            System.out.println("-> Successful Registration, Waiting for approval!");
        } else {
            System.out.println("-> Registration Failed, Username may already exist.");
        }
    }

    private void loginFlow() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = userDao.login(username, password);
        if (user != null) {
            System.out.println("-> Login Successful! Welcome, " + user.fullName() + " (" + user.role() + ")");
            System.out.println("-> Account Status: " + user.status() + " (Waiting for approval)");

            if ("PENDING".equalsIgnoreCase(user.status())) {
                return;
            }

            routeUserRole(user);
        } else {
            System.out.println("-> Invalid username or password.");
        }
    }

    private void routeUserRole(User user) {
        String role = user.role();
        if ("Administrator".equalsIgnoreCase(role)) {
            adminDashboard();
        } else if ("Staff".equalsIgnoreCase(role)) {
            staffDashboard();
        } else {
            attendantDashboard();
        }
    }

    private void adminDashboard() {
        while (true) {
            System.out.println("\n--- ADMINISTRATOR DASHBOARD ---");
            System.out.println("1. Logout");
            System.out.print("Choose an option: ");
            int opt = scanner.nextInt();
            scanner.nextLine();
            if (opt == 1) {
                System.out.println("-> Logged out successfully.");
                break;
            } else {
                System.out.println("Invalid option.");
            }
        }
    }

    private void staffDashboard() {
        while (true) {
            System.out.println("\n--- STAFF DASHBOARD ---");
            System.out.println("1. Logout");
            System.out.print("Choose an option: ");
            int opt = scanner.nextInt();
            scanner.nextLine();
            if (opt == 1) {
                System.out.println("-> Logged out successfully.");
                break;
            } else {
                System.out.println("Invalid option.");
            }
        }
    }

    private void attendantDashboard() {
        while (true) {
            System.out.println("\n--- LAUNDRY ATTENDANT DASHBOARD ---");
            System.out.println("1. Logout");
            System.out.print("Choose an option: ");
            int opt = scanner.nextInt();
            scanner.nextLine();
            if (opt == 1) {
                System.out.println("-> Logged out successfully.");
                break;
            } else {
                System.out.println("Invalid option.");
            }
        }
    }
}