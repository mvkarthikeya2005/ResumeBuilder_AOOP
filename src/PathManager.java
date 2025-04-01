import java.io.File;

/**
 * Utility class to manage application paths
 */
public class PathManager {
    
    // Path to the directory where created resumes are stored
    private static final String CREATED_RESUMES_DIR = "created_resumes";
    
    /**
     * Returns the path to the directory where created resumes are stored
     * @return the path to the created_resumes directory
     */
    public static String getCreatedResumesPath() {
        // Create the directory if it doesn't exist
        File directory = new File(CREATED_RESUMES_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        return directory.getAbsolutePath();
    }
}
