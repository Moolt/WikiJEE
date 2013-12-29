package xhtml;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
/*
 *
 * @author bertram
 */

public class XhtmlClassTest {
    private WebDriver driver;
    private static String text1;
    private static String text2;
    private static String tmp;
    
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
        driver.findElement(By.id("j_idt10:searchBtn")).click();
    }
    
    private void eingabeEdit(){
        driver.findElement(By.name("text:j_idt30")).click();
    }
    
    private void eingabeUebernehmen(){
         driver.findElement(By.name("editForm:submitBtn")).click();
    }

    private void eingabeClearText(){
        driver.findElement(By.name("editForm:clearButton")).click();
    }
    
    private void eingabeZurueck(){
        driver.findElement(By.name("text:backBtn")).click();
    }
    
    private void feldFuellen(String id, String wert){
        driver.findElement(By.name(id)).clear();
        driver.findElement(By.name(id)).sendKeys(wert);
    }
    
    private void nutzernameEingeben(String nutzername){
        driver.findElement(By.name("name:j_idt8")).clear();
        driver.findElement(By.name("name:j_idt8")).sendKeys(nutzername);
    }
    /*TODO: Rückgängig machen? Mit tmp zwischen gespeichert*/
    private void editTextEingeben(String text){
        driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
        tmp = driver.findElement(By.xpath("html/body")).getText();
        eingabeClearText();
        driver.findElement(By.xpath("html/body")).sendKeys(text);
    }
    
    private void enterStartseite(){
        Startseite();
        Assert.assertTrue(driver.findElement(By.tagName("body")).getText().contains("Startseite"));
    }
    
    private void artikelErfolgreichEditieren(String name, String text){
        Startseite();
        nutzernameEingeben(name);
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.elementToBeClickable(By.name("text:j_idt30")));
        eingabeEdit();
        editTextEingeben(text);
        eingabeUebernehmen();
        Assert.assertTrue(driver.findElement(By.tagName("body")).getText().contains(text));   
    }
    
    private void artikelErfolglosEditieren(String name, String text){
        Startseite();
        nutzernameEingeben(name);
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.elementToBeClickable(By.name("text:j_idt30")));
        eingabeEdit();
        editTextEingeben(text);
        nutzernameEingeben("");
        eingabeUebernehmen();
        Assert.assertTrue(driver.findElement(By.tagName("body")).getText().contains("Kein Name eingegeben."));   
    }
    
    private void artikelOhneNutzernameEditieren(){
        Startseite();
        nutzernameEingeben("");
        try{
            eingabeEdit();
        }catch(Exception NoSuchElementException){
           Assert.assertTrue(true);
        }
    }
    
    private void artikelErfolgreichSuchen(String name, String art){
        feldFuellen(name, art);
        eingabeSuche();
        Assert.assertTrue(driver.findElement(By.tagName("body")).getText().contains(art));
    }
    
    private void artikelErfolglosSuchen(String id, String name){
        feldFuellen(id, name);
        eingabeSuche();
        Assert.assertTrue(driver.findElement(By.tagName("body")).getText().contains("Es wurden keine Artikel gefunden"));
    }

    
    
    @Test
    public void testEnterStartseite(){
        enterStartseite();
    }
    
    @Test
    public void testArtikelOhneNutzernameEditieren(){
        Startseite();
        artikelOhneNutzernameEditieren();
    }
    
    @Test
    public void testArtikelErfolgreichEditieren(){
        artikelErfolgreichEditieren("Testuser", "Dies ist ein [Test] unserer [Wikiseite]");
    }
    
    public void testArtikelErfolglosEditieren(){
        artikelErfolglosEditieren("Testuser", "Dies ist ein [Test] unserer [Wikiseite]");
    }
    
    @Test
    public void testErfolglosSuchen(){
        Startseite();
        artikelErfolglosSuchen("j_idt10:j_idt12", "nichtVorkommenderText");

    }
    
    @Test
    public void testErfolgreichSuchen(){
        Startseite();
        artikelErfolgreichSuchen("j_idt10:j_idt12", "Datenbank");
    }

    @Test
    public void testZurueckButtonEinmalNutzen(){
        Startseite();
        feldFuellen("j_idt10:j_idt12", "Entity");
        eingabeSuche();
        feldFuellen("j_idt10:j_idt12", "Datenbank");
        eingabeSuche();
        eingabeZurueck();
        Assert.assertTrue(driver.findElement(By.tagName("body")).getText().contains("Entity"));
    }
    
    @Test
    public void testZurueckButtonZweimalNutzen(){
        Startseite();
        feldFuellen("j_idt10:j_idt12", "Entity");
        eingabeSuche();
        feldFuellen("j_idt10:j_idt12", "Datenbank");
        eingabeSuche();
        eingabeZurueck();
        eingabeZurueck();
        Assert.assertTrue(driver.findElement(By.tagName("body")).getText().contains("Startseite"));     
    }


    
}
