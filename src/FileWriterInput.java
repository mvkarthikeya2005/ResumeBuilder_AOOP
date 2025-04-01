import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileWriterInput {
    private static final String RESUME_DIRECTORY = "created_resumes";
    
    public void add(String s, String s1, String s2, String s3, String s4, String s5, String s6, String s7, String s8, String s9,
                    String s10, String s11, String s12, String s13, String s14, String s15, String s16, String s17, String s18,
                    String s19, String s20, String s21) {
        
        // Ensure the directory exists
        createResumeDirectory();
        
        try {
            // Create file in the created_resumes directory with name based on first and last name
            String fileName = s + s1 + ".txt";
            File file = new File(RESUME_DIRECTORY, fileName);
            
            FileWriter fw = new FileWriter(file);
            
            fw.write("RESUME\n\n");
            fw.write("PERSONAL INFORMATION\n");
            fw.write("-------------------\n");
            fw.write("Name: " + s + " " + s1 + "\n");
            fw.write("Address: " + s2 + ", " + s3 + "\n");
            fw.write("Pincode: " + s4 + "\n");
            fw.write("Nationality: " + s5 + "\n");
            fw.write("Date of Birth: " + s6 + "\n");
            fw.write("Phone: " + s7 + "\n");
            fw.write("Email: " + s8 + "\n\n");
            
            fw.write("SKILLS\n");
            fw.write("------\n");
            if (!s10.trim().isEmpty()) {
                fw.write("- " + s10 + ": " + s9 + "\n");
            }
            if (!s11.trim().isEmpty()) {
                fw.write("- " + s11 + ": " + s13 + "\n");
            }
            if (!s12.trim().isEmpty()) {
                fw.write("- " + s12 + ": " + s14 + "\n");
            }
            fw.write("\n");
            
            fw.write("EDUCATION\n");
            fw.write("---------\n");
            if (!s19.trim().isEmpty()) {
                fw.write("College/University: " + s19 + "\n");
            }
            if (!s20.trim().isEmpty()) {
                fw.write("School: " + s20 + "\n");
            }
            if (!s21.trim().isEmpty()) {
                fw.write("Other Qualifications: " + s21 + "\n");
            }
            fw.write("\n");
            
            fw.write("WORK EXPERIENCE\n");
            fw.write("---------------\n");
            if (!s15.trim().isEmpty()) {
                fw.write("Company: " + s15 + "\n");
                if (!s16.trim().isEmpty()) {
                    fw.write("Designation: " + s16 + "\n");
                }
                fw.write("\n");
            }
            if (!s18.trim().isEmpty()) {
                fw.write("Company: " + s18 + "\n");
                if (!s17.trim().isEmpty()) {
                    fw.write("Designation: " + s17 + "\n");
                }
            }
            
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Create the directory for storing resumes if it doesn't exist
     */
    private void createResumeDirectory() {
        Path path = Paths.get(RESUME_DIRECTORY);
        if (!Files.exists(path)) {
            try {
                Files.createDirectory(path);
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Could not create directory for resumes: " + e.getMessage());
            }
        }
    }
    
    /**
     * Get the path to the resume directory
     * @return The absolute path to the resume directory
     */
    public static String getResumeDirectoryPath() {
        Path path = Paths.get(RESUME_DIRECTORY).toAbsolutePath();
        return path.toString();
    }
}