/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Entity.Thema;
import EJB.ThemenVerwaltung;
import Entity.Content;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Moolt
 */
@SessionScoped
@Named("wiki")
public class ThemenView implements Serializable {

    private final static String STARTPAGE = "Startseite";
    private final static String EDIT = "./edit.xhtml";
    private final static String INDEX = "./index.xhtml";

    @EJB
    private ThemenVerwaltung ev;
    private String nutzerName;
    private Thema ausgewaehltesThema;
    private int ausgewaehlteVersion;
    private String bearbeiteterContent;

    @PostConstruct
    public void init() {
        //ev.testdaten();
        nutzerName = "";
        ausgewaehltesThema = ev.findByName(STARTPAGE);
        ausgewaehlteVersion = 0;
    }

    public String getTitel() {
        if (ausgewaehltesThema != null) {
            return ausgewaehltesThema.getName();
        } else {
            return "Artikel konnte nicht gefunden werden.";
        }
    }

    public String getContent() {
        return ausgewaehltesThema.getContent(ausgewaehlteVersion).getText();
    }

    public String getBearbeiteterContent() {
        return bearbeiteterContent;
    }

    public void setBearbeiteterContent(String bearbeiteterContent) {
        this.bearbeiteterContent = bearbeiteterContent;
    }        

    public Thema getAusgewaehltesThema() {
        return ausgewaehltesThema;
    }

    public void setAusgewaehltesThema(Thema ausgewaehltesThema) {
        this.ausgewaehltesThema = ausgewaehltesThema;
    }   

    public int getAusgewaehlteVersion() {
        return ausgewaehlteVersion;
    }

    public String getAusgewaehlterAutor() {
        return ausgewaehltesThema.getContent(ausgewaehlteVersion).getAuthor();
    }

    public int getNeusteVersion() {
        return ausgewaehltesThema.getLatestVersion();
    }

    public String getNeusterAutor() {
        return ausgewaehltesThema.getContent(ausgewaehltesThema.getLatestVersion()).getAuthor();
    }

    public void setAusgewaehlteVersion(int ausgewaehlteVersion) {
        this.ausgewaehlteVersion = ausgewaehlteVersion;
    }

    public String getNutzerName() {
        return nutzerName;
    }

    public void setNutzerName(String nutzerName) {
        this.nutzerName = nutzerName;
    }    
    
    public String neueVersionSpeichern(){
        Content neueVersion = new Content();
        neueVersion.setAuthor(nutzerName);
        neueVersion.setVersion(ausgewaehltesThema.getLatestVersion()+1);
        neueVersion.setText(bearbeiteterContent);
        this.ausgewaehltesThema.addContent(neueVersion);
        ev.update(this.ausgewaehltesThema);
        return INDEX;
    }
    
    public String bearbeiten(){
        this.bearbeiteterContent = ausgewaehltesThema.getContent(ausgewaehlteVersion).getText();
        return EDIT;
    }
    
    public String aktualisieren(){        
        return INDEX;
    }
    
    public boolean nameVorhanden(){
        return this.nutzerName.length() > 0;
    }
}
