package com.pranavbale.pageObjects;

import com.pranavbale.abstractComponents.AbstractComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LandingPage extends AbstractComponent {

    // initialize the variable
    WebDriver driver;

    // create a constructor
    public LandingPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        // PageFactory is a class that is used to create a WebElement from the @FindBy annotation
        // it initializes in the constructor i.e.
        // all webElement are created at the time of object creation
        PageFactory.initElements(driver, this);
    }


    // the below code is return in another way
    // driver.findElement(By.id("userEmail")).sendKeys(username);
    @FindBy(id = "userEmail")
    WebElement userEmail;

    // driver.findElement(By.id("userPassword")).sendKeys(password);
    @FindBy(id = "userPassword")
    WebElement userPassword;

    // driver.findElement(By.id("login"))
    @FindBy(id = "login")
    WebElement submit;

    @FindBy(css="[class*='flyInOut']")
    WebElement errorMessage;

    // Launch Browser
    public void goTo(String URL) {
        driver.get(URL);
    }

    // Login to the web Application
    public ProductCatalogue loginApp(String URL,String username, String password) {
        goTo(URL);
        userEmail.sendKeys(username);
        userPassword.sendKeys(password);
        submit.click();
        return new ProductCatalogue(driver);
    }

    public String getErrorMessage() {
        waitForWebElementToAppear(errorMessage);
        return errorMessage.getText();
    }

}
