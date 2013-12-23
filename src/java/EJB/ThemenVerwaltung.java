/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB;

import Entity.Content;
import Entity.Thema;
import java.io.Serializable;
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
        c.setVersion(0);
        t.setName("TestThema");
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
        Thema erg = em.createQuery("SELECT t FROM THEMA t", Thema.class).getSingleResult();
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
}
