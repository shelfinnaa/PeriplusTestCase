package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class HomePage {
    
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By signInLink = By.id("nav-signin-text");
    private By searchField = By.id("filter_name");
    private By searchButton = By.cssSelector("button.btnn[type='submit']");

    // Constructor
    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Method to click the "Sign In" link
    public void clickSignIn() {
        // Wait until the preloader disappears
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".preloader")));
        // Wait until the "Sign In" link is clickable and then click it
        WebElement signInElement = wait.until(ExpectedConditions.elementToBeClickable(signInLink));
        signInElement.click();
    }

    // Method to enter search query in the search field
    public void enterSearchQuery(String query) {
        // Wait until the preloader is gone
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".preloader")));
        // Wait until the search field is visible and enter the query
        WebElement searchFieldElement = wait.until(ExpectedConditions.visibilityOfElementLocated(searchField));
        searchFieldElement.sendKeys(query);
    }

    // Method to click the search button
    public void clickSearchButton() {
        // Wait until the preloader disappears
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));
        // Wait until the search button is clickable and then click it
        WebElement searchButtonElement = wait.until(ExpectedConditions.elementToBeClickable(searchButton));
        searchButtonElement.click();
    }

    public boolean verifySearchResult(String query) {
        // Get the list of product items
        List<WebElement> resultItems = driver.findElements(By.cssSelector(".single-product .product-content h3 a"));
    
        // Iterate through the results and check if the query matches any item
        for (WebElement item : resultItems) {
            if (item.getText().toLowerCase().contains(query.toLowerCase())) {
                item.click();  // Click the first result that matches
                return true;  // Return true when a match is found
            }
        }
    
        return false;  // Return false if no results match
    }
}
