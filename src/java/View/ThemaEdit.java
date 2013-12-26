/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import EJB.ThemenVerwaltung;
import javax.ejb.EJB;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import Entity.Thema;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author Moolt
 */
@RequestScoped
@Named("edit")
public class ThemaEdit implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(ThemaShow.class.toString());
    private final static String PAGE = "./edit.xhtml";
    private final static String SHOW = "./show.xhtml";

    @EJB
    private ThemenVerwaltung tv;
    @Inject
    private ThemenView session;

    private String bearbeiteterContent;
    private Thema thema;

    @PostConstruct
    public void init() {
        this.thema = tv.findByName(session.getThema());
        this.bearbeiteterContent = thema.getContent(thema.getLatestVersion()).getText();
        this.session.pushToBacklog(PAGE);
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
