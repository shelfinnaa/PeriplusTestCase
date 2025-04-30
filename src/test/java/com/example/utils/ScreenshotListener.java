package com.example.utils;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.commons.io.FileUtils;
import com.example.base.DriverManager;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class ScreenshotListener implements ITestListener {

    // Method to capture screenshots 
    private void captureScreenshot(String status, ITestResult result) {
        WebDriver driver = DriverManager.getDriver();
        if (driver != null) {
            try {
                // Wait for any preloader to disappear before taking the screenshot
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".preloader")));

                // Capture the screenshot
                File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

                // Create a unique file name using the test method name and current timestamp
                String methodName = result.getName();
                String timestamp = String.valueOf(System.currentTimeMillis());
                File destFile = new File("screenshots/" + status + "_" + methodName + "_" + timestamp + ".png");

                // Save the screenshot to the desired location
                FileUtils.copyFile(srcFile, destFile);
                System.out.println(status + " screenshot saved to: " + destFile.getAbsolutePath());
            } catch (Exception e) {
                System.err.println("Failed to take " + status + " screenshot: " + e.getMessage());
            }
        }
    }

    // Called before a test starts.
    @Override
    public void onTestStart(ITestResult result) {
        captureScreenshot("START", result);
    }

    // Called after a test passes.
    @Override
    public void onTestSuccess(ITestResult result) {
        captureScreenshot("SUCCESS", result);
    }

    // Called after a test fails.
    @Override
    public void onTestFailure(ITestResult result) {
        captureScreenshot("FAIL", result);
    }


}
