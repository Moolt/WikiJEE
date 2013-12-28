/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Moolt
 */
@FacesConverter("Converter.LinkConverter")
public class LinkConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext,
            UIComponent component, String value) {

        LinkData urlData = new LinkData(value.toString());
        return urlData;
    }

    @Override
    public String getAsString(FacesContext facesContext,
            UIComponent component, Object value) {

        //Matcht alle Teilausdruecke, die mit [ beginnen und mit ] enden und innerhalb
        //nicht die zeichen [ oder ] verwenden und wandelt sie in html-link form um
        return value.toString().replaceAll("\\[([^\\[\\]]*)\\]", "<a href=\"./show.xhtml?thema=$1\">$1</a>");
    }
}
