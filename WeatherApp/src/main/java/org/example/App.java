package org.example;

import org.example.service.WeatherForecastService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Author - kaushi
 *
 */
public class  App
{
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        System.out.println("Welcome to US weather station. Please enter a ZipCode : - ");

        String zipCode;
        zipCode = reader.readLine();

        String weatherForecast = WeatherForecastService.getWeatherForecast(zipCode);
        System.out.println(weatherForecast);

        // Fetch the same forecast again, should be retrieved from cache
        weatherForecast = WeatherForecastService.getWeatherForecast(zipCode);
        System.out.println(weatherForecast);
    }
}
