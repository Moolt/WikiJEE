package Entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Daten-Objekt das eine Version eines Themas repraesentiert
 * @author Moolt
 */
@Entity
@Table(name = "Version")
public class Content implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int version;
    private String text;
    private String author;

    public Content() {
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
     * @return Der Textinhalt dieser Version
     */
    public String getText() {
        return text;
    }

    /**
     * 
     * @param text Der Textinhalt dieser Version
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * 
     * @return Die Versionsnummer der Version
     */
    public int getVersion() {
        return version;
    }

    /**
     * 
     * @param version Die Versionsnummer der Version
     */
    public void setVersion(int version) {
        this.version = version;
    }

    /**
     * 
     * @return Der Name des Autors dieser Version
     */
    public String getAuthor() {
        return author;
    }

    /**
     * 
     * @param author Der Name des Autors dieser Version
     */
    public void setAuthor(String author) {
        this.author = author;
    }
}
