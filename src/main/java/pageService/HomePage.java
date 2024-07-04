package pageService;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    WebDriver driver;

    @FindBy(name = "searchValue")
    WebElement searchInputArea;

    @FindBy(xpath = "//button[@type='submit']")
    WebElement searchButton;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void enterSearchText(String searchText){
        searchInputArea.sendKeys(searchText);
    }

    public SearchPage clickSearchButton(){
        searchButton.click();
        return new SearchPage(driver);
    }
}
