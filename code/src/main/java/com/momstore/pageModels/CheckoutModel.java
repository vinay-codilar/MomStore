package com.momstore.pageModels;

import com.momstore.extent_reports.ExtentReport;
import com.momstore.interfaces.Constants;
import com.momstore.loggers.Loggers;
import com.momstore.utilities.ExcelUtils;
import com.momstore.utilities.Property;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class CheckoutModel implements Constants {
    Select select;
    WebDriver driver;

    @FindBy(css = ".action.action-auth-toggle.primary")
    private WebElement signInLink;
    @FindBy(xpath = "//input[@name='firstname']")
    private WebElement firstName;
    @FindBy(xpath = "//input[@name='lastname']")
    private WebElement lastName;
    @FindBy(id = "customer-email")
    private WebElement emailField;
    @FindBy(id = "customer-password")
    private WebElement passwordField;
    @FindBy(xpath = "//input[@name='street[0]']")
    private WebElement streetAddress;
    @FindBy(xpath = "//select[@name='region_id']")
    private WebElement city;
    @FindBy(xpath = "//select[@name='']")
    private WebElement area;
    @FindBy(xpath = "//select[@name='country_id']")
    private WebElement country;
    @FindBy(xpath = "//input[@name='postcode']")
    private WebElement postCode;
    @FindBy(xpath = "//input[@name='telephone']")
    private WebElement telephone;
    @FindBy(css = ".action.login.primary")
    private WebElement loginSubmit;
    @FindBy(css = ".shipping-address-item.selected-item")
    private WebElement selectedShip;
    @FindBy(xpath = "//tbody/tr")
    private List<WebElement> shippingRates;
    @FindBy(css = "#shipping-method-buttons-container .button")
    private WebElement continuePayment;
    @FindBy(xpath = "//td/input[1]")
    private WebElement selectShipping;
    @FindBy(xpath = "//td[4]")
    private By shipMethodName;

    @FindBy(id = "billing-address-same-as-shipping-checkmo")
    private WebElement setDefaultBilling;
    @FindBy(css = ".opc-progress-bar-item._active")
    private WebElement paymentPageRedirect;
    @FindBy(css = ".payment-group")
    private WebElement paymentGroup;
    @FindBy(css = ".payment-group .payment-method ")
    private List<WebElement> paymentMethods;
    @FindBy(css = ".payment-method-title label")
    private List<WebElement> paymentMethodLabels;
    @FindBy(css = "loading-mask")
    private WebElement loader;
    @FindBy(css = ".payment-method._active .action.primary.checkout")
    private WebElement placeOrder;

    /**
     * @param driver - WebDriver element
     */
    public CheckoutModel(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    /**
     * Login to Account from Login page before Checkout page
     */
    public void preCheckoutLogin(LoginModel loginModel) {
        // Getting the Customer Details
        ExcelUtils.excelConfigure(CUSTOMER_SAMPLE_DATA);
//        ExcelUtils.getRowData(Integer.parseInt(Objects.requireNonNull(Property.getProperty("testRow"))));
        ExcelUtils.getRowData(1);

        // Fill the login form
        loginModel.getEmailId().sendKeys(ExcelUtils.getDataMap().get("email_id"));
        loginModel.getPassword().sendKeys(ExcelUtils.getDataMap().get("password"));
        loginModel.getSubmit().click();

        Loggers.getLogger().info("Clicked on Submit button");
        ExtentReport.getExtentNode().info("Clicked on Submit button");
    }

    /**
     * Login to Account from Checkout page
     */
    public void checkoutLogin(LoginModel loginModel) {
        // Getting the Customer Details
        ExcelUtils.excelConfigure(CUSTOMER_SAMPLE_DATA);
        ExcelUtils.getRowData(Integer.parseInt(Objects.requireNonNull(Property.getProperty("testRow"))));

        // Fill the login form
        getEmailField().sendKeys(ExcelUtils.getDataMap().get("email_id"));
        getPasswordField().sendKeys(ExcelUtils.getDataMap().get("password"));
        getLoginSubmit().click();

        Loggers.getLogger().info("Clicked on Submit button");
        ExtentReport.getExtentNode().info("Clicked on Submit button");
    }

    /**
     * Fill the Shipping Address form in Checkout page
     */
    public void addShippingAddress() {
        try {
            getEmailField().sendKeys(ExcelUtils.getDataMap().get("email_id"));
        } catch (Exception e) {
            Loggers.getLogger().info("User already logged in");
        }

        select = new Select(getCountry());
        select.selectByValue(ExcelUtils.getDataMap().get("country"));

        select = new Select(getCity());
        select.selectByValue(ExcelUtils.getDataMap().get("city"));

        select = new Select(getArea());
        select.selectByValue(ExcelUtils.getDataMap().get("region_id"));

        getStreetAddress().sendKeys(ExcelUtils.getDataMap().get("street_address"));
        getTelephone().sendKeys(ExcelUtils.getDataMap().get("mobile_number"));
    }

    /**
     * Select one of the Payment Methods
     */
    public void selectPaymentMethod() {
        Iterator<WebElement> methods = getPaymentMethodLabels().iterator();
        while(methods.hasNext()) {
            WebElement mode = methods.next();
            if(mode.getAttribute("for").equalsIgnoreCase(Property.getProperty("paymentMethod"))) {
                mode.click();

                Loggers.getLogger().info("'"+Property.getProperty("paymentMethod")+"' payment method selected");
                ExtentReport.getExtentNode().pass("'"+Property.getProperty("paymentMethod")+"' payment method selected");
            } else {
                Loggers.getLogger().fatal("Payment Method is not displayed");
                ExtentReport.getExtentNode().fatal("Payment Method is not displayed");
            }
        }
    }

    /**
     * Place Order after selecting Payment Method
     */
    /*public void placeOrder(WebDriverWait wait) {
        javascriptExecutor = (JavascriptExecutor) driver;

        // Selects the Payment Method
        selectPaymentMethod();

        // Place an Order
        Iterator<WebElement> method = getPaymentMethods().iterator();
        while(method.hasNext()) {
            WebElement mode = method.next();
            if(mode.getAttribute("class").contains("_active")) {
                WebElement button = mode.findElement(By.cssSelector(".action.primary.checkout"));
//                javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", button);
                wait.until(ExpectedConditions.elementToBeClickable(button));
                button.click();
            }
        }
    }*/

    /**
     * @return WebElement - Signin Link
     */
    public WebElement getSignInLink() {
        return signInLink;
    }

    /**
     * @return WebElement - Email Field
     */
    public WebElement getEmailField() {
        return emailField;
    }

    /**
     * @return WebElement - Password Field
     */
    public WebElement getPasswordField() {
        return passwordField;
    }

    /**
     * @return WebElement - Street Address
     */
    public WebElement getStreetAddress() {
        return streetAddress;
    }

    /**
     * @return WebElement - City
     */
    public WebElement getCity() {
        return city;
    }

    /**
     * @return WebElement - Area
     */
    public WebElement getArea() {
        return area;
    }

    /**
     * @return WebElement - Country
     */
    public WebElement getCountry() {
        return country;
    }

    /**
     * @return WebElement - Postcode
     */
    public WebElement getPostCode() {
        return postCode;
    }

    /**
     * @return WebElement - Telephone
     */
    public WebElement getTelephone() {
        return telephone;
    }

    /**
     * @return WebElement - Login Submit Button
     */
    public WebElement getLoginSubmit() {
        return loginSubmit;
    }

    /**
     * @return WebElement - Selected Shipping Method
     */
    public WebElement getSelectedShip() {
        return selectedShip;
    }

    /**
     * @return List<WebElement> - Shipping Rates
     */
    public List<WebElement> getShippingRates() {
        return shippingRates;
    }

    /**
     * @return WebElement - Next Submit Button
     */
    public WebElement getContinuePayment() {
        return continuePayment;
    }

    /**
     * @return WebElement - Select Shipping Method
     */
    public WebElement getSelectShipping() {
        return selectShipping;
    }

    /**
     * @return By - Ship Method Name
     */
    public By getShipMethodName() {
        return shipMethodName;
    }

    /**
     * @return WebElement - Get Default Billing
     */
    public WebElement getSetDefaultBilling() {
        return setDefaultBilling;
    }

    /**
     * @return WebElement - Payment Group
     */
    public WebElement getPaymentGroup() {
        return paymentGroup;
    }

    /**
     * @return WebElement - Get Payment Methods
     */
    public List<WebElement> getPaymentMethods() {
        return paymentMethods;
    }

    /**
     * @return WebElement - Payment Method Labels
     */
    public List<WebElement> getPaymentMethodLabels() {
        return paymentMethodLabels;
    }

    /**
     * @return WebElement - First Name
     */
    public WebElement getFirstName() {
        return firstName;
    }

    /**
     * @return WebElement - Last Name
     */
    public WebElement getLastName() {
        return lastName;
    }

    /**
     * @return WebElement - Payment Page
     */
    public WebElement getPaymentPageRedirect() {
        return paymentPageRedirect;
    }

    /**
     * @return WebElement - Loader
     */
    public WebElement getLoader() {
        return loader;
    }

    /**
     * @return WebElement - Place Order Button
     */
    public WebElement getPlaceOrder() {
        return placeOrder;
    }
}
