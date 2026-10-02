import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection() throws Exception {

       String url = "jdbc:mysql://127.0.0.1:3306/skillgap_db";
String username = "root";
String password = System.getenv("DB_PASSWORD");

        return DriverManager.getConnection(url, username, password);
    }

    public static void main(String[] args) {

        try {
            Connection connection = getConnection();

            System.out.println("Database connected successfully!");

            connection.close();

        } catch (Exception e) {
            System.out.println("Database connection failed!");
            e.printStackTrace();
        }
    }
}