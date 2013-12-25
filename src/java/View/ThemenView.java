/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Pojo.SeitenZustand;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Moolt
 */
@SessionScoped
@Named("usession")
public class ThemenView implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(ThemaShow.class.toString());
    private final static String STARTPAGE = "Startseite";

    private Stack<SeitenZustand> backlog;
    private String nutzerName;
    private String thema;

    @PostConstruct
    public void init() {
        this.backlog = new Stack<>();
        this.nutzerName = "";
        this.thema = STARTPAGE;
    }

    public String getNutzerName() {
        return nutzerName;
    }

    public void setNutzerName(String nutzerName) {
        this.nutzerName = nutzerName;
    }

    public String getThema() {
        return thema;
    }

    public void setThema(String thema) {
        this.thema = thema;
    }

    public boolean nameVorhanden() {
        return this.nutzerName.length() > 0;
    }

    public void pushToBacklog(String page) {
        LOGGER.log(Level.SEVERE, "{0} {1} {2}", new Object[]{page, this.thema, this.nutzerName});
        SeitenZustand status = new SeitenZustand(page, this.thema, this.nutzerName);
        this.backlog.push(status);
    }

    public String zurueck() {       
        if(this.backlog.size() == 1){
            return this.backlog.peek().getPageURL();
        }
        this.backlog.pop();            
        SeitenZustand status = this.backlog.pop();
        this.thema = status.getThema();
        return status.getPageURL();
    }
}
