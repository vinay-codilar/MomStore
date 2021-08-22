package com.momstore.pageModels;

import com.momstore.extent_reports.ExtentReport;
import com.momstore.loggers.Loggers;
import com.momstore.pickers.RandomPicker;
import com.momstore.utilities.ExcelUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;
import java.util.List;

public class ListingModel {
    private WebDriver driver;
    private String list_product_name;
    private String list_product_final_price;
    private String list_product_old_price;
    private int product_id;

    @FindBy(css = ".product-items .product-item")
    private List<WebElement> productList;
    @FindBy(css = ".product-item-link")
    private List<WebElement> productItemLink;
    @FindBy(css = "[data-price-type='finalPrice'] span")
    private List<WebElement> finalPriceList;
    @FindBy(xpath = "//span[@data-price-type='oldPrice']/span")
    private List<WebElement> oldPriceList;
    @FindBy(xpath = "//button[@type='submit']")
    private List<WebElement> submitButton;
    @FindBy(xpath = "//div[@data-ui-id='message-success']/div/a")
    private WebElement addSuccess;

    /**
     * @param driver - Constructor
     */
    public ListingModel(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    /**
     * Select the product from the Listing
     * @param productModel - ProductModel
     */
    public void selectProduct(ProductModel productModel) {
        String productName = ExcelUtils.getDataMap().get("product_name");
        Actions act = new Actions(driver);
        List<WebElement> prodList = getProductList();

        // Selecting the product from Listing page
        for (WebElement element : prodList) {
            if (element.getText().equals(productName)) {
                act.moveToElement(element).click().build().perform();
                break;
            }
        }

        // Click on Add to Cart
        productModel.getAddToCartButton().click();
    }

    /**
     * @return String
     */
    public String getListProductName() {
        return list_product_name;
    }

    /**
     * @return String
     */
    public String getListProductFinalPrice() {
        return list_product_final_price;
    }

    /**
     * @return String
     */
    public String getListProductOldPrice() {
        return list_product_old_price;
    }

    /**
     * @return Integer
     */
    public int getProductId() {
        return product_id;
    }

    /**
     * @return List<WebElement>
     */
    public List<WebElement> getProductList() {
        return productList;
    }

    /**
     * @return List<WebElement>
     */
    public List<WebElement> getProductItemLink() {
        return productItemLink;
    }

    /**
     * @return List<WebElement>
     */
    public List<WebElement> getFinalPriceList() {
        return finalPriceList;
    }

    /**
     * @return List<WebElement>
     */
    public List<WebElement> getOldPriceList() {
        return oldPriceList;
    }

    /**
     * @return List<WebElement>
     */
    public List<WebElement> getSubmitButton() {
        return submitButton;
    }

    /**
     * @return List<WebElement>
     */
    public WebElement getAddSuccess() {
        return addSuccess;
    }
}
