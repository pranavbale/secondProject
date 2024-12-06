package com.pranavbale.abstractComponents;

import com.pranavbale.pageObjects.CartPage;
import com.pranavbale.pageObjects.OrderPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

// this class contain all the common methods
// this class is extended by the pageObjects classes
// all the PageObjects classes can access this common class
public class AbstractComponent {

    // declare a variable
    WebDriver driver;
    WebDriverWait wait;

    // initialize the variable
    public AbstractComponent(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        PageFactory.initElements(driver, this);
    }

    // get some web elements
    @FindBy(css = "[routerlink*='cart']")
    WebElement card;

    @FindBy(css = "[routerlink*='myorders']")
    WebElement orderHeader;



    // create a common explicit wait for Element To Appear
    public void waitForElementToAppear(By findBy) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
    }

    // create a common explicit wait for Element To Appear
    public void waitForWebElementToAppear(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    // create a common explicit wait for Element To Disappear
    public void waitForElementToDisappear(WebElement element) {
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    // move to card button
    public CartPage moveToCard() {
        card.click();
        return new CartPage(driver);
    }

    // move to Order button
    public OrderPage moveToOrder() {
        orderHeader.click();
        return new OrderPage(driver);
    }

}
