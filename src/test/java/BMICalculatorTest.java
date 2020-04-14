import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class BMICalculatorTest {

    @Test
    public void normalCategory() {
        // Open https://healthunify.com/bmicalculator/
        // Enter Weight:
        // Enter Height:
        // Click Calculate
        // Validate Category
        // Close browser

        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("https://healthunify.com/bmicalculator/");
        driver.findElement(By.name("wg")).sendKeys("67");
        driver.findElement(By.name("ht")).sendKeys("173");
        driver.findElement(By.name("cc")).click();
        String category = driver.findElement(By.name("desc")).getAttribute("value");
        Assert.assertEquals(category, "Your category is Normal",
                "Your category doesn't match with reality");
        driver.quit();
    }

    @Test
    public void overweightCategory() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("https://healthunify.com/bmicalculator/");
        driver.findElement(By.name("wg")).sendKeys("85");
        driver.findElement(By.name("ht")).sendKeys("173");
        driver.findElement(By.name("cc")).click();
        String category = driver.findElement(By.name("desc")).getAttribute("value");
        Assert.assertEquals(category, "Your category is Overweight",
                "Your category matches with reality");
        driver.quit();
    }

    @Test
    public void underweightCategory() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("https://healthunify.com/bmicalculator/");
        Select dropdownWeight = new Select(driver.findElement(By.name("opt1")));
        dropdownWeight.selectByVisibleText("pounds");
        Select dropdownHeight = new Select(driver.findElement(By.name("opt2")));
        dropdownHeight.selectByVisibleText("6′");
        driver.findElement(By.name("wg")).clear();
        driver.findElement(By.name("wg")).sendKeys("60");
        driver.findElement(By.name("cc")).click();
        String category = driver.findElement(By.name("desc")).getAttribute("value");
        Assert.assertEquals(category, "Your category is Underweight",
                "Your category doesn't match with reality");
        driver.quit();
    }

    @Test
    public void normalAndObeseCategories() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("https://healthunify.com");
        driver.findElement(By.id("menu-item-963")).click();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        Select dropdownWeight = new Select(driver.findElement(By.name("opt1")));
        driver.findElement(By.name("wg")).sendKeys("75");
        Select dropdownHeight = new Select(driver.findElement(By.name("opt2")));
        dropdownHeight.selectByVisibleText("5′");
        Select dropdownSecondHeight = new Select(driver.findElement(By.name("opt3")));
        dropdownSecondHeight.selectByVisibleText("9″");
        driver.findElement(By.name("cc")).click();
        String category = driver.findElement(By.name("desc")).getAttribute("value");
        Assert.assertEquals(category, "Your category is Normal",
                "Your category matches with reality");
        dropdownWeight.selectByVisibleText("pounds");
        driver.findElement(By.name("cc")).click();
        String secondCategory = driver.findElement(By.name("desc")).getAttribute("value");
        Assert.assertEquals(secondCategory, "Your category is Obese",
                "Your category doesn't match with reality, it's a bug");
        if (secondCategory.equals(category)) {
            System.out.println("Everything is ok");
        }
        else {
            System.out.println("Found a bug");
        }
        driver.quit();
    }

        @Test
    public void starvationCategory() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("https://healthunify.com");
        driver.findElement(By.id("menu-item-963")).click();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        Select dropdownHeight = new Select(driver.findElement(By.name("opt2")));
        dropdownHeight.selectByVisibleText("7′");
        Select dropdownSecondHeight = new Select(driver.findElement(By.name("opt3")));
        dropdownSecondHeight.selectByVisibleText("11″");
        driver.findElement(By.name("cc")).click();
        driver.switchTo().alert().accept();
        String category = driver.findElement(By.name("desc")).getAttribute("value");
        Assert.assertEquals(category, "Your category is Starvation",
                "Your category doesn't match with reality");
        driver.quit();
    }

    @Test
    public void zeroHeightCategory() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("https://healthunify.com");
        driver.findElement(By.id("menu-item-963")).click();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        driver.findElement(By.name("wg")).sendKeys("75");
        driver.findElement(By.name("cc")).click();
        driver.switchTo().alert().accept();
        String category = driver.findElement(By.name("desc")).getAttribute("value");
        Assert.assertEquals(category, "Your category is Obese",
                "Your category doesn't match with reality");
        driver.quit();
    }
}