package pageobjects;

import AbstractComponents.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CheckoutPage extends AbstractComponent {
    WebDriver driver;

    @FindBy(css ="[placeholder='Select Country']" )
    WebElement country;

    @FindBy(css = ".ng-star-inserted:nth-of-type(4)")
    WebElement selectCountry;

    @FindBy(css = ".action__submit" )
    WebElement submitButton;

    By results = By.cssSelector(".ta-results");
    public CheckoutPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void selectCountry(String countryName){
        Actions a = new Actions(driver);
        a.sendKeys(country, countryName).build().perform();

        waitForElementToAppear(results);
        // select the United States
        selectCountry.click();
    }

    public ConfirmationPage submitOrder(){
        submitButton.click();
        return new ConfirmationPage(driver);
    }

}
