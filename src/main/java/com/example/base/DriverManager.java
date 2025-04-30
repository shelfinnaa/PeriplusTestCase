package com.example.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverManager {

    private static WebDriver driver;

    // Start a chrome browser for automation task
    public static WebDriver getDriver() {
        if (driver == null) {

        ChromeOptions options = new ChromeOptions();

        /*
        Set up Chrome options to make sure the browser and automation tool can work together smoothly. 
        Without this, the automated tests might not be able to open or control the browser properly.
        */
        options.addArguments("--remote-allow-origins=*"); 
        
        // Create the ChromeDriver 
        driver = new ChromeDriver(options);
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
