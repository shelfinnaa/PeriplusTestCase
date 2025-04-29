package com.example.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverManager {

    private static WebDriver driver;

    // Start a chrome browser for automation task
    public static WebDriver getDriver() {
        if (driver == null) {

        /*
        Set the path to the ChromeDriver executable manually.
        ChromeDriver version used: 135.0.7049.114 (to match the Chrome browser version on my Mac)
        Downloaded from: https://googlechromelabs.github.io/chrome-for-testing/#stable
        Note: Manual setup is used because automatic WebDriverManager configuration did not work previously.
        */
        System.setProperty("webdriver.chrome.driver", "/Users/shelfinna/Downloads/chromedriver-mac-arm64/chromedriver");

        /*
        Set up Chrome options to make sure the browser and automation tool can work together smoothly. 
        Without this, the automated tests might not be able to open or control the browser properly.
        */
        ChromeOptions options = new ChromeOptions();
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
