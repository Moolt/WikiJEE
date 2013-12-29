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
 *
 * @author Moolt
 */
@FacesValidator("Validator.EditorValidator")
public class EditorValidator implements Validator {

    //an dieser stelle haette man eigentlich einen regulaeren ausdruck verwenden koennen
    //allerdigs gibt es bei groesseren ausdruecken eine stackoverflow exception
    //eine folge von zeichen und in [ ]-umschlossenen zeichen
    //private static final String LINK_PATTERN = "(\\[[^\\[\\]]+\\]|[^\\[\\]])*";
    @Inject
    private ThemenView tv;
//    private final Pattern pattern;
    private Matcher matcher;

    public EditorValidator() {
        //pattern = Pattern.compile(LINK_PATTERN);
    }

    /**
     * Validiert den eingegebenen Text auf die Einhaltung der Klammer-Regeln
     * Klammern duerfen nicht [[verschachtelt]] sein und muessen immer
     * [geoeffnet und wieder geschlossen] werden. Desweiteren dueften Klammern
     * nicht leer [] sein Es wird ebenfalls ueberprueft, ob ein Nutzername
     * eingegeben wurde, da ein Autor erforderlich ist
     *
     * @param context
     * @param component
     * @param value Der zu validierende Text
     * @throws ValidatorException
     */
    @Override
    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        String text = value.toString();
        //matcher = pattern.matcher(value.toString());

        if (!klammerTest(text)) {
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

    /**
     * 
     * @param text Der Text, dessen Klammerregeln ueberprueft werden sollen
     * @return True, wenn die Regeln eingehalten wurden
     */
    private boolean klammerTest(String text) {
        int klammerAuf = 0;
        int klammerZu = 0;
        for (int i = 0; i < text.length(); i++) {
            switch (text.charAt(i)) {
                case '[':
                    klammerAuf++;
                    break;
                case ']':
                    klammerZu++;
                    break;
            }
            //klammern in klammern
            if (klammerAuf > 1 || klammerZu > 1) {
                return false;
            }
            //geschlossene klammer vor geoeffneter klammer
            if (klammerAuf == 0 && klammerZu > 0) {
                return false;
            }
            //leere klammer
            if (klammerAuf == 1 && klammerZu == 1) {
                if (i > 0 && text.charAt(i - 1) == '[') {
                    return false;
                }
            }
            //richtig geklammert
            if (klammerAuf == 1 && klammerZu == 1) {
                klammerAuf = klammerZu = 0;
            }
        }
        return klammerAuf == klammerZu;
    }
}
