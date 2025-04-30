package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

// BasePage.java
public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    // Constructor
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Waits until the preloader element disappears.
    public void waitForPreloaderToDisappear() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".preloader")));
    }

    // Waits for the element specified by the locator to be present on the page and returns the element.
    public WebElement find(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    // Waits for the element specified by the locator to be clickable and clicks it.
    public void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    // Waits for the element specified by the locator to be visible, clears any pre-existing text, and types the provided text into the element.
    public void type(By locator, String text) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(text);
    }

    // Waits for the element specified by the locator to be visible and returns its text content. 
    public String getText(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText().trim();
    }

    // Waits until the element specified by the locator is visible on the page.
    public void waitForVisibility(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // Waits until the element specified by the locator becomes invisible on the page.
    public void waitForInvisibility(By locator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    // Waits until all elements in the provided list are visible on the page.
    public void waitForVisibilityList(List<WebElement> elements) {
        for (WebElement element : elements) {
            wait.until(ExpectedConditions.visibilityOf(element));
        }
    }

    // Checks if a given element is visible on the page by returning true if it's displayed and false if not.
    public boolean isElementVisible(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

}
