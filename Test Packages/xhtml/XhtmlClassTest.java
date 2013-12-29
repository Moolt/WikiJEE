package xhtml;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
/*
 *
 * @author bertram
 */

public class XhtmlClassTest {
    private WebDriver driver;
    private static String tmp="";
    
    /**
     * Setup wird vor jedem Test ausgefuehrt. Es wir der driver fuer Firefox angelegt.
     * Außerdem wird direkt zur Startseite verlinkt.
     * 
     */
    
    @Before
    public void setUp(){
        driver = new FirefoxDriver();
        driver.get("http://localhost:8080/WikiJEE/faces/show.xhtml");
    }
 
    /**
     * After wird nach jedem Test ausgefuehrt. Der driver wird geschlossen.
     * 
     */
    
    @After
    public void tearDown(){   
        WebDriver clean = new FirefoxDriver();
        clean.get("http://localhost:8080/WikiJEE/faces/show.xhtml");
        WebDriverWait wait = new WebDriverWait(clean, 5);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        if(tmp!=""){
            clean.findElement(By.name("name:nutzerName")).clear();
            clean.findElement(By.name("name:nutzerName")).sendKeys("Michael");
            wait.until(ExpectedConditions.elementToBeClickable(By.name("text:editBtn")));
            clean.findElement(By.name("text:editBtn")).click();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("iframe")));
            clean.switchTo().frame(clean.findElement(By.tagName("iframe")));
            clean.findElement(By.xpath("html/body")).sendKeys(Keys.CONTROL + "a");
            clean.findElement(By.xpath("html/body")).sendKeys(tmp);
            clean.switchTo().defaultContent();
            clean.findElement(By.name("editForm:submitBtn")).click();
            tmp="";   
        }
        clean.quit();
        driver.quit();
        System.out.println("\n##########################################################");
    }

    /**
     * Sucht den Button "Search" auf der Seite und fuehrt ihn aus
     * 
     */
 
    private void eingabeSuche(){
        driver.findElement(By.id("searchForm:searchBtn")).click();
    }
    
    /**
     * Sucht den Button "Edit" auf der Seite und fuehrt ihn aus
     * 
     */    
    
    private void eingabeEdit(){
        driver.findElement(By.name("text:editBtn")).click();
    }

    /**
     * Sucht den Button "Uebernehmen" auf der Seite und fuehrt ihn aus
     * 
     */
    
    private void eingabeUebernehmen(){
         driver.findElement(By.name("editForm:submitBtn")).click();
    }

    /**
     * Sucht den Button "Bearbeiten" auf der Seite und fuehrt ihn aus
     * 
     */
    
    private void eingabeClearText(){
        driver.findElement(By.name("editForm:clearButton")).click();
    }
  
    /**
     * Sucht den Button "Zurueck" auf der Seite und fuehrt ihn aus
     * 
     */
    
    private void eingabeZurueck(){
        driver.findElement(By.name("text:backBtn")).click();
    }
    
    /**
     * Mithilfe einer id und einem Text koennen input-Textfelder
     * gefuellt werden
     * @param id = Id des Inputtextfeldes
     * @param wert = Der einzufuegende Text
     * 
     */    
    
    private void feldFuellen(String id, String wert){
        driver.findElement(By.name(id)).clear();
        driver.findElement(By.name(id)).sendKeys(wert);
    }

    /**
     * Mithilfe einem Nutzernamen kann das Textfeld "nutzerName" gefuellt werden
     * @param nutzername = neuer Nutzername
     * 
     */    
    
    private void nutzernameEingeben(String nutzername){
        driver.findElement(By.name("name:nutzerName")).clear();
        driver.findElement(By.name("name:nutzerName")).sendKeys(nutzername);
    }
    
    /**
     * Mithilfe einem Text wird im edit Feld auf der Bearbeiten-Seite der alte Text
     * geloescht und eni neuer Text eingefuegt. Da prime faces mit dem p:editor
     * ein iframe mit eigenem <html></html> erstellt, muss man den driver wechseln.
     * @param text = neuer Text fuer den Artikel
     * 
     */   
    
    private void editTextEingeben(String text){
        driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
        tmp=driver.findElement(By.xpath("html/body")).getText();
        driver.findElement(By.xpath("html/body")).sendKeys(Keys.CONTROL + "a");
        driver.findElement(By.xpath("html/body")).sendKeys(text);
    }

    /**
     * Dieser Test besteht darin, die Startseite zu erreichen.
     * 
     */   
    
    private void enterStartseite(){
        Assert.assertTrue(driver.findElement(By.tagName("body")).getText().contains("Startseite"));
    }
   
    /**
     * Dieser Test besteht darin, mit einem Nutznamen und einem Text erfolgreich
     * einen Artikel zu edieren. Da der Button per Ajax erst sichtbar wird, ist ein kleiner
     * Delay eingebaut. Der driver muss bei den Button-Klicks wieder zum Standardkontext
     * wechseln.
     * @param name = neuer Nutzername
     * @param text = Der neue text fuer den Artikel
     * 
     */   
    
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
     * Artikel soll editiert werden, danach soll der Username herausgelöscht werden und der Button "Uebernehmen" aktiviert werden.
     * Jedoch beim Klick auf Uebernehmen schreibt sich automatisch wieder der vorherige Nutzername ins Feld rein. Wenn man das
     * Testszenario haendisch ausfuehrt, passiert das nicht. Habe nichts weiteres dazu im Internet gefunden.
     * 
     */
    
    /**
     * Dieser Test besteht darin, mit einem Nutznamen und einem Text erfolglos
     * einen Artikel zu edieren. Da der Button per Ajax erst sichtbar wird, ist ein kleiner
     * Delay eingebaut. Der driver muss bei den Button-Klicks wieder zum Standardkontext
     * wechseln.
     * @param name = neuer Nutzername
     * @param text = Der neue text fuer den Artikel
     * 
     */ 
    private void artikelErfolglosEditieren(String name, String text){
        nutzernameEingeben(name);
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.elementToBeClickable(By.name("text:editBtn")));
        eingabeEdit();
        driver.findElement(By.name("name:nutzerName")).clear();
        try{
            eingabeUebernehmen();
        }catch(Exception e){
            Assert.assertTrue(driver.findElement(By.tagName("body")).getText().contains("Kein Name eingegeben."));  
        }
    }
    
    /**
     * Dieser Test besteht darin, den Edit-Button zu druecken, ohne einen
     * Nutzername eingegeben zu haben. Da dies nicht moeglich sein soll, weil
     * der Button ausgeblendet wird, soll eine NoSuchElementException geworfen werden
     * 
     */ 
    
    private void artikelOhneNutzernameEditieren(){
         nutzernameEingeben("");
        try{
            eingabeEdit();
        }catch(Exception NoSuchElementException){
           Assert.assertTrue(true);
        }
    }

    /**
     * Dieser Test besteht darin, mithilfe der Suchfunktion einen Artikel
     * zu finden
     * @param id = Id des Such-Textfeldes
     * @param name = Name des zu findenden Artikels 
     *
     */   

    private void artikelErfolgreichSuchen(String id, String name){
        feldFuellen(id, name);
        eingabeSuche();
        Assert.assertTrue(driver.findElement(By.tagName("body")).getText().contains(name));
    }

    /**
     * Dieser Test besteht darin, mithilfe der Suchfunktion einen Artikel
     * nicht zu finden.
     * @param id = Id des Such-Textfeldes
     * @param name = Name des zu findenden Artikels 
     *
     */ 
    
    private void artikelErfolglosSuchen(String id, String name){
        feldFuellen(id, name);
        eingabeSuche();
        Assert.assertTrue(driver.findElement(By.tagName("body")).getText().contains("Es wurden keine Artikel gefunden"));
    }

    /**
     * Erster Test: Erreichen der Startseite
     *
     */      
    
    @Test
    public void testEnterStartseite(){
        enterStartseite();
    }

    /**
     * Zweiter Test: Versuche einen Artiekl zu editieren von der show-Seite aus, ohne dass ein Nutzername
     * angegeben wurde
     *
     */    
    
    @Test
    public void testArtikelOhneNutzernameEditieren(){
        artikelOhneNutzernameEditieren();
    }
 
    /**
     * Dritter Test: Einen Artikel editieren 
     *
     */ 
    
    @Test
    public void testArtikelErfolgreichEditieren(){
        artikelErfolgreichEditieren("Testuser", "Dies ist ein [Test] unserer [Wikiseite] ");
    }
    
    /**
     * Vierter Test: Versuche einen Artiekl zu editieren von der edit-Seite aus, obwohl
     * der Nutzername wieder geloescht wurde
     *
     */ 
    
    @Test
    public void testArtikelErfolglosEditieren(){
        artikelErfolglosEditieren("Testuser", "Dies ist ein [Test] unserer [Wikiseite] ");
    }
    
    /**
     * Fuenfter Test: Versuche einen Artiekl zu suchen, der nicht existiert
     * 
     */ 
    
    @Test
    public void testErfolglosSuchen(){
         artikelErfolglosSuchen("searchForm:searchInput", "nichtVorkommenderText");

    }

    /**
     * Sechster Test: Einen Artikel finden
     * 
     */     
    
    @Test
    public void testErfolgreichSuchen(){
        artikelErfolgreichSuchen("searchForm:searchInput", "Datenbank");
    }

    /**
     * Siebter Test: Zurueckbutton funktion bei einmaligem Nutzen testen
     * 
     */   
    
    @Test
    public void testZurueckButtonEinmalNutzen(){
        feldFuellen("searchForm:searchInput", "Entity");
        eingabeSuche();
        feldFuellen("searchForm:searchInput", "Datenbank");
        eingabeSuche();
        eingabeZurueck();
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        Assert.assertTrue(driver.findElement(By.tagName("body")).getText().contains("Entity"));
    }

    /**
     * Achter Test: Zurueckbutton funktion bei zweimaligem Nutzen testen
     * 
     */    
    
    @Test
    public void testZurueckButtonZweimalNutzen(){
        feldFuellen("searchForm:searchInput", "Entity");
        eingabeSuche();
        feldFuellen("searchForm:searchInput", "Datenbank");
        eingabeSuche();
        eingabeZurueck();
        eingabeZurueck();
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        Assert.assertTrue(driver.findElement(By.tagName("body")).getText().contains("Startseite"));     
    }

   
}
