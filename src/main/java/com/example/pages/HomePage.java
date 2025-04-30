package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class HomePage extends BasePage {

    // Use @FindBy annotations instead of By locators
    @FindBy(id = "nav-signin-text")
    private WebElement signInLink;

    @FindBy(id = "filter_name")
    private WebElement searchField;

    @FindBy(css = "button.btnn[type='submit']")
    private WebElement searchButton;

    @FindBy(css = ".single-product .product-content h3 a")
    private List<WebElement> searchResults;

    // Constructor
    public HomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    // Method to click the "Sign In" link
    public void clickSignIn() {
        waitForPreloaderToDisappear();
        // Wait until the "Sign In" link is clickable and then click it
        signInLink.click();
    }

    // Method to enter search query in the search field
    public void enterSearchQuery(String query) {
        waitForPreloaderToDisappear();
        // Wait until the search field is visible and enter the query
        searchField.clear();
        searchField.sendKeys(query);
    }

    // Method to click the search button
    public void clickSearchButton() {
        waitForPreloaderToDisappear();
        // Wait until the search button is clickable and then click it
        searchButton.click();
    }

    public boolean verifySearchResult(String query) {
        waitForPreloaderToDisappear();

        // Iterate through the results and check if the query matches any item
        for (WebElement item : searchResults) {
            if (item.getText().toLowerCase().contains(query.toLowerCase())) {
                item.click();  // Click the first result that matches
                return true;  // Return true when a match is found
            }
        }

        return false;  // Return false if no results match
    }

    public void waitForSearchResultsToLoad() {
        waitForPreloaderToDisappear();
    }
}
