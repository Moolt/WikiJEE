/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import EJB.ThemenVerwaltung;
import javax.ejb.EJB;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import Entity.Thema;
import javax.inject.Named;

/**
 *
 * @author Moolt
 */
@RequestScoped
@Named("edit")
public class ThemaEdit {
    private final static String SHOW = "./show.xhtml";
    
    @EJB
    private ThemenVerwaltung tv;
    @Inject
    private ThemenView session;

    private String bearbeiteterContent;
    private Thema thema;

    @PostConstruct
    public void init() {
        thema = tv.findByName(session.getAusgewaehltesThemaName());
        bearbeiteterContent = thema.getContent(thema.getLatestVersion()).getText();
    }

    public String getBearbeiteterContent() {
        return bearbeiteterContent;
    }

    public void setBearbeiteterContent(String bearbeiteterContent) {
        this.bearbeiteterContent = bearbeiteterContent;
    }

    public String uebernehmen() {
        String name = session.getNutzerName();
        tv.neueVersionSpeichern(thema, bearbeiteterContent, name);
        return SHOW;
    }

    public String getTitel() {
        return thema.getName();
    }
}
