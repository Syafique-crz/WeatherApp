
import com.github.cliftonlabs.json_simple.JsonObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Optional;


public class WeatherAppGui extends JFrame {

    //public JsonObject weatherData;

//    public WeatherAppGui(JsonObject weatherData) throws HeadlessException {
//        this.weatherData = weatherData;
//    }

//    public Optional<Object> getWeatherData() {
//        return Optional.ofNullable(weatherData);
//    }

//    public void setWeatherData(JsonObject weatherData) {
//        this.weatherData = weatherData;
//    }

    public WeatherAppGui() {

        super("Weather App v2"); //setting title for the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //set to exit program when closed
        setSize(450, 650); //set frame size
        setLocationRelativeTo(null); //set location to middle of the computer
        setLayout(null); //make layout manager manually set on middle of the frame
        setResizable(false); // set cant resize the frame

        addGuiComponents();
    }
    private void addGuiComponents() {

        //search text field
        JTextField searchTextField = new JTextField();
        searchTextField.setBounds(10, 13, 350, 45);
        searchTextField.setFont(new Font("Roboto", Font.PLAIN, 24));
        add(searchTextField);

        JLabel builtByLabel = new JLabel("Built by Syafique");
        builtByLabel.setBounds(10, 50, 200, 30); // Adjust position and size as needed
        builtByLabel.setForeground(Color.GRAY); // Optional: Set color to distinguish it visually
        add(builtByLabel);


        //weather condition image
        JLabel weatherConditionImage = new JLabel(loadImage("D:/Backend Projects/WeatherAppGUIv2/WeatherAppGUI/src/assets/cloudy.png"));
        weatherConditionImage.setBounds(0, 60, 450, 250);
        add(weatherConditionImage);

        //temperature label
        JLabel temperatureLabel = new JLabel("0°C");
        temperatureLabel.setBounds(-10, 280, 450, 30);
        temperatureLabel.setFont(new Font("Roboto", Font.BOLD, 40));
        temperatureLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(temperatureLabel);

        //weather condition description label
        JLabel weatherConditionDescriptionLabel = new JLabel("N/A");
        weatherConditionDescriptionLabel.setBounds(-10, 320, 450, 40);
        weatherConditionDescriptionLabel.setFont(new Font("Roboto", Font.PLAIN, 24));
        weatherConditionDescriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(weatherConditionDescriptionLabel);

        //humidity image
        JLabel humidityLabel = new JLabel(loadImage("D:/Backend Projects/WeatherAppGUIv2/WeatherAppGUI/src/assets/humidity.png"));
        humidityLabel.setBounds(20, 500, 80, 75);
        add(humidityLabel);

        //humidity label
        JLabel humidityValueLabel = new JLabel("<html><b>Humidity</b> 0 % </html>");
        humidityValueLabel.setBounds(100, 510, 100, 55);
        humidityValueLabel.setFont(new Font("Roboto", Font.PLAIN, 16));
        add(humidityValueLabel);

        //wind-speed image
        JLabel windSpeedLabel = new JLabel(loadImage("D:/Backend Projects/WeatherAppGUIv2/WeatherAppGUI/src/assets/windspeed.png"));
        windSpeedLabel.setBounds(230, 500, 80, 66);

        add(windSpeedLabel);

        //wind-speed label
        JLabel windSpeedValueLabel = new JLabel("<html><b>Wind Speed</b> 0 km/h </html>");
        windSpeedValueLabel.setBounds(320, 500, 100, 75);
        windSpeedValueLabel.setFont(new Font("Roboto", Font.PLAIN, 16));
        add(windSpeedValueLabel);

        //air-quality image
        JLabel airQualityLabel = new JLabel(loadImage("D:/Backend Projects/WeatherAppGUIv2/WeatherAppGUI/src/assets/airquality.png"));
        airQualityLabel.setBounds(20, 400, 80, 75);

        add(airQualityLabel);

        //air-quality label
        JLabel airQualityValueLabel = new JLabel("<html><b>Air Quality</b> N/A </html>");
        airQualityValueLabel.setBounds(100, 410, 100, 55);
        airQualityValueLabel.setFont(new Font("Roboto", Font.PLAIN, 16));
        add(airQualityValueLabel);

        //uv-index image
        JLabel uvIndexLabel = new JLabel(loadImage("D:/Backend Projects/WeatherAppGUIv2/WeatherAppGUI/src/assets/uvindex.png"));
        uvIndexLabel.setBounds(230, 400, 80, 75);

        add(uvIndexLabel);

        //uv-index label
        JLabel uvIndexValueLabel = new JLabel("<html><b>UV Index</b> N/A </html>");
        uvIndexValueLabel.setBounds(320, 410, 100, 55);
        uvIndexValueLabel.setFont(new Font("Roboto", Font.PLAIN, 16));
        add(uvIndexValueLabel);

        //search button
        JButton searchButton = new JButton(loadImage("D:/Backend Projects/WeatherAppGUIv2/WeatherAppGUI/src/assets/search.png"));

        //change cursor to a hand cursor when hovering over this button
        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchButton.setBounds(375, 13, 47, 45);


        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // get location from user
                String userInput = searchTextField.getText().trim();

                //validate input - remove whitespace to ensure non-empty text
                if (userInput.isEmpty()) {
                    JOptionPane.showMessageDialog(WeatherAppGui.this, "Could not retrieve weather data." +
                            " Please check the location.", "Error", JOptionPane.ERROR_MESSAGE);

                    return;
                }

                //retrieve weather data
                Optional<JsonObject> weatherData = (Optional<JsonObject>) WeatherApp.getWeatherData(userInput);
                if (weatherData.isEmpty()) {
                    JOptionPane.showMessageDialog(WeatherAppGui.this, "Could not retrieve weather data. Please check the location.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                //update gui components

                //assert weatherData != null;
                JsonObject data = weatherData.get(); // Safe extraction after validation.
                double latitude = WeatherApp.latitude; // Ensure WeatherApp.latitude is updated during weather fetch
                double longitude = WeatherApp.longitude;
                System.out.println("latitude: " + WeatherApp.latitude + " longitude: " + WeatherApp.longitude); //debug

                String weatherCondition = (String) data.get("weather_condition");
                System.out.println("Weather Condition: " + weatherCondition); // Debug
                JsonObject airQualityData = WeatherApp.getAirQualityData(latitude, longitude);
                System.out.println("Air Quality: " + airQualityData);
                JsonObject uvIndexData = WeatherApp.getUVIndexData(latitude, longitude);
                System.out.println("UV Index: " + uvIndexData);

                //weatherConditionImage.setIcon(loadImage("C:/Users/user/Downloads/WeatherAppGUI/WeatherAppGUI/src/assets/" + weatherCondition.toLowerCase() + ".png"));

                switch (weatherCondition) {
                    case "Clear":
                        weatherConditionImage.setIcon(loadImage("D:/Backend Projects/WeatherAppGUIv2/WeatherAppGUI/src/assets/clear.png"));
                        break;
                    case "Cloudy":
                        weatherConditionImage.setIcon(loadImage("D:/Backend Projects/WeatherAppGUIv2/WeatherAppGUI/src/assets/cloudy.png"));
                        break;
                    case "Rain":
                        weatherConditionImage.setIcon(loadImage("D:/Backend Projects/WeatherAppGUIv2/WeatherAppGUI/src/assets/rain.png"));
                        break;
                    case "Snow":
                        weatherConditionImage.setIcon(loadImage("D:/Backend Projects/WeatherAppGUIv2/WeatherAppGUI/src/assets/snow.png"));
                        break;

                }
                weatherConditionDescriptionLabel.setText(weatherCondition);
                humidityValueLabel.setText("<html><b>Humidity</b> " + data.get("humidity") + "%</html>");
                airQualityValueLabel.setText("<html><b>Air Quality</b><br> " + airQualityData.get("airquality") + "</html>");
                uvIndexValueLabel.setText("<html><b>UV Index</b><br>" + uvIndexData.get("uvindex") + "</html>");
                windSpeedValueLabel.setText("<html><b>Wind Speed</b> " + data.get("windspeed") + " km/h</html>");
//                // System.out.println("weatherData: " + weatherData);


                //update temperature text
                Object tempObj = data.get("temperature");
                if (tempObj instanceof Double temperature) {
                    temperatureLabel.setText(String.format("%.1f°C", temperature));

                    //update humidity
                    Object humidityObj = data.get("humidity");
                    if (humidityObj instanceof Double) {
                        double humidity = Math.round((Double) humidityObj);
                        System.out.println("Updated Humidity: " + humidity + "%"); // Debugging
                        humidityValueLabel.setText("<html><b>Humidity</b> " + humidity + "%</html>");

                        //update windspeed
                        Object windSpeedObj = data.get("windspeed");
                        if (windSpeedObj instanceof Double) {
                            double windspeed = (Double) windSpeedObj; // FIXED
                            System.out.println("Updated Wind Speed: " + windspeed + " km/h");
                            windSpeedValueLabel.setText("<html><b>Wind Speed</b> " + windspeed + " km/h</html>");

                            // Update Air Quality Label
                            Object airQualityObj = airQualityData.get("airquality");
                            if (airQualityObj instanceof Number) {
                                int airQualityIndex = ((Number) airQualityObj).intValue();
                                airQualityValueLabel.setText("<html><b>Air Quality</b><br> " + airQualityIndex + " </html>");

                                // update uv index label
                                Object uvIndexObj = uvIndexData.get("uvindex");
                                if (uvIndexObj instanceof Number) {
                                    double uvIndex = ((Number) uvIndexObj).doubleValue();
                                    uvIndexValueLabel.setText("<html><b>UV Index</b><br> " + uvIndex + "</html>");

                                }
                            }
                        }
                    }
                }
            }
        });

                add(searchButton);
    }

                //used to create image in our gui components
        private ImageIcon loadImage(String path){

              try {
                        BufferedImage image = ImageIO.read(new File(path));
                        return new ImageIcon(image);
              } catch (IOException e) {
                        System.err.println("error loading image" + e.getMessage());

                    }
                    return null;
                }
}
