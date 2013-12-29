package Pojo;

/**
 * Speichert den Zustand einer Seite, sodass dieser spaeter ueber einen Zurueck-Button 
 * wiederhergestellt werden kann
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
