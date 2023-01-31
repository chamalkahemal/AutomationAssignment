package com.amazon;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Home {
    WebDriver driver;

    public void openBrowser(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    // 1.Navigate to official Amazon Site
    public void navigateToURL(){
        driver.get("https://www.amazon.com/");
    }

    // 2.Select 'Books' from the Category list
    public void selectBooks(){
        Select se = new Select(driver.findElement(By.xpath("//*[@id='searchDropdownBox']")));
        se.selectByVisibleText("Books");
    }

    // 3. Search for the search term "Automation"
    public void searchAutomation(){
        driver.findElement(By.id("twotabsearchtextbox")).sendKeys("Automation");
        driver.findElement(By.id("nav-search-submit-button")).click();
    }

    // 4. Select the Customer Reviews as "4 Stars & Up"
    public void clickCustomerReview(){
        driver.findElement(By.xpath("//div[@id= 'reviewsRefinements']//child::li//i[@class = 'a-icon a-icon-star-medium a-star-medium-4']")).click();
    }

    // 5. Select Language as "English"
    public void selectLanguage(){

        driver.findElement(By.xpath("//div[@class = 'a-checkbox a-checkbox-fancy s-navigation-checkbox aok-float-left']//following-sibling::span[contains(text(),'English')]")).click();
        //Thread.sleep(6000);
        waitUntilNextElementAppears(By.xpath("//div[@cel_widget_id='MAIN-SEARCH_RESULTS-2']//h2//a//span"),10);
    }



    private WebElement waitUntilNextElementAppears(By locator, int timeOut){
        WebElement element = new WebDriverWait(driver,timeOut).until(ExpectedConditions.presenceOfElementLocated(locator));
        return element;
    }
}
