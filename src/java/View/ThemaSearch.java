/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import EJB.ThemenVerwaltung;
import Entity.Thema;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Moolt
 */
@SessionScoped
@Named("search")
public class ThemaSearch implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(ThemaShow.class.toString());
    @EJB
    private ThemenVerwaltung tv;
    private String suchbegriff;
    private List<Thema> ergebnisse;

    /**
     * Initialisiert die Objektvariablen
     */
    @PostConstruct
    public void init() {
        ergebnisse = new ArrayList<>();
    }

    /**
     * Startet eine Suchanfrage anhand eines Suchbegriffs. Gibt es keines oder
     * mehr als ein Ergebnis, wird auf die search.xhtml verwiesen Gibt es genau
     * ein Ergebnis, wird auf den gefundenen Artikel verwiesen
     *
     * @return Der Verweis auf die weiterfuhrende Seite
     */
    public String suchen() {
        ergebnisse.clear();
        this.ergebnisse = tv.searchByName(suchbegriff);

//        for (Thema t : themen) {
//            String link = "<a href=\"./show.xhtml?thema=" + t.getName()
//                    + "&faces-redirect=true\">" + t.getName() + "</a>";
//            ergebnisse.add(link);
//        }

        //Es wurde genau 1 Thema gefunden, also kann direkt zum 
        //Artikel verwiesen werden
        if (ergebnisse.size() == 1) {
            return "./show.xhtml?thema=" + ergebnisse.get(0).getName() + "&faces-redirect=true";
        } else {
            return "./search.xhtml";
        }
    }

    /**
     * 
     * @return Der Suchbegriff des Suchvorgangs
     */
    public String getSuchbegriff() {
        return suchbegriff;
    }

    /**
     *
     * @param suchbegriff Der Suchbegriff, nach dem beim Aufruf der Funktion
     * suchen() gesucht werden soll.
     */
    public void setSuchbegriff(String suchbegriff) {
        this.suchbegriff = suchbegriff;
    }

    public List<Thema> getErgebnisse() {
        return ergebnisse;
    }

    public void setErgebnisse(List<Thema> ergebnisse) {
        this.ergebnisse = ergebnisse;
    }
    
    public int getAnzahlErgebnisse() {
        return this.ergebnisse.size();
    }
}