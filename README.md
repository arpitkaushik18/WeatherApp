# Weather Forecast Service with Caching

This Java application provides weather forecast information for a given zip code. It includes a caching mechanism to improve performance by storing previously fetched weather data.

## Features

- Fetches weather forecast for a given zip code
- Caches the forecast for subsequent requests to the same zip code
- Supports caching using a simple cache implementation
- Example usage included in the Main class

## Installation

1. Clone the repository:


2. Import the project into your favorite Java IDE.

3. Set up the necessary dependencies:

   - Jackson library for JSON parsing
   - Ehcache library for caching (optional)

4. Replace `"your_api_key"` in `WeatherForecastService.java` with your actual OpenWeatherMap API key.

## Usage

1. Create an instance of `WeatherForecastService`.

2. Use the `getWeatherForecast` method of the `WeatherForecastService` class to fetch weather forecast data for a given zip code.

3. Weather forecast data will be fetched from the external API and cached for subsequent requests to the same zip code.

4. Subsequent requests for the same zip code will retrieve the cached data, improving performance.

## Example

```java
public class Main {
    public static void main(String[] args) {
        // Example usage
        String zipCode = "12345";
        WeatherForecastService weatherService = new WeatherForecastService();

        Weather weatherForecast = weatherService.getWeatherForecast(zipCode);
        System.out.println("Weather Forecast for Zip Code " + zipCode + ": " + weatherForecast);

        // Fetch the same forecast again, should be retrieved from cache
        weatherForecast = weatherService.getWeatherForecast(zipCode);
        System.out.println("Weather Forecast for Zip Code " + zipCode + " (from cache): " + weatherForecast);
    }
}
