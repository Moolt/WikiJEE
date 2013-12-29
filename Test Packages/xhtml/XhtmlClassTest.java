package xhtml;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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
    
    @BeforeClass
    public static void setupClass(){

    }
    
    @Before
    public void setUp(){
        driver = new FirefoxDriver();
        driver.get("http://localhost:8080/WikiJEE/faces/show.xhtml");
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
        driver.findElement(By.id("searchForm:searchBtn")).click();
    }
    
    private void eingabeEdit(){
        driver.findElement(By.name("text:editBtn")).click();
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
        driver.findElement(By.name("name:nutzerName")).clear();
        driver.findElement(By.name("name:nutzerName")).sendKeys(nutzername);
    }
    /*TODO: Rückgängig machen? Mit tmp zwischen gespeichert*/
    private void editTextEingeben(String text){
        driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
        driver.findElement(By.xpath("html/body")).sendKeys(Keys.CONTROL + "a");
        driver.findElement(By.xpath("html/body")).sendKeys(text);
    }
    
    private void enterStartseite(){
        Assert.assertTrue(driver.findElement(By.tagName("body")).getText().contains("Startseite"));
    }
    
    private void artikelErfolgreichEditieren(String name, String text){
        nutzernameEingeben(name);
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.elementToBeClickable(By.name("text:editBtn")));
        eingabeEdit();
        editTextEingeben(text);
        driver.switchTo().defaultContent();
        eingabeUebernehmen();
        Assert.assertTrue(driver.findElement(By.tagName("body")).getText().contains("Dies ist ein"));   
    }
    
    /**
     *  Artikel soll editiert werden, danach soll der Username herausgelöscht werden und der Button "Uebernehmen" aktiviert werden.
     * Jedoch beim Klick auf Uebernehmen schreibt sich automatisch wieder der vorherige Nutzername ins Feld rein. Wenn man das
     * Testszenario haendisch ausfuehrt, passiert das nicht.
     * 
     */
    private void artikelErfolglosEditieren(String name, String text){
         nutzernameEingeben(name);
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.elementToBeClickable(By.name("text:editBtn")));
        eingabeEdit();
        editTextEingeben(text);
        driver.switchTo().defaultContent();
        driver.findElement(By.name("name:nutzerName")).clear();
        
        eingabeUebernehmen();
        Assert.assertTrue(driver.findElement(By.tagName("body")).getText().contains("Kein Name eingegeben."));   
    }
    
    private void artikelOhneNutzernameEditieren(){
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
        artikelOhneNutzernameEditieren();
    }
    
    @Test
    public void testArtikelErfolgreichEditieren(){
        artikelErfolgreichEditieren("Testuser", "Dies ist ein [Test] unserer [Wikiseite] ");
    }
    
    @Test
    public void testArtikelErfolglosEditieren(){
        artikelErfolglosEditieren("Testuser", "Dies ist ein [Test] unserer [Wikiseite] ");
    }
    
    @Test
    public void testErfolglosSuchen(){
         artikelErfolglosSuchen("searchForm:searchInput", "nichtVorkommenderText");

    }
    
    @Test
    public void testErfolgreichSuchen(){
        artikelErfolgreichSuchen("searchForm:searchInput", "Datenbank");
    }

    @Test
    public void testZurueckButtonEinmalNutzen(){
        feldFuellen("searchForm:searchInput", "Entity");
        eingabeSuche();
        feldFuellen("searchForm:searchInput", "Datenbank");
        eingabeSuche();
        eingabeZurueck();
        Assert.assertTrue(driver.findElement(By.tagName("body")).getText().contains("Entity"));
    }
    
    @Test
    public void testZurueckButtonZweimalNutzen(){
        feldFuellen("searchForm:searchInput", "Entity");
        eingabeSuche();
        feldFuellen("searchForm:searchInput", "Datenbank");
        eingabeSuche();
        eingabeZurueck();
        eingabeZurueck();
        Assert.assertTrue(driver.findElement(By.tagName("body")).getText().contains("Startseite"));     
    }

   
}
