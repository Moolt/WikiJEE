/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import EJB.ThemenVerwaltung;
import Entity.Thema;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Moolt
 */
@RequestScoped
@Named("show")
public class ThemaShow {

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
        thema = tv.findByName(session.getAusgewaehltesThemaName());
        angezeigteVersion = thema.getLatestVersion();
        aktuelleVersion = thema.getLatestVersion();
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
