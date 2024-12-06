package com.pranavbale.pageObjects;

import com.pranavbale.abstractComponents.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ProductCatalogue extends AbstractComponent {

    // Declare the variables
    WebDriver driver;

    // initialize the variable
    public ProductCatalogue(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // get a Web Elements
    // if we required a WebElement then user @FindBy
    @FindBy(css = ".mb-3")
    List<WebElement> products;

    @FindBy(id = "toast-container")
    WebElement spinner;

    // if need only a By  then initialize the verialbe
    By productBy = By.cssSelector(".mb-3");
    By addToCard = By.cssSelector(".card-body button:last-of-type");
    By toastMessage = By.cssSelector("#toast-container");

    // get a list of product
    public List<WebElement> getProductList() {
        waitForElementToAppear(productBy);
        return products;
    }

    // get a product by name
    public WebElement getProductByName(String selectProduct) {
        return getProductList().stream()
                .filter(pro -> pro.findElement(By.cssSelector("b")).getText().equals(selectProduct))
                .findFirst().orElse(null);
    }

    // add a product to the card and load for the conformation
    public void addProductToCard(String selectProduct) {
        getProductByName(selectProduct).findElement(addToCard).click();
        waitForElementToAppear(toastMessage);
        waitForElementToDisappear(spinner);
    }
}
