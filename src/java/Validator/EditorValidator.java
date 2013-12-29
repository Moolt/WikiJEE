package Validator;

import View.ThemenView;
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
 * Validator fuer den Primefaces-Editor
 * @author Moolt
 */
@FacesValidator("Validator.EditorValidator")
public class EditorValidator implements Validator {

    //eine folge von zeichen und in [ ]-umschlossenen zeichen
    private static final String LINK_PATTERN = "(\\[[^\\[\\]]+\\]|[^\\[\\]])*";
    @Inject
    private ThemenView tv;
    private final Pattern pattern;
    private Matcher matcher;

    public EditorValidator() {
        pattern = Pattern.compile(LINK_PATTERN);
    }

    /**
     * Validiert den eingegebenen Text auf die Einhaltung der Klammer-Regeln
     * Klammern duerfen nicht [[verschachtelt]] sein und muessen immer [geoeffnet 
     * und wieder geschlossen] werden. Desweiteren dueften Klammern nicht leer [] sein
     * Es wird ebenfalls ueberprueft, ob ein Nutzername eingegeben wurde, da ein Autor
     * erforderlich ist
     * @param context 
     * @param component
     * @param value Der zu validierende Text
     * @throws ValidatorException 
     */
    @Override
    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {

        matcher = pattern.matcher(value.toString());
        if (!matcher.matches()) {

            FacesMessage msg = new FacesMessage("Gewünschte Textform nicht eingehalten.");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
        if (!tv.nameVorhanden()) {
            FacesMessage msg = new FacesMessage("Kein Name eingegeben.", "Beitrag wurde nicht editiert.");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }
}
