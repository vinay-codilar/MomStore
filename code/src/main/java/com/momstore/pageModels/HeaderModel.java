package com.momstore.pageModels;

import com.momstore.utilities.ExcelUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class HeaderModel {
    WebDriver driver;

    @FindBy(css = ".header.links .customer-name")
    private WebElement loginLink;
    @FindBy(css = "#search")
    private WebElement searchBox;
    @FindBy(css = ".showcart")
    private WebElement cartIcon;
    @FindBy(xpath = "//span[@class='customer-name']/span")
    private WebElement accountIcon;
    @FindBy(css = ".header .links li .authorization-link")
    private WebElement logoutLink;

    /**
     * @param driver - Constructor
     */
    public HeaderModel(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    /**
     * Search for products
     * @param searchListingModel - SearchListingModel
     * @param wait - WebDriverWait
     */
    public void searchForProducts(SearchListingModel searchListingModel, WebDriverWait wait) {
        // Searching for the products
        getSearchBox().sendKeys(ExcelUtils.getDataMap().get("search_text"), Keys.RETURN);
        wait.until(ExpectedConditions.visibilityOf(searchListingModel.getSearchTitle()));
        Assert.assertTrue(searchListingModel.getSearchTitle().getText()
                .contains(ExcelUtils.getDataMap().get("search_text")));
    }

    /**
     * @return WebElement - Login Link
     */
    public WebElement getLoginLink() {
        return loginLink;
    }

    /**
     * @return WebElement - Search box
     */
    public WebElement getSearchBox() {
        return searchBox;
    }

    /**
     * @return WebElement - Cart Icon
     */
    public WebElement getCartIcon() {
        return cartIcon;
    }

    /**
     * @return WebElement - Account Dropdown
     */
    public WebElement getAccountIcon() {
        return accountIcon;
    }

    /**
     * @return WebElement - Logout Link
     */
    public WebElement getLogoutLink() {
        return logoutLink;
    }
}
