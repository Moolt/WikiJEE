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
import javax.annotation.PreDestroy;
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
public class ThemaShow implements Serializable  {
    private static final Logger LOGGER = Logger.getLogger(ThemaShow.class.toString());
    private final static String PAGE = "./show.xhtml";
    private final static String EDIT = "./edit.xhtml";
    
    @EJB
    private ThemenVerwaltung tv;
    @Inject
    private ThemenView session;

    private Thema thema;
    private int aktuelleVersion;
    private int angezeigteVersion;

    @PostConstruct
    public void init() {
        thema = tv.findByName(session.getThema());
        angezeigteVersion = thema.getLatestVersion();
        aktuelleVersion = thema.getLatestVersion();        
        this.session.pushToBacklog(PAGE);
    }
    
    public String getContent() {
        return thema.getContent(angezeigteVersion).getText();
    }

    public String getTitel() {
        return thema.getName();
    }

    public int getAktuelleVersion() {
        return aktuelleVersion;
    }

    public void setAktuelleVersion(int aktuelleVersion) {
        this.aktuelleVersion = aktuelleVersion;
    }

    public int getAngezeigteVersion() {
        return angezeigteVersion;
    }

    public void setAngezeigteVersion(int angezeigteVersion) {
        this.angezeigteVersion = angezeigteVersion;
    }

    public String bearbeiten() {
        return EDIT;
    }

    public String getAktuellerAutor() {
        return this.thema.getContent(aktuelleVersion).getAuthor();
    }

    public String getAngezeigterAutor() {
        return this.thema.getContent(angezeigteVersion).getAuthor();
    }

}
