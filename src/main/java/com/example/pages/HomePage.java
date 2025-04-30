package com.example.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class HomePage extends BasePage {

    // Locators 
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
        signInLink.click();
    }

    // Method to enter search query in the search field
    public void enterSearchQuery(String query) {
        waitForPreloaderToDisappear();
        searchField.clear();
        searchField.sendKeys(query);
    }

    // Method to click the search button
    public void clickSearchButton() {
        waitForPreloaderToDisappear();
        searchButton.click();
    }

    /*  
    Method to check if a search query matches any item in the search results, 
    clicks on the first matching item, and returns true if a match is found; 
    otherwise, it returns false.
    */
    public boolean verifySearchResult(String query) {
        waitForPreloaderToDisappear();

        // Iterate through the results and check if the query matches any item
        for (WebElement item : searchResults) {
            if (item.getText().toLowerCase().contains(query.toLowerCase())) {
                item.click();  // Click the first result that matches
                return true;
            }
        }
        return false;
    }

}
