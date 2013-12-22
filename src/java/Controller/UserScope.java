/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Entity.Thema;
import Stateless.ThemenVerwaltung;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Moolt
 */
@SessionScoped
@Named("wiki")
public class UserScope implements Serializable {
    private final static String STARTPAGE = "Startseite";
    
    @EJB
    private ThemenVerwaltung ev;
    private String userName;
    private Thema ausgewaehltesThema;
    private int ausgewaehlteVersion;

    public void UserScope() {
        ev.testdaten();
        ausgewaehltesThema = ev.findById(0);
    }

    public String getTitel() {
        if(ausgewaehltesThema == null) return "null";
        return ausgewaehltesThema.getName();
    }

    public String getContent() {
        return "";//ausgewaehltesThema.getContent(ausgewaehlteVersion).getText();
    }

    public Thema getAusgewaehlterEintrag() {
        return ausgewaehltesThema;
    }

    public void setAusgewaehlterEintrag(Thema ausgewaehlterEintrag) {
        this.ausgewaehltesThema = ausgewaehlterEintrag;
    }

    public int getAusgewaehlteVersion() {
        return ausgewaehlteVersion;
    }

    public void setAusgewaehlteVersion(int ausgewaehlteVersion) {
        this.ausgewaehlteVersion = ausgewaehlteVersion;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
