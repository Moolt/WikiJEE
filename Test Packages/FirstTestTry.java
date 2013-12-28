/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
import 
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;*/
/**
 *
 * @author bertram
 */
/**
public class FirstTestTry {
    private WebDriver driver;
    private static String text;
    
    @BeforeClass
    public static void setupClass(){
        text="a";
    }
    
    @Before
    public void setUp(){
        driver = new FirefoxDriver();
    }
    
    @After
    public void tearDown(){
        driver.quit();
    }

    private void Startseite(){
        driver.get("http://localhost:8080/WikiKBSE");
    }
}
*/