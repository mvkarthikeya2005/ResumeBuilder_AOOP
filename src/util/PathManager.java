package util;

import java.io.File;

/**
 * Utility class to manage paths for the application
 */
public class PathManager {
    // Base directory is the parent directory of the application
    private static final String BASE_PATH = System.getProperty("user.dir");
    
    // Directory for storing created resumes
    private static final String CREATED_RESUMES_DIR = "created_resumes";
    
    /**
     * Get the path to the created_resumes directory
     * @return Full path to created_resumes directory
     */
    public static String getCreatedResumesPath() {
        return BASE_PATH + File.separator + CREATED_RESUMES_DIR;
    }
    
    /**
     * Get the base application directory
     * @return Base directory path
     */
    public static String getBasePath() {
        return BASE_PATH;
    }
    
    /**
     * Get a file path within the created_resumes directory
     * @param fileName Name of the file
     * @return Full path to the file in created_resumes directory
     */
    public static String getCreatedResumePath(String fileName) {
        return getCreatedResumesPath() + File.separator + fileName;
    }
}
