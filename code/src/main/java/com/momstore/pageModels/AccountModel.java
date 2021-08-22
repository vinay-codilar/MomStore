package com.momstore.pageModels;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AccountModel {

    @FindBy(css = ".box-information .box-content p")
    private WebElement userDetails;
    @FindBy(css = ".customer-name .name-email")
    private WebElement userName;
    @FindBy(css = ".column.main .nav.item a")
    private WebElement logoutLink;
    @FindBy(xpath = "//div[@class='messages']/div/div")
    private WebElement messages;

    /**
     * @param driver - Constructor
     */
    public AccountModel(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    /**
     * @return WebElement
     */
    public WebElement getUserName() {
        return userName;
    }

    /**
     * @return WebElement
     */
    public WebElement getUserDetails(WebElement userDetails) {
        return userDetails;
    }

    /**
     * @return WebElement
     */
    public WebElement getLogoutLink() {
        return logoutLink;
    }

    /**
     * @return WebElement
     */
    public WebElement getMessages() {
        return messages;
    }
}
