package demo;


import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestCases {
    ChromeDriver driver;
    WebDriverWait wait;
    boolean status;

    @BeforeTest
    public void startTest(){
        WebDriverManager.chromedriver().timeout(30).setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    @AfterTest
    public void endTest(){
        System.out.println("End Test: TestCases");
        driver.quit();
    }
    
    @Test(description = "Printing the count of Washing Machine with rating less than or equal to 4 stars.")
    public void testCase01(){
        driver.get("https://www.flipkart.com/");
        wrapper wrap = new wrapper(driver, Duration.ofSeconds(30));
        //Sending "Washing Machine" to Search TextBox using wrapper
        status = wrap.advanceSendKeys(By.xpath("//input[contains(@title,'Search')]"), "Washing Machine");
        Assert.assertTrue(status,"Failed to SendKeys to TextBox");
        //Using Explicit wait until page loads
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Popularity']")));
        //Clicking on Popularity sort using wrapper
        status = wrap.advanceClick(By.xpath("//div[text()='Popularity']"));
        Assert.assertTrue(status, "Failed to Click on Popularity");
        //Waiting until Popularity option is selected
        wait.until(ExpectedConditions.attributeContains(By.xpath("//div[text()='Popularity']"), "className", "_3LsR0e"));
        //locating ratings of washing machine
        List<WebElement> rating = driver.findElements(By.className("_3LWZlK"));
        int count = 0;
        for(WebElement rating1: rating){
            //Getting ratings text from element and Converting it from String to Double datatype
            double stars = Double.parseDouble(rating1.getText());
            if(stars<=4){
                count++;
            }
        }
        System.out.println("Items with rating less than or equal to 4 stars:- " + count);
    }

    @Test(description = "Printing the Titles and Discount % of Iphone")
    public void testCase02(){
        driver.get("https://www.flipkart.com/");
        wrapper wrap = new wrapper(driver, Duration.ofSeconds(30));
        //Sending "iPhone" to Search TextBox using wrapper
        status = wrap.advanceSendKeys(By.xpath("//input[contains(@title,'Search')]"), "iPhone");
        Assert.assertTrue(status, "Failed to SendKeys to textBox");
        //Using Explicit wait until page loads
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Popularity']")));
        //Locating Discounts and title of products
        List<WebElement> discounts = driver.findElements(By.className("_3Ay6Sb"));
        List<WebElement> titles = driver.findElements(By.xpath("//div[@class='_4rR01T' or @class='_1W9f5C']"));
            for(int i=0;i<discounts.size();i++){
            int number = Integer.parseInt(discounts.get(i).getText().replaceAll("[^\\d.]",""));
            //No Iphone was greater than 17% off so I have used 12.
            if(number>12){
                String text = titles.get(i).getText();
                System.out.println(text + " with " + number + "% discount");
            }
        }
    }

    @Test(description = "Printing the Titles and Image URLs with highest number of reviews")
    public void testCase03() throws InterruptedException{
        driver.get("https://www.flipkart.com/");
        wrapper wrap = new wrapper(driver, Duration.ofSeconds(30));
        //Sending "Coffee Mug" to Search TextBox using wrapper
        status = wrap.advanceSendKeys(By.xpath("//input[contains(@title,'Search')]"), "Coffee Mug");
        Assert.assertTrue(status, "Failed to SendKeys to textBox");
        //Using Explicit wait until page loads
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Popularity']")));
        //Applying filter '4★ & above'
        status = wrap.advanceClick(By.xpath("//div[contains(text(),'4')]/parent::label"));
        Assert.assertTrue(status, "Failed to Click on 4 star and above option");
        //Applying wait until page loads desired results;
        wait.until(ExpectedConditions.elementSelectionStateToBe(By.xpath("//div[contains(text(),'4')]/parent::label/input"), true));
        //Fetching review counts
        List<WebElement> reviews = driver.findElements(By.xpath("//span[@class='_2_R_DZ']"));
        //Converting review counts to integer
        List<Integer> reviewNo = new ArrayList<>();
        for(WebElement review:reviews){
            reviewNo.add(Integer.parseInt(review.getText().replaceAll("[(),]", "")));
        }
        //Sorting review counts to get highest numbers of reviews first
        Collections.sort(reviewNo,Collections.reverseOrder());
        //Fetching First five highest review count's Title and Image URL
        wrap.highestReviews(reviewNo);
    }
}
