package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.interactions.Actions;

import java.util.List;


// Represents the Shopping Cart Page and provides methods to interact with within the page. 
public class CartPage extends BasePage {

    private Actions actions;

    // Locators (No ID Found)
    @FindBy(css = ".row-cart-product")
    private List<WebElement> cartItems;

    @FindBy(xpath = "//div[@class='shopping-item']//a[contains(@href, 'Logout')]")
    private WebElement logoutLink;

    @FindBy(css = "span#nav-signin-text")
    private WebElement signInText;

    // Locators for specific parts of a cart item
    private By isbnContainerLocator = By.cssSelector(".col-lg-10 .row:nth-of-type(2)");
    private By removeButtonLocator = By.cssSelector("a.btn-cart-remove");

    // Constructor 
    public CartPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        actions = new Actions(driver);
    }

    /* Method to check if a product matches in the cart using its ISBN. 
    It returns a boolean: 
    - 'true' if a product with the matching ISBN is found in the cart,
    - 'false' if no match is found.
    */
    public boolean checkProductMatchInCart(String expectedIsbn) {

        // Wait until all cart items are visible. Since there's no ID, we use a CSS selector to find them.
        waitForVisibilityList(cartItems);

        // Initialize a boolean by assuming no match is found yet.
        boolean matchFound = false;

        // Go through each product and check if the ISBN matches.
        for (WebElement item : cartItems) {
        
            /* Look in the section of the product where the ISBN is shown.
            Since there's no ID or name, we use a CSS selector to locate it.
            Then, we save the text content (ISBN) into the isbnText string.
            */
            WebElement isbnContainer = item.findElement(isbnContainerLocator);
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

    // Method to find and remove a book from a shopping cart based on its ISBN
    public void removeBookByIsbn(String expectedIsbn) {

        // Wait until all cart items are visible. Since there's no ID, we use a CSS selector to find them.
        waitForVisibilityList(cartItems);

        // Go through each product and check if the ISBN matches.
        for (WebElement item : cartItems) {

            /* Look in the section of the product where the ISBN is shown.
            Since there's no ID or name, we use a CSS selector to locate it.
            Then, we save the text content (ISBN) into the isbnText string.
            */
            WebElement isbnContainer = item.findElement(isbnContainerLocator);
            String isbnText = isbnContainer.getText().trim();

            // Checks if the ISBN found in the cart matches the expectedIsbn.  If they match, it proceeds to remove the item.
            if (isbnText.contains(expectedIsbn)) {
                // Find and click the "Remove" button within this cart item
                WebElement removeButton = item.findElement(removeButtonLocator);
                removeButton.click();

                // Optional: wait for the item to disappear from DOM
                wait.until(ExpectedConditions.stalenessOf(item));
                break;
            }
        }
    }

    public void logout() {
        // Moves the mouse pointer to the 'signInText' element to hover.
        actions.moveToElement(signInText).perform();
        //Click on the 'logout' link to log out. 
        logoutLink.click();
    }


}
