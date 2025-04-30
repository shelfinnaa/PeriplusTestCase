package com.example.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverManager {

    private static WebDriver driver;

    // Start a chrome browser for automation task
    public static WebDriver getDriver() {
        if (driver == null) {
            // Create the ChromeDriver
            driver = new ChromeDriver();
        }
        return driver;
    }

    // Close the browser if it is open. 
    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null; // reset
        }
    }
}
