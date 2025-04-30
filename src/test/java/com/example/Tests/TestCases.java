package com.example.Tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

import com.example.base.DriverManager;
import com.example.pages.CartPage;
import com.example.pages.HomePage;
import com.example.pages.ProductPage;
import com.example.pages.LoginPage;
import com.example.TestData;

public class TestCases {

    String addedBookIsbn;

    private WebDriver driver;
    private HomePage homePage;
    private LoginPage loginPage;
    private ProductPage productPage;
    private CartPage cartPage;

    /*
    This setup method runs once before any tests in the class.
    It initializes the WebDriver and the Page Object instances for the home, login, cart, and product pages.
    It also navigates to the Periplus website and performs a login using predefined credentials.
    This ensures the test suite starts from a logged-in state, ready for further test steps.
    */
    @BeforeClass
    public void setUpClass() {
        // Initialize WebDriver once for the whole class
        driver = DriverManager.getDriver();

        // Initialize Page Objects
        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
        cartPage = new CartPage(driver);
        productPage = new ProductPage(driver);

        // Navigate to the website and perform login using constants from TestData
        driver.get(TestData.BASE_URL);
        homePage.clickSignIn();
        loginPage.login(TestData.EMAIL, TestData.PASSWORD);
        // Take a screenshot after login is completed
        loginPage.takeScreenshot("AfterLogin");
    }


    /*
    This test verifies the login functionality of the application.
    It waits for the user's display name to appear on the navigation bar after login.
    The test then retrieves and trims the displayed name text and compares it with the expected name "Shelfinna".
    If the displayed name does not match, the test fails, indicating an unsuccessful login or incorrect user display.
    */
    @Test
    public void testValidLogin() {
        String actualText = loginPage.getDisplayedLoginName();
        Assert.assertTrue(
                actualText.equalsIgnoreCase(TestData.EXPECTED_USERNAME),
                "Login name is not displayed correctly!"
        );
    }


    /*
    This test validates the book search functionality on the home page.
    It enters a search query, clicks the search button, and verifies the search results to check if any result contains the expected query text.
    If a match is found, the test clicks on the first item found to proceed to the product page.
    The test fails if no relevant search results are found.
    */
    @Test(dependsOnMethods = {"testValidLogin"})
    // This test depends on 'testValidLogin', meaning it will only run if the login test passes first.
    public void testSearchBook() throws InterruptedException {

        // Enter the search query from TestData in the search field and click the search button
        homePage.enterSearchQuery(TestData.SEARCH_QUERY);
        homePage.clickSearchButton();
        homePage.waitForSearchResultsToLoad();


        // Call the method to verify if the search results contain the query from TestData
        boolean resultsContainQuery = homePage.verifySearchResult(TestData.SEARCH_QUERY);

        // Assert that the search results contain the query
        Assert.assertTrue(resultsContainQuery, "No search result contains the query!");
    }


    /* 
    This test verifies that a book can be successfully added to the shopping cart.
    It simulates clicking the "Add to Cart" button on the product page, retrieves the book's ISBN and waits for the confirmation modal to appear. 
    The test then asserts that the modal is displayed and contains the expected success message.
    If the modal does not appear or the message is incorrect, the test fails.
    */
    @Test(dependsOnMethods = {"testSearchBook"})
    // This test depends on testSearchBook, meaning the search must successfully find an item and view the item on its product page.
    public void testAddBookToCart() throws InterruptedException {

        // Click the "Add to Cart" button on the product page
        productPage.clickAddToCart();

        // Save the ISBN number
        addedBookIsbn = productPage.getIsbnNumber();

        // Wait for the modal to appear using its ID
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement modalElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("notification-modal-header")));

        // Verify that the modal is displayed
        Assert.assertTrue(modalElement.isDisplayed(), "Add to cart modal did not appear!");

        // Get the modal text and trim any surrounding whitespace
        WebElement modalTextElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".modal-text")));
        String modalMessage = modalTextElement.getText().trim();

        // Verify that the modal message matches the expected message from TestData (case-insensitive)
        Assert.assertTrue(modalMessage.equalsIgnoreCase(TestData.EXPECTED_MODAL_MESSAGE), "Modal message did not match expected!");


    }


    /*This test verifies if the product added to the cart has the correct ISBN.
    It  clicks the cart button, verifies the ISBN, and asserts it matches.
    If no matches are found, the test fails.*/
    @Test(dependsOnMethods = {"testAddBookToCart"}, enabled = false)
    // this test depends on testAddBookToCart, meaning the cart must have a book added before this test runs.
    public void testVerifyItemCart() throws InterruptedException {

        //click the 'Cart' button to navigate to the cart page. 
        productPage.clickCartButton();

        //Check if the product with the ISBN that was added to the cart is present.
        // This method returns true if the product is found, and false if it is not.
        boolean isMatch = cartPage.checkProductMatchInCart(addedBookIsbn);

        // Assert that the product with the expected ISBN is present in the cart.
        // If the product with the expected ISBN is not found, this assertion will fail.
        Assert.assertTrue(isMatch, "Expected ISBN was not found in the cart.");


    }


    // Quit driver after all tests
    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
