package com.amazon;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductList {
    WebDriver driver;

    // 6. Get the name of the second item from the Product List page
    public void getSecondItemName (){
        String productNameInList = driver.findElement(By.xpath("//div[@cel_widget_id='MAIN-SEARCH_RESULTS-2']//h2//a//span")).getText();
        waitUntilNextElementAppears(By.xpath("//div[@cel_widget_id='MAIN-SEARCH_RESULTS-2']//h2//a"),10);
        //Thread.sleep(6000);
        System.out.println(productNameInList);
    }

    // 7. Click the second item from the Product List page and navigate to the Product Detail page
    public void selectSecondItem(){
        driver.findElement(By.xpath("//div[@cel_widget_id='MAIN-SEARCH_RESULTS-2']//h2//a")).click();
        //Thread.sleep(4000);
        waitUntilNextElementAppears(By.xpath("//li[@class = 'swatchElement unselected resizedSwatchElement']//span[@class = 'a-button-inner']"),10);
    }



    private WebElement waitUntilNextElementAppears(By locator, int timeOut){
        WebElement element = new WebDriverWait(driver,timeOut).until(ExpectedConditions.presenceOfElementLocated(locator));
        return element;
    }
}
