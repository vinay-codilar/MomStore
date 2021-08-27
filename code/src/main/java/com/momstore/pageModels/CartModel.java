package com.momstore.pageModels;

import com.momstore.extent_reports.ExtentReport;
import com.momstore.loggers.Loggers;
import com.momstore.utilities.ExcelUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CartModel {
    private WebDriver driver;
    private ArrayList<String> cartProductName;
    private ArrayList<String> cartSwatch;
    private ArrayList<String> cartFinalPrice;
    private ArrayList<String> cartSubtotalPrice;
    private ArrayList<String> cartProductQty;

    @FindBy(css = ".cart.item .item-info")
    private List<WebElement> productList;
    @FindBy(css = ".col.item dd")
    private List<WebElement> swatchOptions;
    @FindBy(css = ".price .cart-price .price")
    private List<WebElement> productFinalPrice;
    @FindBy(css = ".subtotal .cart-price .price")
    private List<WebElement> productSubtotalPrice;
    @FindBy(css = ".control.qty .qty")
    private List<WebElement> productQty;
    @FindBy(css = "h1 span")
    private WebElement cartTitle;
    @FindBy(css = "#shopping-cart-table tbody tr[class='item-info'] td div strong a")
    private List<WebElement> productNames;
    @FindBy(css = ".item .checkout")
    private WebElement checkoutButton;
    @FindBy(css = ".grand.totals th strong")
    private WebElement grandTotalLabel;

    /**
     * @param driver - WebDriver
     */
    public CartModel(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    /**
     * @return WebElement
     */
    public WebElement getCartTitle() {
        return cartTitle;
    }

    /**
     * @return List<WebElement>
     */
    public List<WebElement> getProductNames() {
        return productNames;
    }

    /**
     * @return WebElement
     */
    public WebElement getCheckoutButton() {
        return checkoutButton;
    }

    /**
     * @return WebElement
     */
    public WebElement getGrandTotalLabel() {
        return grandTotalLabel;
    }

    /**
     * Fetching the Cart product details
     */
    public void fetchProductDetails() {
        WebDriverWait wait = new WebDriverWait(driver, 5);

        wait.until(ExpectedConditions.visibilityOfAllElements(productList));
        this.cartSwatch = new ArrayList<>();
        this.cartProductName = new ArrayList<>();
        this.cartFinalPrice = new ArrayList<>();
        this.cartSubtotalPrice = new ArrayList<>();
        this.cartProductQty = new ArrayList<>();

        for (WebElement element : productList) {

            // Fetching the Product name
            for (WebElement productObject : productNames) {
                this.cartProductName.add(productObject.getAttribute("innerHTML"));
            }

            // Fetching the Product Swatch options
            for (WebElement swatch : swatchOptions) {
                cartSwatch.add(swatch.getText());
            }

            // Fetching Product Final Price
            for (WebElement final_price : productFinalPrice) {
                this.cartFinalPrice.add(final_price.getText());
            }

            // Fetching the Product Subtotal
            for (WebElement subtotal : productSubtotalPrice) {
                this.cartSubtotalPrice.add(subtotal.getText());
            }

            // Fetching the Product qty
            for (WebElement qty : productQty) {
                this.cartProductQty.add(qty.getAttribute("value"));
            }
        }
        Loggers.getLogger().info("Fetched the Product Details from Cart");
        ExtentReport.getExtentNode().pass("Fetched the Product Details from Cart");
    }

    /**
     * Verify the Products added to Cart
     * @param driver - WebDriver
     * @return Boolean
     */
    public boolean verifyProducts(WebDriver driver) {
        HeaderModel headerModel = new HeaderModel(driver);
        MinicartModel minicartModel = new MinicartModel(driver);

        // Navigate to Cart page
        headerModel.getCartIcon().click();
        minicartModel.getViewCart().click();

        String productName = ExcelUtils.getDataMap().get("product_name");

        // Select the product passed from Listing page
        Iterator<WebElement> iter = getProductNames().iterator();
        while (iter.hasNext()) {
            String prodName = iter.next().getText();
            if (prodName.equals(productName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return String
     */
    public ArrayList<String> getCartProductName() {
        return cartProductName;
    }

    /**
     * @return ArrayList<String>
     */
    public ArrayList<String> getCartSwatch() {
        return cartSwatch;
    }

    /**
     * @return String
     */
    public ArrayList<String> getCartFinalPrice() {
        return cartFinalPrice;
    }

    /**
     * @return String
     */
    public ArrayList<String> getCartSubtotalPrice() {
        return cartSubtotalPrice;
    }

    /**
     * @return String
     */
    public ArrayList<String> getCartProductQty() {
        return cartProductQty;
    }
}
