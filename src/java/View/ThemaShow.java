/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import EJB.ThemenVerwaltung;
import Entity.Thema;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Moolt
 */
@ViewScoped
@Named("show")
public class ThemaShow implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(ThemaShow.class.toString());
    private final static String PAGE = "./show.xhtml";
    private final static String EDIT = "./edit.xhtml";

    @EJB
    private ThemenVerwaltung tv;
    @Inject
    private ThemenView session;

    private Thema thema;
    private int aktuelleVersion;
    
    /**
     * Initialisiert die Objektvariablen
     */
    @PostConstruct
    public void init() {
        try {
            thema = tv.findByName(session.getThema());
            session.setAngezeigteVersion(thema.getLatestVersion());
            aktuelleVersion = thema.getLatestVersion();
        } catch (Exception e) {

        }
    }

    /**
     * 
     * @return Verweis zur edit.xhtml
     */
    public String bearbeiten() {
        return EDIT;
    }

    /**
     * 
     * @return Der Textinhalt der angezeigten Version des ausgewaehlten Themas
     */
    public String getContent() {
        return thema.getContent(session.getAngezeigteVersion()).getText();
    }

    /**
     * 
     * @return Der Titel es angezeigten/ zu bearbeitenden Themas
     */
    public String getTitel() {
        return thema.getName();
    }

    /**
     * 
     * @return Die Versionsnummer der aktuellsten Version eines Themas
     */
    public int getAktuelleVersion() {
        return aktuelleVersion;
    }

    /**
     * 
     * @param aktuelleVersion Die Versionsnummer der aktuellsten Version eines Themas
     */
    public void setAktuelleVersion(int aktuelleVersion) {
        this.aktuelleVersion = aktuelleVersion;
    }

    /**
     *
     * @return Der Name des Autors, der die aktuellste Version des angezeigten
     * Themas verfasst hat
     */
    public String getAktuellerAutor() {
        return this.thema.getContent(aktuelleVersion).getAuthor();
    }

    /**
     *
     * @return Der Name des Autors, dessen Version angezeigt wird
     */
    public String getAngezeigterAutor() {
        return this.thema.getContent(session.getAngezeigteVersion()).getAuthor();
    }

}
