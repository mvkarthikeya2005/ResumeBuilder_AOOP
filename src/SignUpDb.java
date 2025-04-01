import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import javax.swing.JOptionPane;

public class SignUpDb {
    public void database(String fname, String username, String email, String phonenum, String password) {
        Connection conn = null;
        Statement stmt = null;
        try {
            // Load the db.properties file from the classpath
            InputStream is = getClass().getResourceAsStream("/db.properties");
            if (is == null) {
                System.err.println("Error loading database configuration: db.properties not found.");
                return;
            }
            Properties props = new Properties();
            props.load(is);
            
            // Register the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Retrieve connection details from properties file
            String url = props.getProperty("db.url");
            String dbUser = props.getProperty("db.user");
            String dbPassword = props.getProperty("db.password");
            
            // Establish the connection
            conn = DriverManager.getConnection(url, dbUser, dbPassword);
            stmt = conn.createStatement();
            
            // Validate and convert phone number
            long phonenumber;
            try {
                phonenumber = Long.parseLong(phonenum);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid phone number. Please enter only digits.");
                return;
            }
            
            // Build the SQL insert statement
            String insert = "INSERT INTO signup(fullname, username, email, phone, password) " +
                            "VALUES('" + fname + "','" + username + "','" + email + "','" + phonenumber + "','" + password + "')";
            int count = stmt.executeUpdate(insert);
            
            System.out.println("Rows affected: " + count);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close resources
            if (stmt != null) {
                try { stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
            if (conn != null) {
                try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }
}
