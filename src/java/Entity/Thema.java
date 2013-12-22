package Entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
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

    public Thema() {
        contentSet = new HashSet<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Content> getContentSet() {
        return contentSet;
    }

    public void setContentSet(Set<Content> contentSet) {
        this.contentSet = contentSet;
    }
    
    public void addContent(Content content){
        this.contentSet.add(content);
    }
    
    public int getLatestVersion(){
        int version = -1;
        for(Content v: contentSet){
            if(v.getVersion() > version){
                version = v.getVersion();
            }
        }
        return version;
    }
    
    public Content getContent(int version){
        for(Content v: contentSet){
            if(v.getVersion() == version){
                return v;
            }
        }
        return null;
    }
}
