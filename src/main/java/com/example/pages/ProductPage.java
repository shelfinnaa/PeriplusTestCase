package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProductPage extends BasePage {


    // Locators
    @FindBy(css = "button.btn.btn-add-to-cart")
    private WebElement addToCartButton;

    @FindBy(id = "show-your-cart")
    private WebElement cartButton;

    @FindBy(id = "Notification-Modal")
    private WebElement notificationModal;

    // Constructor
    public ProductPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    // Method to click the Add to Cart button to add an item into the car
    public void clickAddToCart() {
        waitForPreloaderToDisappear();
        addToCartButton.click();
        waitForVisibility(By.id("Notification-Modal"));
    }

    // Method to click the cart button/icon to be redirected to the cart page. 
    public void clickCartButton() {
        waitForPreloaderToDisappear();

        // Wait for the modal to disappear before clicking the cart button
        if (isElementVisible(notificationModal)) {
            waitForInvisibility(By.id("Notification-Modal"));
        }
        cartButton.click();
    }

    // Method to get Book Title and ISBN from the page title
    public String getIsbnNumber() {
        waitForPreloaderToDisappear();

        /*
        The product page does not display the ISBN number directly.
        Therefore, we extract it from the page title, which includes it in a specific format.
        Format: Sapiens | Yuval Noah Harari | 9780099590088 | Periplus Online Bookstore - Indonesia
        Get the page title using " | " as a delimiter
        */
        String pageTitle = driver.getTitle().trim();
        System.out.println("Page title is: " + pageTitle);

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
