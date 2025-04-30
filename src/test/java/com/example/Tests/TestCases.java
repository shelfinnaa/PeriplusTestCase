package com.example.Tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.annotations.Listeners;

import com.example.base.DriverManager;
import com.example.pages.CartPage;
import com.example.pages.HomePage;
import com.example.pages.ProductPage;
import com.example.pages.LoginPage;
import com.example.TestData;

@Listeners(com.example.utils.ScreenshotListener.class)
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
    It also navigates to the Periplus website. 
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

        // Navigate to the website using the base URL defined in TestData
        driver.get(TestData.BASE_URL);

    }


    /*
    This test performs login and verifies the login functionality of the application.
    It clicks on the sign-in link on the home page and performs login when redirected to the login page
    It waits for the user's display name to appear on the navigation bar after login.
    The test then retrieves the displayed name text and compares it with the expected name "Shelfinna".
    If the displayed name does not match, the test fails, indicating an unsuccessful login or incorrect user display.
    */
    @Test
    public void testValidLogin() {
        homePage.clickSignIn();
        loginPage.login(TestData.EMAIL, TestData.PASSWORD);
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
        homePage.waitForPreloaderToDisappear();

        // Call the method to verify if the search results contain the query from TestData
        boolean resultsContainQuery = homePage.verifySearchResult(TestData.SEARCH_QUERY);

        // Assert that the search results contain the query
        Assert.assertTrue(resultsContainQuery, "No search result contains the query!");
    }


    /*
    This test verifies if the product added to the cart has the correct ISBN.
    It saves the ISBN of the product to be added, adds the product to the cart, clicks the cart button,
    verifies the ISBN, and asserts that it matches. If no match is found, the test fails. 
    */
    @Test(dependsOnMethods = {"testSearchBook"})
    // this test depends on testSearchBook, meaning the cart must have a book added before this test runs.
    public void testVerifyItemCart() throws InterruptedException {

        // Save the ISBN of the item
        addedBookIsbn = productPage.getIsbnNumber();

        // Add item to cart
        productPage.clickAddToCart();

        // Click the 'Cart' button to navigate to the cart page. 
        productPage.clickCartButton();

        // Check if the product with the ISBN that was added to the cart is present.
        // This method returns true if the product is found, and false if it is not.
        boolean isMatch = cartPage.checkProductMatchInCart(addedBookIsbn);

        // Assert that the product with the expected ISBN is present in the cart.
        // If the product with the expected ISBN is not found, this assertion will fail.
        Assert.assertTrue(isMatch, "Expected ISBN was not found in the cart.");
    }

    /* 
    This test verifies the functionality of deleting an item from the cart.
    It removes the item from the cart using its ISBN, refreshes the cart page to reflect the changes,
    and asserts that the item is no longer in the cart. If the item is still present, the test will fail. 
    */
    @Test(dependsOnMethods = {"testVerifyItemCart"})
    public void testDeleteItemFromCart() // this test requires a book to be added
    {
        // Remove the item using its ISBN
        cartPage.removeBookByIsbn(addedBookIsbn);

        // Refresh the cart page to re-fetch updated cart items
        driver.navigate().refresh();

        // Assert that the item is no longer in the cart
        boolean isStillPresent = cartPage.checkProductMatchInCart(addedBookIsbn);
        Assert.assertFalse(isStillPresent, "Book was not deleted from cart as expected!");
    }

    /* 
    This test verifies the logout functionality.
    It logs the user out, then checks if the URL or page source contains the word "Login" or "Sign In" to confirm successful logout.
    If the user is still on a logged-in page, the test will fail. 
    */
    @Test(dependsOnMethods = {"testDeleteItemFromCart"})
    public void testLogout() {
        cartPage.logout();
        boolean isLoggedOut = driver.getCurrentUrl().contains("Login") || driver.getPageSource().contains("Sign In");
        Assert.assertTrue(isLoggedOut, "Logout failed.");
    }

    // Quit driver after all tests
    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
