package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

// LoginPage.java
public class LoginPage extends BasePage {

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
        super(driver);  // Call BasePage constructor to initialize WebDriver and WebDriverWait
        PageFactory.initElements(driver, this);
    }

    // Enters the provided email into the email input field
    public void enterEmail(String email) {
        waitForPreloaderToDisappear();
        emailField.clear();
        emailField.sendKeys(email);
    }

    // Enters the provided password into the password input field
    public void enterPassword(String password) {
        waitForPreloaderToDisappear();
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    // Click Login button
    public void clickLogin() {
        waitForPreloaderToDisappear();
        loginButton.click();
    }

    // Perform the entire login sequence (enter email, password, and click login button)
    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLogin();
    }

    // Wait until the login text element is visible and get the text from the element and trim any surrounding whitespace
    public String getDisplayedLoginName() {
        return signInText.getText();
    }
}

