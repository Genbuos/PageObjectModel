package adlesson;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pageobjects.*;

import java.time.Duration;
import java.util.List;

public class SubmitOrderTest {
    static String productName = "ZARA COAT 3";
   static String productName2 = "ADIDAS ORIGINAL";
    String productName3 = "IPHONE 13 PRO";
   static String userEmail = "Jordy@gmail.com";
   static String userPw = "Jordy2468";
    public static void main(String[] args) {
        //optimized for Page Object Design Pattern

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        LandingPage landingPage = new LandingPage(driver);
        landingPage.goTo();
        ProductCatalogue productCatalogue = landingPage.loginApplication(userEmail, userPw);
        productCatalogue.addProductToCart(productName);
        productCatalogue.addProductToCart(productName2);
        CartPage cartPage =  productCatalogue.goToCartPage();

        var match = cartPage.verifyProductDisplay(productName);
        Assert.assertTrue(match);
       CheckoutPage checkoutPage = cartPage.goToCheckOut();
        checkoutPage.selectCountry("United");
        ConfirmationPage confirmationPage = checkoutPage.submitOrder();




        var confirmMess = confirmationPage.getConfirmMessage();
        Assert.assertTrue(confirmMess.equalsIgnoreCase("THANKYOU FOR THE ORDER."));

    }
}
