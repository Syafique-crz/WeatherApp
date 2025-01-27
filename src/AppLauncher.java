import javax.swing.*;
import java.sql.Connection;

public class AppLauncher {

    public static void main(String[] args) {

        // Launch the Swing GUI on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            try {
                WeatherAppGui appGui = new WeatherAppGui();
                appGui.setVisible(true);
            } catch (Exception e) {
                System.err.println("Error launching GUI: " + e.getMessage());
            }
        });
    }
}
