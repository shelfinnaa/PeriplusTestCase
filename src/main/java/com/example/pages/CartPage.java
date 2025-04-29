package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;


// Represents the Shopping Cart Page and provides methods to interact with within the page. 
public class CartPage {
    
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By CART_ITEM_LOCATOR = By.cssSelector(".row-cart-product");
    private By ISBN_LOCATOR = By.cssSelector(".col-lg-10 .row:nth-of-type(2)");

    // Constructor 
    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /* Method to check if a product matches in the cart using its ISBN. 
    It returns a boolean: 
    - 'true' if a product with the matching ISBN is found in the cart,
    - 'false' if no match is found.
    */
    public boolean checkProductMatchInCart(String expectedIsbn) {

    // Wait until all cart items are visible. Since there's no ID, we use a CSS selector to find them.
    wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(CART_ITEM_LOCATOR));

    // Get each cart item and save them in a list 
    List<WebElement> cartItems = driver.findElements(By.cssSelector(".row-cart-product"));

    // Initialize a boolean by assuming no match is found yet. 
    boolean matchFound = false;

    // Go through each product and check if the ISBN matches.
    for (WebElement item : cartItems) {
        
        /* Look in the section of the product where the ISBN is shown. 
        Since there's no ID or name, we use a CSS selector to locate it.
        Then, we save the text content (ISBN) into the isbnText string.
        */
        WebElement isbnContainer = item.findElement(ISBN_LOCATOR);
        String isbnText = isbnContainer.getText().trim();

        // Compare the ISBN we found with the one we expect.
        // If it matches, we set the boolean variable to true and exit the loop.
        if (isbnText.contains(expectedIsbn)) {
            matchFound = true;
            break;
        }
    }

    //return boolean
    return matchFound;
    
} 
    

}
