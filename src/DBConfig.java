import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBConfig {
    private static final String CONFIG_FILE = "src/db.properties";
    private static Properties properties = new Properties();
    
    static {
        try {
            // First try to load from classpath
            InputStream inputStream = DBConfig.class.getClassLoader().getResourceAsStream("db.properties");
            
            // If not found in classpath, try file path
            if (inputStream == null) {
                inputStream = new FileInputStream(CONFIG_FILE);
            }
            
            properties.load(inputStream);
            inputStream.close();
        } catch (IOException ex) {
            System.err.println("Error loading database configuration: " + ex.getMessage());
            // Set default values using the correct property names
            properties.setProperty("db.url", "jdbc:mysql://localhost:3306/resume");
            properties.setProperty("db.user", "root");
            properties.setProperty("db.password", "1811");
        }
    }
    
    public static String getConnectionURL() {
        return properties.getProperty("db.url");
    }
    
    public static String getUsername() {
        return properties.getProperty("db.user");
    }
    
    public static String getPassword() {
        return properties.getProperty("db.password");
    }
}