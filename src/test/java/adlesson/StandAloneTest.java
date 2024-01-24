package adlesson;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class StandAloneTest {
    public static void main(String[] args) {
        var productName = "ZARA COAT 3";
        var productName2 = "ADIDAS ORIGINAL";
        var productName3 = "IPHONE 13 PRO";
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("https://rahulshettyacademy.com/client");

        driver.findElement(By.cssSelector("#userEmail")).sendKeys("Jordy@gmail.com");
        driver.findElement(By.cssSelector("#userPassword")).sendKeys("Jordy2468");
        driver.findElement(By.id("login")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));

        //get a list of all products

        List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));

        //using to java streams to iterate through list
        //in this case we are looking for a specific item in the list and returning it
       WebElement prod =  products.stream().filter(product ->
                product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst().orElse(null);

       //add our item to the cart
        prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();




        //Using Explicit wait vs Thread.sleep

        //wait for toast to appear
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));

        //wait for animation to disappear
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));

        driver.findElement(By.cssSelector("[routerlink*='cart']")).click();


        //validate the list of cart items
       List <WebElement> cartProds =  driver.findElements(By.xpath("//*[@class='cartSection']/h3"));
       var match = cartProds.stream().anyMatch(cartProduct -> cartProduct.getText().equalsIgnoreCase(productName) );
        Assert.assertTrue(match);

        //click on checkout
        driver.findElement(By.xpath("//*[@class='totalRow']/button")).click();

        Actions a = new Actions(driver);
        a.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")), "United").build().perform();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
        // select the United States
        a.click(driver.findElement(By.cssSelector(".ng-star-inserted:nth-of-type(4)"))).build().perform();
        //click place order
        a.click(driver.findElement(By.cssSelector(".action__submit"))).build().perform();


        var confirmMess = driver.findElement(By.cssSelector(".hero-primary")).getText();

        Assert.assertTrue(confirmMess.equalsIgnoreCase("THANKYOU FOR THE ORDER."));

    }
}
