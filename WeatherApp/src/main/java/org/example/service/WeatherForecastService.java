package org.example.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.cache.SimpleCache;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.io.InputStreamReader;
import java.net.URL;

public class WeatherForecastService {

    private static SimpleCache<String, String> weatherCache = new SimpleCache<>(30 * 60 * 1000L); // 30 minutes cache duration
    private static final String API_KEY = "f718a7e3f8d7945af39875aaf6d6f102";
    static ObjectMapper objectMapper = new ObjectMapper();

    public static String getWeatherForecast(String zipCode) {
        // Check if weather data is cached
        String cachedForecast = weatherCache.get(zipCode);
        if (cachedForecast != null) {
            return "Weather Forecast (Cached) for Zip Code " + zipCode + ": " + cachedForecast;
        }

        // If not cached, fetch weather forecast from external API (mock implementation)
        String weatherForecast = fetchWeatherForecastFromExternalAPI(zipCode);

        // Cache the forecast
        weatherCache.put(zipCode, weatherForecast);

        return "Weather Forecast for Zip Code " + zipCode + ": " + weatherForecast;
    }

    private static String fetchWeatherForecastFromExternalAPI(String zipCode) {
        try {
            // Construct the URL for the OpenWeatherMap API
            String apiUrl = "https://api.openweathermap.org/data/2.5/weather?zip=" + zipCode + ",us"+"&appid=" + API_KEY;
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            StringBuilder response = getResponse(connection);

            // Parse the JSON response and extract relevant weather data
            return parseWeatherData(response.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return "Unable to fetch weather forecast for Zip Code: " + zipCode;
        }
    }

    private static StringBuilder getResponse(HttpURLConnection connection) {
        StringBuilder response = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    private static  String parseWeatherData(String jsonResponse) {
        try {
            JsonNode rootNode = objectMapper.readTree(jsonResponse);

            // Extract relevant weather information from the JSON response
            String cityName = rootNode.path("name").asText();
            double temperature = rootNode.path("main").path("temp").asDouble();
            double maxTemperature = rootNode.path("main").path("temp_max").asDouble();
            double minTemperature = rootNode.path("main").path("temp_min").asDouble();
            double feelsLike = rootNode.path("main").path("feels_like").asDouble();
            int humidity = rootNode.path("main").path("humidity").asInt();
            String weatherDescription = rootNode.path("weather").get(0).path("description").asText();

            // Construct the weather forecast string
            return "City: " + cityName + "\nTemperature: " + temperature + "°F" + "\nMax Temperature: " + maxTemperature +"°F" + "\nMin Temperature: " + minTemperature+ "°F\nFeels like: " + feelsLike + "°F\nHumidity: " + humidity + "%\nWeather Description: " + weatherDescription;
        } catch (IOException e) {
            e.printStackTrace();
            return "Error parsing weather data";
        }
    }
}
