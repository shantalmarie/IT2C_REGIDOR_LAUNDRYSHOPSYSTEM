package com.laundry;

import com.laundry.dao.DatabaseInitializer;
import com.laundry.dao.UserDao;
import com.laundry.ui.Authentication;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 1. Initialize Database Tables
        DatabaseInitializer.initializeDatabase();

        // 2. Instantiate DAOs and Shared Utilities
        UserDao userDao = new UserDao();
        Scanner scanner = new Scanner(System.in);

        // 3. Delegate execution to Authentication
        Authentication auth = new Authentication(userDao, scanner);
        auth.start();
    }
}