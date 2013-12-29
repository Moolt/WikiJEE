package Entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Daten-Objekt das ein Themas (Wiki-Eintrag) mit Versionen repraesentiert
 * @author Moolt
 */
@Entity
@Table(name = "Thema")
public class Thema implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    @OneToMany(cascade = CascadeType.PERSIST)
    private Set<Content> contentSet;
    private long letzteAenderung;

    public Thema() {
        contentSet = new HashSet<>();
    }

    /**
     * 
     * @return Die ID, unter der die Entitaet in der Datenbank gespeichert wird 
     */
    public int getId() {
        return id;
    }

    /**
     * 
     * @param id Die ID, unter der die Entitaet in der Datenbank gespeichert wird 
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * 
     * @return Der Titel des Themas
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name Der Titel des Themas 
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return Die Menge aller Versionen dieses Themas
     */
    public Set<Content> getContentSet() {
        return contentSet;
    }

    /**
     * 
     * @param contentSet Die Menge aller Versionen dieses Themas
     */
    public void setContentSet(Set<Content> contentSet) {
        this.contentSet = contentSet;
    }
    
    /**
     * Fuegt eine neue Version in die Menge der Versionen ein
     * @param content Das Content-Objekt der neuen Version
     */
    public void addContent(Content content){
        this.contentSet.add(content);
    }
    
    /**
     * 
     * @return Die Versionsnummer der aktuellsten Version
     */
    public int getLatestVersion(){
        int version = -1;
        for(Content v: contentSet){
            if(v.getVersion() > version){
                version = v.getVersion();
            }
        }
        return version;
    }
    
    /**
     * Sucht die Version aus der Menge der Version raus, dessen Versionsnummer
     * mit der uebergebenen uebereinstimmt
     * @param version Die Versionsnummer der Version, die gesucht werden soll
     * @return Die gefundene Version oder null, wenn keine entsprechende Version gefunden wurde
     */
    public Content getContent(int version){
        for(Content v: contentSet){
            if(v.getVersion() == version){
                return v;
            }
        }
        return null;
    }

    /**
     * 
     * @return Das Datum der letzten Aenderung des Themas als Unix-Timestamp
     */
    public long getLetzteAenderung() {
        return letzteAenderung;
    }

    /**
     * 
     * @param letzteAenderung Das Datum der letzten Aenderung des Themas als Unix-Timestamp
     */
    public void setLetzteAenderung(long letzteAenderung) {
        this.letzteAenderung = letzteAenderung;
    } 
    
    /**
     * Formatiert das letzte Aenderungsdatum zu einem String
     * @return 
     */
    public String getLetzteAenderungAsString(){
        Date datum = new Date((long)letzteAenderung*1000);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy 'um' HH:mm:ss");
        return sdf.format(datum);
    }
}
