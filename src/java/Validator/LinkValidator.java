/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Moolt
 */
@FacesValidator("Validator.LinkValidator")
public class LinkValidator implements Validator {
    //eine folge von zeichen und in [ ]-umschlossenen zeichen
    private static final String LINK_PATTERN = "(\\[[^\\[\\]<]+\\]|[^\\[\\]<])*";

    private Pattern pattern;
    private Matcher matcher;

    public LinkValidator() {
        pattern = Pattern.compile(LINK_PATTERN);
    }

    @Override
    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {

        matcher = pattern.matcher(value.toString());
        if (!matcher.matches()) {

            FacesMessage msg = new FacesMessage("Gewünschte Textform nicht eingehalten.");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }
}
