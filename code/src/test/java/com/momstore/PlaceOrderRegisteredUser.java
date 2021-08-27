package com.momstore;

import com.momstore.extent_reports.ExtentReport;
import com.momstore.loggers.Loggers;
import com.momstore.pageModels.*;
import com.momstore.project_setup.TestNGBase;
import com.momstore.utilities.ExcelUtils;
import com.momstore.utilities.Property;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Objects;

public class PlaceOrderRegisteredUser extends TestNGBase {
    public WebDriver driver;
    public WebDriverWait wait;
    JavascriptExecutor executor;
    private Select select;

    @BeforeClass(description = "Pre Test Configurations", alwaysRun = true)
    public void preTestRuns() {
        // Initialize Driver
        driver = initializeDriver();
        wait = new WebDriverWait(driver, 5);
        executor = (JavascriptExecutor) driver;

        // Setting the Loggers and Extent Reports
        Loggers.setLogger(UserAccounts.class.getName());
        ExtentReport.createTest("Place Order");

        // Getting the Website Details
        ExcelUtils.excelConfigure(WEBSITE_SAMPLE_DATA);
        ExcelUtils.getRowData(Integer.parseInt(Objects.requireNonNull(Property.getProperty("testRow"))));
    }

    /**
     * Place Order as Registered User
     */
    @Test(description = "Place Order as Registered User", priority = 1, groups = {"placeOrder.minicart"})
    public void placeOrderRegistered() {
        // Setting up the Extent Report
        ExtentReport.createNode("Place Order from Minicart");

        HeaderModel headerModel = new HeaderModel(driver);
        SearchListingModel searchListingModel = new SearchListingModel(driver);
        ProductModel productModel = new ProductModel(driver);
        MinicartModel minicartModel = new MinicartModel(driver);
        CartModel cartModel = new CartModel(driver);
        LoginModel loginModel = new LoginModel(driver);
        CheckoutModel checkoutModel = new CheckoutModel(driver);
        SuccessModel successModel = new SuccessModel(driver);
        OrderDetailModel orderDetailModel = new OrderDetailModel(driver);

        // Searching for the products
        headerModel.searchForProducts(searchListingModel, wait);

        // Select the product passed
        searchListingModel.selectProduct(productModel, wait);

        // Selecting the qty to add
        wait.until(ExpectedConditions.elementToBeClickable(productModel.getQuantity()));
        productModel.getQuantity().clear();
        productModel.getQuantity().sendKeys(ExcelUtils.getDataMap().get("qty"));

        productModel.selectSwatchesIfConfigProduct(wait);
        productModel.getAddToCartButton().click();

        // Navigate to the Checkout page
        wait.until(ExpectedConditions.visibilityOf(productModel.getSuccessMessage()));
        headerModel.getCartIcon().click();
        minicartModel.getViewCart().click();

        wait.until(ExpectedConditions.textToBePresentInElement(cartModel.getGrandTotalLabel(), "Grand Total"));
        cartModel.getCheckoutButton().click();

        // Verify if Navigated to Login page
        wait.until(ExpectedConditions.visibilityOf(loginModel.getSignInTitle()));
        Assert.assertTrue(driver.getCurrentUrl().contains("/customer/account/login"));
        Loggers.getLogger().info("Redirected to Login Page");
        ExtentReport.getExtentNode().info("Redirected to Login Page");

        checkoutModel.preCheckoutLogin(loginModel);

        // Verify if Navigated to checkout shipping page
        wait.until(ExpectedConditions.elementToBeClickable(checkoutModel.getContinuePayment()));
        Assert.assertTrue(driver.getCurrentUrl().contains("/checkout/#shipping"));

        // Verify Login and Add Address
        try {
            Assert.assertFalse(checkoutModel.getSignInLink().isDisplayed());

            Loggers.getLogger().info("Logged into the User Account successfully");
            ExtentReport.getExtentNode().pass("Logged into the User Account successfully");
        } catch (Exception e) {
            if (!checkoutModel.getSelectedShip().isDisplayed()) {
                checkoutModel.addShippingAddress();
            }

            Loggers.getLogger().error("Shipping Address entered/selected");
            ExtentReport.getExtentNode().fail("Shipping Address entered/selected");
        }

        // Navigate to Checkout payment page
        checkoutModel.getContinuePayment().click();

        wait.until(ExpectedConditions.visibilityOfAllElements(checkoutModel.getPaymentMethods()));
        try {
            Assert.assertTrue(driver.getCurrentUrl().contains("/checkout/#payment"));
        } catch (Exception e) {
            driver.navigate().refresh();
            wait.until(ExpectedConditions.visibilityOfAllElements(checkoutModel.getPaymentMethods()));
            Assert.assertTrue(driver.getCurrentUrl().contains("/checkout/#payment"));
        }

        // Place Order
        checkoutModel.selectPaymentMethod();
        executor.executeScript("arguments[0].scrollIntoView(true);", checkoutModel.getPaymentMethodLabels().get(0));
        wait.until(ExpectedConditions.elementToBeClickable(checkoutModel.getPlaceOrder()));
        checkoutModel.getPlaceOrder().click();

        // Verify Order Placed Successfully or not
        wait.until(ExpectedConditions.visibilityOf(successModel.getOrderLink()));
        Assert.assertTrue(driver.getCurrentUrl().contains("checkout/onepage/success/"));

        ExcelUtils.excelConfigure(WEBSITE_SAMPLE_DATA);
        ExcelUtils.getRowData(Integer.parseInt(Objects.requireNonNull(Property.getProperty("testRow"))));
        Assert.assertEquals(successModel.getSuccessTitle().getText(), ExcelUtils.getDataMap().get("success_title"));

        Loggers.getLogger().info("Order Placed Successfully");
        ExtentReport.getExtentNode().pass("Order Placed Successfully");

        String orderLink = successModel.getOrderLink().getAttribute("href");
        String orderId = successModel.getOrderId().getText();

        // Verify the Order Link and the Order Id
        successModel.getOrderLink().click();
        Assert.assertEquals(driver.getCurrentUrl(), orderLink);
        Assert.assertTrue(orderDetailModel.getOrderTitle().getText().contains(orderId));

        Loggers.getLogger().info("Order Id and Link verified");
        ExtentReport.getExtentNode().pass("Order Id and Link verified");

    }

}
