import java.io.File;
import javax.swing.*;

public class FileBrowser extends JFrame {
    private String selectedFilePath;

    public FileBrowser() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select a Profile Picture");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            selectedFilePath = selectedFile.getAbsolutePath();
        }
        dispose(); // Close the file chooser window
    }

    public String getS() {
        return selectedFilePath;
    }
}