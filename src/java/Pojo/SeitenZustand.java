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

    public SeitenZustand(String pageURL, String thema) {
        this.pageURL = pageURL;
        this.thema = thema;
    }

    public String getPageURL() {
        return pageURL;
    }

    public void setPageURL(String pageURL) {
        this.pageURL = pageURL;
    }

    public String getThema() {
        return thema;
    }

    public void setThema(String thema) {
        this.thema = thema;
    }
}
