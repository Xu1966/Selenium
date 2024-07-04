package pageService;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class SearchPage {
    WebDriver driver;

    @FindBy(css = "aside[class=\"aside--ad \"] input[name='location']")
    WebElement inputLocation;

    @FindBy(xpath = "//div[contains(concat(' ', normalize-space(@class), ' '), 'place--suggestions')]//li[2]")
    WebElement selectLocation;

    @FindBy(xpath = "//aside[@class='aside--ad ']//button")
    WebElement filterButton;

    @FindBy(xpath = "//aside[@class='aside--ad ']//input[@type='range'] ")
    WebElement slider;

    @FindAll(@FindBy(css = "h2[class='product-title']"))
    List<WebElement> productTitle;

    @FindAll(@FindBy(css = "p[class='description']"))
    List<WebElement> productDescription;

    @FindAll(@FindBy(css = "span[class='regularPrice']"))
    List<WebElement> productPrice;

    @FindAll(@FindBy(css = "span[class='condition']"))
    List<WebElement> productCondition;

    @FindAll(@FindBy(css = "span[class='time']"))
    List<WebElement> productAdPostedDate;

    @FindAll(@FindBy(css = "span[class='username-fullname']"))
    List<WebElement> productSellerName;

    @FindBy(css = "aside[class='aside--ad '] select[name='sortParam']")
    WebElement sortDropdown;

    public SearchPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void enterLocation(String location) {
        inputLocation.sendKeys(location);
    }

    public void selectLocation() {
        selectLocation.click();
    }

    public void filter() {
        filterButton.click();
    }

    public void slideRange(){
        int minValue = Integer.parseInt(slider.getAttribute("aria-valuemin"));
        int maxValue = Integer.parseInt(slider.getAttribute("aria-valuemax"));
        int currentValue = Integer.parseInt(slider.getAttribute("aria-valuenow"));

        int desiredValue = 5000;

        int sliderWidth = slider.getSize().getWidth();
        double valueRange = maxValue - minValue;
        double pixelsPerValue = sliderWidth / valueRange;
        int xOffset = (int) ((desiredValue - currentValue) * pixelsPerValue);

        Actions move = new Actions(driver);
        move.clickAndHold(slider).moveByOffset(xOffset, 0).release().build().perform();
    }

    public List<WebElement> getProductTitleList(){
        return productTitle;
    }

    public List<WebElement> getProductDescriptionList(){
        return productDescription;
    }

    public List<WebElement> getProductPriceList(){
        return productPrice;
    }

    public List<WebElement> getProductConditionList(){
        return productCondition;
    }

    public List<WebElement> getProductAdPostedDateList(){
        return productAdPostedDate;
    }

    public List<WebElement> getProductSellerNameList(){
        return productSellerName;
    }

    public void sortDropdown() {
        sortDropdown.click();
        Select select = new Select(sortDropdown);
        select.selectByValue("3");
    }
}
