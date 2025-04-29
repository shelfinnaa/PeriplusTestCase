package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By addToCartButton = By.cssSelector("button.btn.btn-add-to-cart"); // Update this if your Add to Cart button uses a different class
    private By cartButton = By.id("show-your-cart");

    // Constructor
    public ProductPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }


    // Click the Add to Cart button to add an item into the car
    public void clickAddToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton)).click();
    }

    // Click the cart button/icon to be redirected to the cart page. 
    public void clickCartButton() {
        wait.until(ExpectedConditions.elementToBeClickable(cartButton)).click();
    }

    // Method to get Book Title and ISBN from the page title
    public String getIsbnNumber() {

    /*
    The product page does not display the ISBN number directly.
    Therefore, we extract it from the page title, which includes it in a specific format.
    Format: Sapiens | Yuval Noah Harari | 9780099590088 | Periplus Online Bookstore - Indonesia
    Get the page title using " | " as a delimiter
     */
    String pageTitle = driver.getTitle().trim();

    // Split the title
    String[] parts = pageTitle.split(" \\| "); 

    // Extract the ISBN number from the third part
    if (parts.length >= 3) {
        String isbn = parts[2].trim();
        return isbn;
    } else {
        throw new RuntimeException("Unexpected Title Format");
    }
}

    
}
