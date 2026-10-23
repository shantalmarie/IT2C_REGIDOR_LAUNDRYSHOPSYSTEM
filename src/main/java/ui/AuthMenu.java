package ui;

import dao.UserDao;
import model.User;
import java.util.Scanner;

public class AuthMenu {
    public static void show() {
        Scanner scanner = new Scanner(System.in);
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
                    loginFlow(scanner);
                    break;
                case 2:
                    registerFlow(scanner);
                    break;
                case 3:
                    System.out.println("Exiting application...");
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void registerFlow(Scanner scanner) {
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

        boolean success = UserDao.registerUser(fullName, username, password, role);
        if (success) {
            System.out.println("-> Successful Registration, Waiting for approval!");
        } else {
            System.out.println("-> Registration Failed, Username may already exist.");
        }
    }

    private static void loginFlow(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = UserDao.login(username, password);
        if (user != null) {
            if ("PENDING".equalsIgnoreCase(user.status())) {
                System.out.println("-> Login Failed: Account is still PENDING approval.");
                return;
            }
            System.out.println("-> Login Successful! Welcome, " + user.fullName() + " (" + user.role() + ")");

            while (true) {
                System.out.println("\n--- DASHBOARD ---");
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
        } else {
            System.out.println("-> Invalid username or password.");
        }
    }
}