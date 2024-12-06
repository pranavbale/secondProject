package tests;

import TestComponents.BaseTest;
import TestComponents.Retry;
import com.pranavbale.pageObjects.*;
import org.testng.Assert;
import org.testng.IRetryAnalyzer;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

public class ErrorValidationsTest extends BaseTest {

    // enter username and password
    String URL = "https://rahulshettyacademy.com/client/";
    String username = "pranavbale99@gmail.com";
    String password = "Pranav@123";
    String selectProduct = "ADIDAS ORIGINAL";
    String country = "India";
    String expectedConformationMessage = "Thankyou for the order. dfghd";

    // this Test is used to check the Login Error Validations
    @Test(retryAnalyzer = Retry.class)
    public void loginErrorValidation() throws IOException {

        // working with launchPage
        landingPage.loginApp(URL, username, password);
        String expectErrorMessage = "Incorrect email or password.";
        String actualErrorMessage = landingPage.getErrorMessage();
        Assert.assertEquals(actualErrorMessage, expectErrorMessage);

    }

    // This Test is used to check the correct product enter in to the card or not
    @Test()
    public void productErrorValidation() throws IOException {

        // working with launchPage
        ProductCatalogue productCatalogue = landingPage.loginApp(URL, username, password);

        // add product in to the card
//        List<WebElement> products = productCatalogue.getProductList();
//        WebElement product = productCatalogue.getProductByName(selectProduct);
        productCatalogue.addProductToCard(selectProduct);
        CartPage cartPage = productCatalogue.moveToCard();

        // verify the card product and move to payment and conformation
        boolean match = cartPage.VerifyProductDisplay(selectProduct);
        // NO Assertion should be return in to the pageObject
        // because at the time of error it hard to find error
        Assert.assertTrue(match);
        CheckoutPage checkoutPage = cartPage.goToCheckout();

        // make the payment and move to conformation page
        checkoutPage.selectCountry(country);
        ConformationPage conformationPage = checkoutPage.submit();

        // check the conformation page
        Assert.assertEquals(conformationPage.checkConformation(), expectedConformationMessage.toUpperCase());
    }

    @Test(dependsOnMethods = {"productErrorValidation"})
    public void OrderHistoryTest() {

        // working with launchPage
        ProductCatalogue productCatalogue = landingPage.loginApp(URL, username, password);
        OrderPage orderPage = productCatalogue.moveToOrder();
        Assert.assertTrue(orderPage.VerifyOrderDisplay(selectProduct));
    }


}
