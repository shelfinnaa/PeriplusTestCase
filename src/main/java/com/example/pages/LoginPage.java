package com.example.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

// LoginPage.java
public class LoginPage extends BasePage {

    // Locators 
    @FindBy(name = "email")
    private WebElement emailField;

    @FindBy(name = "password")
    private WebElement passwordField;

    @FindBy(id = "button-login")
    private WebElement loginButton;

    @FindBy(id = "nav-signin-text")
    private WebElement signInText;

    // Constructor
    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    // Method to enter the provided email into the email input field.
    public void enterEmail(String email) {
        waitForPreloaderToDisappear();
        emailField.clear();
        emailField.sendKeys(email);
    }

    // Method to enter the provided password into the password input field.
    public void enterPassword(String password) {
        waitForPreloaderToDisappear();
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    // Method to click Login button. 
    public void clickLogin() {
        waitForPreloaderToDisappear();
        loginButton.click();
    }

    // Method to perform the entire login sequence (enter email, password, and click login button). 
    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLogin();
    }

    // Method to wait until the login text element is visible and get the text from the element.
    public String getDisplayedLoginName() {
        return signInText.getText();
    }
}

