package tests;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageService.HomePage;
import pageService.SearchPage;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class HamroBazaarTest {
    WebDriver driver;

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\sujanmanandhar\\Desktop\\SeleniumTask\\Verisk automation Task\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(2000, TimeUnit.MILLISECONDS);
        driver.get("https://hamrobazaar.com/");
    }

    @Test
    public void test() throws IOException, InterruptedException {
        HomePage homeObj = new HomePage(driver);
        homeObj.enterSearchText("Monitor");
        SearchPage searchObj = homeObj.clickSearchButton();
        searchObj.enterLocation("New Road");
        searchObj.selectLocation();
        searchObj.slideRange();
        searchObj.filter();
        searchObj.sortDropdown();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfAllElements(searchObj.getProductTitleList()));
        List<String> productTitles = new ArrayList<>();
        List<String> productDescriptions = new ArrayList<>();
        List<String> productPrices = new ArrayList<>();
        List<String> productConditions = new ArrayList<>();
        List<String> productAdPostedDates = new ArrayList<>();
        List<String> productSellerNames = new ArrayList<>();

        int maxData = 50;

        while (true) {
            productTitles.addAll(searchObj.getProductTitleList().stream().map(WebElement::getText).collect(Collectors.toList()));
            productPrices.addAll(searchObj.getProductPriceList().stream().map(WebElement::getText).collect(Collectors.toList()));
            productDescriptions.addAll(searchObj.getProductDescriptionList().stream().map(WebElement::getText).collect(Collectors.toList()));
            productAdPostedDates.addAll(searchObj.getProductAdPostedDateList().stream().map(WebElement::getText).collect(Collectors.toList()));
            productConditions.addAll(searchObj.getProductConditionList().stream().map(WebElement::getText).collect(Collectors.toList()));
            productSellerNames.addAll(searchObj.getProductSellerNameList().stream().map(WebElement::getText).collect(Collectors.toList()));

            if (productTitles.size() >= maxData) {
                break;
            }

            // Scroll down
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
            Thread.sleep(5000);
            productTitles.addAll(searchObj.getProductTitleList().stream().map(WebElement::getText).collect(Collectors.toList()));
            productDescriptions.addAll(searchObj.getProductDescriptionList().stream().map(WebElement::getText).collect(Collectors.toList()));
            productPrices.addAll(searchObj.getProductPriceList().stream().map(WebElement::getText).collect(Collectors.toList()));
            productAdPostedDates.addAll(searchObj.getProductAdPostedDateList().stream().map(WebElement::getText).collect(Collectors.toList()));
            productConditions.addAll(searchObj.getProductConditionList().stream().map(WebElement::getText).collect(Collectors.toList()));
            productSellerNames.addAll(searchObj.getProductSellerNameList().stream().map(WebElement::getText).collect(Collectors.toList()));
        }
        Thread.sleep(2000);
        writeDataToCSV(productTitles, productDescriptions, productPrices, productConditions, productAdPostedDates, productSellerNames);
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

    private static void writeDataToCSV(List<String> names, List<String> descriptions, List<String> prices,
                                       List<String> condition,List<String> adPostedDate,
                                       List<String> productSeller) throws IOException {
        FileWriter csvWriter = new FileWriter("C:\\Users\\sujanmanandhar\\Desktop\\SeleniumTask\\Verisk automation Task\\output.csv");
        csvWriter.append("Name,Description,Price,Condition,AdPostedDate,SellerName\n");
        for (int i = 0; i < names.size(); i++) {
            csvWriter.append(names.get(i))
                    .append(",")
                    .append(descriptions.get(i))
                    .append(",")
                    .append(prices.get(i))
                    .append(",")
                    .append(condition.get(i))
                    .append(",")
                    .append(adPostedDate.get(i))
                    .append(",")
                    .append(productSeller.get(i))
                    .append("\n");
        }
        csvWriter.flush();
        csvWriter.close();
    }
}

