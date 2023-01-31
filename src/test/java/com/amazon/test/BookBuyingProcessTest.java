package com.amazon.test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class BookBuyingProcessTest {
    WebDriver driver;

    @BeforeMethod
    public void setUp() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        //driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        // 1.Navigate to official Amazon Site
        driver.get("https://www.amazon.com/");
    }

    @Test
    public void testBuy(){

        String amount = "$0.00";
        int setQty = 2;

        // 2.Select 'Books' from the Category list
        Select se = new Select(driver.findElement(By.xpath("//*[@id='searchDropdownBox']")));
        se.selectByVisibleText("Books");

        // 3. Search for the search term "Automation"
        driver.findElement(By.id("twotabsearchtextbox")).sendKeys("Automation");
        driver.findElement(By.id("nav-search-submit-button")).click();

        // 4. Select the Customer Reviews as "4 Stars & Up"
        driver.findElement(By.xpath("//div[@id= 'reviewsRefinements']//child::li//i[@class = 'a-icon a-icon-star-medium a-star-medium-4']")).click();

        // 5. Select Language as "English"
        driver.findElement(By.xpath("//div[@class = 'a-checkbox a-checkbox-fancy s-navigation-checkbox aok-float-left']//following-sibling::span[contains(text(),'English')]")).click();
        //Thread.sleep(6000);
        waitUntilNextElementAppears(By.xpath("//div[@cel_widget_id='MAIN-SEARCH_RESULTS-2']//h2//a//span"),10);

        // 6. Get the name of the second item from the Product List page
        String productNameInList = driver.findElement(By.xpath("//div[@cel_widget_id='MAIN-SEARCH_RESULTS-2']//h2//a//span")).getText();
        waitUntilNextElementAppears(By.xpath("//div[@cel_widget_id='MAIN-SEARCH_RESULTS-2']//h2//a"),10);
        //Thread.sleep(6000);
        System.out.println(productNameInList);

        // 7. Click the second item from the Product List page and navigate to the Product Detail page
        driver.findElement(By.xpath("//div[@cel_widget_id='MAIN-SEARCH_RESULTS-2']//h2//a")).click();
        //Thread.sleep(4000);
        //waitUntilNextElementAppears(By.xpath("//li[@class = 'swatchElement unselected resizedSwatchElement']//span[@class = 'a-button-inner']"),10);

        //Click the Paperback type
        driver.findElement(By.xpath("//li[@class = 'swatchElement unselected resizedSwatchElement']//span[@class = 'a-button-inner']")).click();
        driver.findElement(By.xpath("//li[@class = 'swatchElement unselected resizedSwatchElement']//span[@class = 'a-button-inner']")).click();
        waitUntilNextElementAppears(By.xpath("//div[@id = 'corePrice_feature_div']//span[@class = 'a-offscreen'] | //span[@id = 'price']"),10);

        // 8. Get the Unit Price of the item displayed in the Product Details Page
        String unitPrice = driver.findElement(By.xpath("//div[@id = 'corePrice_feature_div']//span[@class = 'a-offscreen'] | //span[@id = 'price']")).getText();
        System.out.println(unitPrice);

        // 9. Verify whether the item name of the Product Detail page is same as the item name obtained from the Product List page in step 5
        String productNameInDetailPage = driver.findElement(By.xpath("//span[@id = 'productTitle']")).getText();
        Assert.assertEquals(productNameInList,productNameInDetailPage,"Title is correct");
        System.out.println(driver.findElement(By.xpath("//span[@id = 'productTitle']")).getText());

        // 10. Set the Quantity to 2
        //Click on the item count dropdown
        driver.findElement(By.xpath("//*[@id = 'a-autoid-0']")).click();
        //Thread.sleep(3000);

        //Select the quantity as 2
        WebElement setQuantity = driver.findElement(By.xpath("//a[@id = 'quantity_1']"));
        setQuantity.click();

        // 11. Click on Add to Cart
        driver.findElement(By.id("add-to-cart-button")).click();
        //Thread.sleep(3000);

        // 12. Click on Go to Cart
        driver.findElement(By.id("sw-gtc")).click();


        // 13. Verify whether the cart details are correct (Item name, Quantity and Total Price)
        String productNameInCart = driver.findElement(By.xpath("//span[@class = 'a-truncate sc-grid-item-product-title a-size-base-plus']//span[@class = 'a-truncate-cut']")).getText();
        float quantityInCart = Integer.parseInt(driver.findElement(By.xpath("//span[@class = 'a-dropdown-prompt']")).getText().trim());
        String totalPriceInCart = driver.findElement(By.xpath("//div[@data-name = 'Subtotals']/span[@id = 'sc-subtotal-amount-activecart']")).getText();

        // 13.1 Verify the Product Name displayed in the cart
        Assert.assertEquals(productNameInDetailPage,productNameInCart);

        // 13.2 Verify the quantity in cart and set quantity
        Assert.assertEquals(quantityInCart,setQty);

        // From unitPrice extract only the digits
        int itemPrice = Integer.parseInt(unitPrice.replaceAll("[^0-9]",""));
        System.out.println(itemPrice);

        String itemp = unitPrice.replaceAll("[^0-9]","");

        //From totalPriceInCart extract only the digits
        int totalItemPrice = Integer.parseInt(totalPriceInCart.replaceAll("[^0-9]",""));

        //Calculate the Total price from unit price
         float multipliedTotalPrice = itemPrice*2;

         // 13.3 Verify the Total Price
        Assert.assertEquals(totalItemPrice,multipliedTotalPrice);

        System.out.println(productNameInCart);
        System.out.println(quantityInCart);
        System.out.println(totalPriceInCart);

        // 14. Clear the cart and verify the total amount is equal to $0.00
        //Click the delete button to clear the cart
        driver.findElement(By.xpath("//input[@data-action = 'delete']")).click();

        waitUntilNextElementAppears(By.xpath("//div[@data-name = 'Subtotals']/span[@id = 'sc-subtotal-amount-activecart']"),10);

        String totalAmount = driver.findElement(By.xpath("//div[@data-name = 'Subtotals']/span[@id = 'sc-subtotal-amount-activecart']")).getText().trim();
        //Thread.sleep(3000);
        System.out.println(totalAmount);

        Assert.assertEquals(totalAmount,amount,"values match");

        driver.quit();

    }

    private WebElement waitUntilNextElementAppears(By locator, int timeOut){
        WebElement element = new WebDriverWait(driver,timeOut).until(ExpectedConditions.presenceOfElementLocated(locator));
        return element;
    }
}
