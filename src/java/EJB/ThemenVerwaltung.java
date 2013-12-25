/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB;

import Entity.Content;
import Entity.Thema;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Moolt
 */
@Stateless
public class ThemenVerwaltung implements Serializable {

    @PersistenceContext(unitName = "WikiKBSEPU")
    private EntityManager em;

    public void ThemenVerwaltung() {
    }

    public void testdaten() {
        Thema t = new Thema();
        Content c = new Content();
        c.setText("Das ist ein Beispieltext");
        c.setAuthor("testautor");
        c.setVersion(0);
        t.setName("Startseite");
        t.addContent(c);
        em.persist(t);
    }

    /**
     * Persistiert ein Objekt in der Datenbank
     *
     * @param object Das Objekt, das persistiert werden soll
     */
    public void persist(Object object) {
        em.persist(object);
    }

    /**
     * Aktualisiert ein Objekt in der Datenbank
     *
     * @param object Das Objekt, das aktualisiert werden soll
     */
    public void update(Object object) {
        em.merge(object);
    }

    /**
     * Sucht ein WikiEintrag Objekt mit dem angegebenen Namen aus der Datenbank
     *
     * @param name Name des Wiki-Eintrags
     * @return Der Eintrag, der aus der Suche resultiert
     */
    public Thema findByName(String name) {
        Thema erg = em.createQuery("SELECT t FROM Thema t WHERE t.name=\"" + name + "\"", Thema.class).getSingleResult();
        return erg;
    }

    /**
     * Sucht ein WikiEintrag Objekt mit der angegebenen Id aus der Datenbank
     *
     * @param id Id des Wiki-Eintrags
     * @return Der Eintrag, der aus der Suche resultiert
     */
    public Thema findById(int id) {
        return em.find(Thema.class, id);
    }

    /**
     * Sucht in der Datenbank, ob bereits ein Thema mit dem Namen existiert
     * @param name Der Name nach dem gesucht werden soll
     * @return True, wenn ein Thema mit dem Namen gefunden wurde
     */
    public boolean themaExists(String name) {
        return em.createQuery("SELECT t FROM Thema t WHERE t.name=\"" + name + "\"", Thema.class).getResultList().size() > 0;
    }
    
    public void refresh(Object o){
        em.refresh(o);
    }

    public void neueVersionSpeichern(Thema thema, String neuerContent, String nutzerName) {
        List<String> themen = this.erstelleNeueThemen(neuerContent);
        Content neueVersion = new Content();
        neueVersion.setAuthor(nutzerName);
        neueVersion.setVersion(thema.getLatestVersion() + 1);
        neueVersion.setText(neuerContent);
        thema.addContent(neueVersion);
        this.update(thema);
    }

    private List<String> erstelleNeueThemen(String str) {
        List<String> ermittelteThemen = new LinkedList<>();
        Matcher m = Pattern.compile("\\[([^\\[\\]<]*)\\]").matcher(str);
        while (m.find()) {
            String gefundenesThema = m.group(1).toLowerCase();
            if (!this.themaExists(gefundenesThema)) {
                Thema neuesThema = new Thema();
                Content neuerContent = new Content();
                neuesThema.setName(gefundenesThema);
                neuerContent.setVersion(0);
                neuerContent.setText("kein text");
                neuerContent.setAuthor("System");
                neuesThema.addContent(neuerContent);
                this.persist(neuesThema);
            }
        }
        return ermittelteThemen;
    }
}
