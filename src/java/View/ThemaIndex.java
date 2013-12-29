package View;

import EJB.ThemenVerwaltung;
import javax.ejb.EJB;
import Entity.Thema;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author Moolt
 */
@RequestScoped
@Named("index")
public class ThemaIndex implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(ThemaShow.class.toString());
    private final static String PAGE = "./index.xhtml";
    private int anzahl = 10;

    @EJB
    private ThemenVerwaltung tv;

    /**
     * Laed die Seite neu, um die aktuellen Aenderungen anzueigen
     *
     * @return Der Link zur aktuellen Seite
     */
    public String aktualisieren() {
        return PAGE;
    }

    /**
     *
     * @return Die Liste der aktuelltsten Themen
     */
    public List<Thema> getAktuellsteThemen() {
        return tv.getAktuellsteThemen(anzahl);
    }

    /**
     *
     * @return Anzahl der Themen, die angezeigt werden sollen
     */
    public int getAnzahl() {
        return anzahl;
    }

    /**
     *
     * @param anzahl Anzahl der Themen, die angezeigt werden sollen
     */
    public void setAnzahl(int anzahl) {
        this.anzahl = anzahl;
    }
}
