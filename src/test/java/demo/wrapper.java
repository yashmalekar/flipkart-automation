package demo;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class wrapper {
    private WebDriver driver;
    private WebDriverWait wait;
    private Duration timeOut;

    public wrapper(WebDriver driver,Duration timeOut){
        this.driver=driver;
        this.timeOut=timeOut;
        wait = new WebDriverWait(driver, timeOut);
    }

    public boolean advanceSendKeys(By locator,String keysToSend){
        try{
            WebElement elem = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            elem.clear();
            elem.sendKeys(keysToSend);
            elem.sendKeys(Keys.ENTER);
            return true;
        }
        catch(Exception e){
            System.out.println("Element is not Present");
            return false;
        }
    }

    public boolean advanceClick(By locator){
        try{
            WebElement elem = wait.until(ExpectedConditions.elementToBeClickable(locator));
            elem.click();
            return true;
        }
        catch(Exception e){
            System.out.println("Element is not clickable or not present");
            return false;
        }
    }

    public void highestReviews(List<Integer> reviewNo){
        for(int i=0;i<5;i++){
            String number = Integer.toString(reviewNo.get(i));
            if(reviewNo.get(i)>999){
                number = number.substring(0,1)+","+number.substring(1);
            }
            List<WebElement> elem = driver.findElements(By.xpath("//span[contains(text(),'"+number+"')]/parent::div/preceding-sibling::a"));
            String url = elem.get(0).findElement(By.xpath(".//img")).getAttribute("src");
            String title = elem.get(1).getText();
            System.out.println(i+1+" Title:- " + title);
            System.out.println(i+1+" Image Url:- " + url);
            System.out.println();
    }
}
}