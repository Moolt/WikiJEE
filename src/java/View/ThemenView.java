/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Entity.Thema;
import EJB.ThemenVerwaltung;
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
@Named("usession")
public class ThemenView implements Serializable {

    private final static String STARTPAGE = "Startseite";

    @EJB
    private ThemenVerwaltung ev;
    private String nutzerName;
    private String ausgewaehltesThemaName;

    @PostConstruct
    public void init() {
        //ev.testdaten();
        nutzerName = "";
        ausgewaehltesThemaName = STARTPAGE;
    }

    public String getNutzerName() {
        return nutzerName;
    }

    public void setNutzerName(String nutzerName) {
        this.nutzerName = nutzerName;
    }

    public String getAusgewaehltesThemaName() {
        return ausgewaehltesThemaName;
    }

    public void setAusgewaehltesThemaName(String ausgewaehltesThemaName) {
        this.ausgewaehltesThemaName = ausgewaehltesThemaName;
    }

    public boolean nameVorhanden() {
        return this.nutzerName.length() > 0;
    }
}
