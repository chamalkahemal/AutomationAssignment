package com.amazon;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductDetails {
    WebDriver driver;

    // 8. Get the Unit Price of the item displayed in the Product Details Page
    public void getUnitPrice(){

        //Click the Paperback type
        driver.findElement(By.xpath("//li[@class = 'swatchElement unselected resizedSwatchElement']//span[@class = 'a-button-inner']")).click();
        waitUntilNextElementAppears(By.xpath("//div[@id = 'corePrice_feature_div']//span[@class = 'a-offscreen'] | //span[@id = 'price']"),10);

        String unitPrice = driver.findElement(By.xpath("//div[@id = 'corePrice_feature_div']//span[@class = 'a-offscreen'] | //span[@id = 'price']")).getText();
        //Thread.sleep(3000);
        System.out.println(unitPrice);
        //Thread.sleep(2000);
    }


    private WebElement waitUntilNextElementAppears(By locator, int timeOut){
        WebElement element = new WebDriverWait(driver,timeOut).until(ExpectedConditions.presenceOfElementLocated(locator));
        return element;
    }
}
