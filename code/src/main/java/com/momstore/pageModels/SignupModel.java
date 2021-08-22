package com.momstore.pageModels;

import com.momstore.project_setup.TestNGBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

public class SignupModel extends TestNGBase {

    @FindBy(css = ".header .links")
    private WebElement headerLinks;
    @FindBy(css = "h1 span")
    private WebElement pageTitle;
    @FindBy(id = "firstname")
    private WebElement firstName;
    @FindBy(id = "lastname")
    private WebElement lastName;
    @FindBy(css = ".terms-conditions label")
    private WebElement termsCheckbox;
    @FindBy(id = "email_address")
    private WebElement emailAddress;
    @FindBy(css = ".control #phone_number")
    private WebElement phoneNumber;
    @FindBy(id = "prefix")
    private WebElement prefixTitle;
    @FindBy(css = ".control #password")
    private WebElement password;
    @FindBy(css = ".control #password-confirmation")
    private WebElement passwordConfirmation;
    @FindBy(css = ".action.submit.primary")
    private WebElement submit;
    @FindBy(xpath = "//div[@class='messages']/div/div")
    private WebElement messages;

    /**
     * @param driver - Webdriver element
     */
    public SignupModel(WebDriver driver) {
        PageFactory.initElements(driver, this);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    /**
     * @return WebElement - Header Links
     */
    public WebElement getHeaderLinks() {
        return headerLinks;
    }

    /**
     * @return WebElement - Page title
     */
    public WebElement getPageTitle() {
        return pageTitle;
    }

    /**
     * @return WebElement - Firstname Field
     */
    public WebElement getFirstName() {
        return firstName;
    }

    /**
     * @return WebElement - Lastname Field
     */
    public WebElement getLastName() {
        return lastName;
    }

    /**
     * @return WebElement - Subscribed Checkbox
     */
    public WebElement getTermsCheckbox() {
        return termsCheckbox;
    }

    /**
     * @return WebElement - Email Address
     */
    public WebElement getEmailAddress() {
        return emailAddress;
    }

    /**
     * @return WebElement - Phone Number
     */
    public WebElement getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @return WebElement - Prefix Title
     */
    public WebElement getPrefixTitle() {
        return prefixTitle;
    }

    /**
     * @return WebElement - Password
     */
    public WebElement getPassword() {
        return password;
    }

    /**
     * @return WebElement - Password Confirmation
     */
    public WebElement getPasswordConfirmation() {
        return passwordConfirmation;
    }

    /**
     * @return WebElement - Submit button
     */
    public WebElement getSubmit() {
        return submit;
    }

    /**
     * @return WebElement - Messages
     */
    public WebElement getMessages() {
        return messages;
    }

}
