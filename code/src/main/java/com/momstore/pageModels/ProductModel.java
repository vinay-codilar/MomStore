package com.momstore.pageModels;

import com.momstore.extent_reports.ExtentReport;
import com.momstore.loggers.Loggers;
import com.momstore.pickers.RandomPicker;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductModel {
    private WebDriver driver;
    private ArrayList<String> productOldPrice;
    private ArrayList<String> productFinalPrice;
    private ArrayList<String> productQuantity;
    private ArrayList<String> productSwatches;
    private ArrayList<String> productName;

    @FindBy(xpath = "//h1/span")
    private WebElement pdpProductName;
    @FindBy(css = ".product-info-main [data-price-type='oldPrice'] span")
    private WebElement pdpOldPrice;
    @FindBy(css = ".product-info-main [data-price-type='finalPrice'] span")
    private WebElement pdpFinalPrice;
    @FindBy(css = "form #product-options-wrapper")
    private WebElement productOptions;
    @FindBy(css = ".swatch-option.text")
    private List<WebElement> swatchesSizeList;
    @FindBy(css = ".swatch-option.color")
    private List<WebElement> swatchesColorList;
    @FindBy(id = "qty")
    private WebElement quantity;
    @FindBy(id = "product-addtocart-button")
    private WebElement addToCartButton;
    @FindBy(xpath = "//div[@data-ui-id='message-error']/div")
    private WebElement errorMessage;
    @FindBy(xpath = "//div[@data-ui-id='message-success']/div")
    private WebElement successMessage;
    @FindBy(css = "div[class='mage-error']")
    private WebElement swatchReqErr;

    /**
     * @param driver - Constructor
     */
    public ProductModel(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    /**
     * Select the swatches if Configurable Products are selected
     */
    public ArrayList<String> selectSwatchesIfConfigProduct(WebDriverWait wait) {
        this.productSwatches = new ArrayList<>();

        try {
            wait.until(ExpectedConditions.visibilityOf(getProductOptions()));
            if (getProductOptions().isDisplayed()) {
                // Logging and Reporting
                Loggers.getLogger().info("Selected a Configurable Product");
                ExtentReport.getExtentNode().info("Selected a Configurable Product");

                JavascriptExecutor executor = (JavascriptExecutor) driver;
                executor.executeScript("arguments[0].scrollIntoView();", getPdpProductName());
                Thread.sleep(2000);

                wait.until(ExpectedConditions.elementToBeClickable(getSwatchesSizeList().get(0)));
                System.out.println("Size: " + getSwatchesSizeList().size());
                if (getSwatchesSizeList().size() > 0) {
                    int sizeOption = RandomPicker.numberPicker(getSwatchesSizeList().size());
                    WebElement sizeElement = getSwatchesSizeList().get(0);

                    // Selecting the Swatches
//                    executor.executeScript("arguments[0].click();", sizeElement);
                    sizeElement.click();
                    this.productSwatches.add(sizeElement.getAttribute("data-option-label"));

                    // Logging and Reporting
                    Loggers.getLogger().info("Swatch '" + getProductSwatches().get(0) + "' is selected");
                    ExtentReport.getExtentNode().info("Swatch '" + getProductSwatches().get(0) + "' is selected");
                }

                wait.until(ExpectedConditions.elementToBeClickable(getSwatchesColorList().get(0)));
                System.out.println("Color: " + getSwatchesColorList().size());
                if (getSwatchesColorList().size() > 0) {
                    int colorOption = RandomPicker.numberPicker(getSwatchesColorList().size());
                    WebElement colorElement = getSwatchesColorList().get(0);

                    // Selecting the Swatches
//                    executor.executeScript("arguments[0].click();", colorElement);
                    colorElement.click();
                    this.productSwatches.add(colorElement.getAttribute("data-option-label"));

                    // Logging and Reporting
                    Loggers.getLogger().info("Swatch '" + getProductSwatches().get(0) + "' is selected");
                    ExtentReport.getExtentNode().info("Swatch '" + getProductSwatches().get(0) + "' is selected");
                }
                return getProductSwatches();
            }
        } catch (Exception e) {
            Loggers.getLogger().info("Selected a Simple Product");
            ExtentReport.getExtentNode().info("Selected a Simple Product");
        }
        return new ArrayList<String>(Arrays.asList("simple"));
    }

    /**
     * Creating the ArrayList objects
     */
    public void createVariableObjects() {
        this.productName = new ArrayList<>();
        this.productOldPrice = new ArrayList<>();
        this.productFinalPrice = new ArrayList<>();
        this.productQuantity = new ArrayList<>();
    }

    /**
     * @return void
     */
    public void setProductFinalPrice() {
        productFinalPrice.add(getPdpFinalPrice().getText());
    }

    /**
     * Create an object of Product Swatches
     */
    public void setProductSwatches() {
        this.productSwatches = new ArrayList<>();
    }

    /**
     * @return String
     */
    public ArrayList<String> getProductOldPrice() {
        return productOldPrice;
    }

    /**
     * @param productOldPrice - String
     */
    public void setProductOldPrice(String productOldPrice) {
        this.productOldPrice.add(productOldPrice);
    }

    /**
     * @return String
     */
    public ArrayList<String> getProductFinalPrice() {
        return productFinalPrice;
    }

    /**
     * @return String
     */
    public ArrayList<String> getProductQuantity() {
        return productQuantity;
    }

    /**
     * @return ArrayList<String>
     */
    public ArrayList<String> getProductSwatches() {
        return productSwatches;
    }

    /**
     * @return String
     */
    public ArrayList<String> getProductName() {
        return productName;
    }

    /**
     * @return WebElement
     */
    public WebElement getPdpProductName() {
        return pdpProductName;
    }

    /**
     * @return WebElement
     */
    public WebElement getPdpOldPrice() {
        return pdpOldPrice;
    }

    /**
     * @return WebElement
     */
    public WebElement getPdpFinalPrice() {
        return pdpFinalPrice;
    }

    /**
     * @return WebElement
     */
    public WebElement getProductOptions() {
        return productOptions;
    }

    /**
     * @return List<WebElement>
     */
    public List<WebElement> getSwatchesSizeList() {
        return swatchesSizeList;
    }

    /**
     * @return List<WebElement>
     */
    public List<WebElement> getSwatchesColorList() {
        return swatchesColorList;
    }

    /**
     * @return WebElement
     */
    public WebElement getQuantity() {
        return quantity;
    }

    /**
     * @return WebElement
     */
    public WebElement getAddToCartButton() {
        return addToCartButton;
    }

    /**
     * @return WebElement
     */
    public WebElement getErrorMessage() {
        return errorMessage;
    }

    /**
     * @return WebElement
     */
    public WebElement getSuccessMessage() {
        return successMessage;
    }

    /**
     * @return WebElement
     */
    public WebElement getSwatchReqErr() {
        return swatchReqErr;
    }
}
