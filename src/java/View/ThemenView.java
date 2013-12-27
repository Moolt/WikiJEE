/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Pojo.SeitenZustand;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Moolt
 */
@SessionScoped
@Named("usession")
public class ThemenView implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(ThemaShow.class.toString());
    private final static String STARTPAGE = "Startseite";

    private Stack<SeitenZustand> backlog;
    private String nutzerName;
    private String thema;

    /**
     * Initialisiert die Objektvariablen
     */
    @PostConstruct
    public void init() {
        this.backlog = new Stack<>();
        this.nutzerName = "";
        this.thema = STARTPAGE;
    }

    /**
     * Fuegt eine Seite in den Stack ein, der fuer die zurueck-Funktion verwenden wird
     * @param page Der Seitennahme (z.B. show.xhtml)
     */
    public void pushToBacklog(String page) {
        LOGGER.log(Level.SEVERE, "{0} {1} {2}", new Object[]{page, this.thema, this.nutzerName});
        SeitenZustand status = new SeitenZustand(page, this.thema, this.nutzerName);
        this.backlog.push(status);
    }

    /**
     * Liesst den die letzte aufgerufene Seite aus dem Stack und gibt sie zurueck
     * @return Die letze aufgerufende Seite
     */
    public String zurueck() {
        if (this.backlog.size() == 1) {
            return this.backlog.peek().getPageURL();
        }
        this.backlog.pop();
        SeitenZustand status = this.backlog.pop();
        this.thema = status.getThema();
        return status.getPageURL();
    }

    /**
     * 
     * @return Der zuvor festgelegte Name des Nutzers 
     */
    public String getNutzerName() {
        return nutzerName;
    }

    /**
     * 
     * @param nutzerName Der Name des Nutzers, unter dem Beitraege verfasst und 
     * bearbeitet werden sollen
     */
    public void setNutzerName(String nutzerName) {
        this.nutzerName = nutzerName;
    }

    /**
     * 
     * @return Der Name des aktuell ausgewaehlten Themas
     */
    public String getThema() {
        return thema;
    }

    /**
     * 
     * @param thema Der Name des auszuwaehlenden Themas
     */
    public void setThema(String thema) {
        this.thema = thema;
    }

    /**
     * 
     * @return True, wenn der Name nicht leer ist
     */
    public boolean nameVorhanden() {
        return this.nutzerName.length() > 0;
    }
}
