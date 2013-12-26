/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Validator;

import View.ThemaShow;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;

/**
 *
 * @author Moolt
 */
@FacesValidator("Validator.VersionValidator")
public class VersionValidator implements Validator {
    @Inject
    ThemaShow show;
    
    
    //eine folge von zeichen und in [ ]-umschlossenen zeichen
    private static final String NUMERIC_PATTERN = "^\\d+$";

    private Pattern pattern;
    private Matcher matcher;

    public VersionValidator() {
        pattern = Pattern.compile(NUMERIC_PATTERN);
    }

    @Override
    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        
        if(value != null){
            matcher = pattern.matcher(value.toString());
            if (matcher.matches()) {
                Integer input = Integer.parseInt(value.toString());
                if(!(input > 0 && input <= show.getAktuelleVersion())){
                    FacesMessage msg = new FacesMessage("Version nicht vorhanden.");
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(msg);
                }
            }
        }
    }
}
