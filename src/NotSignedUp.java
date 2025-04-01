import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class NotSignedUp {

    public boolean database(String username, String password) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // Load database properties from db.properties file
            Properties prop = new Properties();
            InputStream input = getClass().getClassLoader().getResourceAsStream("db.properties");
            if (input == null) {
                System.err.println("Unable to find db.properties");
                return false;
            }
            prop.load(input);
            String url = prop.getProperty("db.url");
            String dbUser = prop.getProperty("db.user");
            String dbPassword = prop.getProperty("db.password");

            // Debug prints
            System.out.println("Loaded URL: " + url);
            System.out.println("Loaded User: " + dbUser);

            if (url == null || url.isEmpty()) {
                System.err.println("Database URL is null or empty.");
                return false;
            }
            
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, dbUser, dbPassword);

            // Update query to use the 'signup' table
            String sql = "SELECT * FROM signup WHERE username = ? AND password = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);  // For production, consider hashing passwords
            rs = ps.executeQuery();

            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            // Clean up database resources
            try { if (rs != null) rs.close(); } catch (Exception e) { }
            try { if (ps != null) ps.close(); } catch (Exception e) { }
            try { if (conn != null) conn.close(); } catch (Exception e) { }
        }
    }
}
