import dao.DatabaseConnection;
import ui.AuthMenu;

public class Main {
    public static void main(String[] args) {
        DatabaseConnection.initializeDatabase();
        AuthMenu.show();
    }
}