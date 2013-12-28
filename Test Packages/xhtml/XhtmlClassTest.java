package xhtml;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
/*
 *
 * @author bertram
 */

public class XhtmlClassTest {
    private WebDriver driver;
    private static String text1;
    private static String text2;
    
    @BeforeClass
    public static void setupClass(){
        text1="Startseite";
        text2="searchBtn";
    }
    
    @Before
    public void setUp(){
        driver = new FirefoxDriver();
    }
    
    @After
    public void tearDown(){
        driver.quit();
        System.out.println("\n##########################################################");
    }

    private void Startseite(){
        driver.get("http://localhost:8080/WikiJEE/faces/show.xhtml");
    }

    private void eingabeSuche(){
        driver.findElement(By.id("searchBtn")).click();
    }
    
    private void eingabeEdit(){
        driver.findElement(By.id("submitBtn")).click();
    }

    private void eingabeZurueck(){
        driver.findElement(By.id("backBtn")).click();
    }
    
    private void feldFuellen(String id, String wert){
        driver.findElement(By.id(id)).clear();
        driver.findElement(By.id(id)).sendKeys(wert);
    }

    private void artikelErfolgreichSuchen(String id, String name){
        feldFuellen(id, name);
        eingabeSuche();
        Assert.assertTrue(driver.findElement(By.tagName("body")).getText().contains("Startseite"));
    }
    
    private void artikelErfolglosSuchen(String id, String name){
        feldFuellen(id, name);
        eingabeSuche();
        Assert.assertTrue(driver.findElement(By.tagName("body")).getText().contains("Es wurden keine Artikel gefunden"));
    }

    @Test
    public void testErfolglosSuchen(){
        try{
            artikelErfolglosSuchen("searchBtn", "nichtVorkommenderText");
        }catch(Exception e){
            System.out.println("An error-message");
        }
    }
    
    @Test
    public void testErfolgreichSuchen(){
        artikelErfolgreichSuchen("searchBtn", "Mudda");
    }
}
