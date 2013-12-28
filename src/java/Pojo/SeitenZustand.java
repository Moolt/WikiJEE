/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pojo;

/**
 *
 * @author Moolt
 */
public class SeitenZustand {

    private String pageURL;
    private String thema;

    /**
     * 
     * @param pageURL Eine relative Seiten-URL
     * @param thema Ein Name eines Wiki-Themas
     */
    public SeitenZustand(String pageURL, String thema) {
        this.pageURL = pageURL;
        this.thema = thema;
    }

    /**
     * 
     * @return Eine relative Seiten-URL
     */
    public String getPageURL() {
        return pageURL;
    }

    /**
     * 
     * @param pageURL Eine relative Seiten-URL
     */
    public void setPageURL(String pageURL) {
        this.pageURL = pageURL;
    }

    /**
     * 
     * @return Ein Name eines Wiki-Themas
     */
    public String getThema() {
        return thema;
    }

    /**
     * 
     * @param thema Ein Name eines Wiki-Themas
     */
    public void setThema(String thema) {
        this.thema = thema;
    }
}
