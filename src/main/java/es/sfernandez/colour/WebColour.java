package es.sfernandez.colour;

import es.sfernandez.colour.codifications.AcceptedByCssColourCode;
import es.sfernandez.colour.codifications.HexCode;
import es.sfernandez.colour.codifications.HslCode;
import es.sfernandez.colour.codifications.RgbCode;
import es.sfernandez.colour.conversion.ColourCodeConverter;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

public class WebColour
        extends Colour {

    //---- Constructor ----
    public WebColour(AcceptedByCssColourCode code) {
        super(code);
    }

    public WebColour(AcceptedByCssColourCode code, ColourCodeConverter converter) {
        super(code, converter);
    }

    //---- Methods ----
    public String toCssCode() {
        return ((AcceptedByCssColourCode) code).toCssCode();
    }

    public static boolean isCssColourCode(final String cssColourCode) {
        return colourCodeFrom(cssColourCode).isPresent();
    }

    public static WebColour from(final String cssColourCode) {
        return colourCodeFrom(cssColourCode)
                .map(WebColour::new)
                .orElseThrow(() -> new IllegalArgumentException("Error. '" + cssColourCode + "' is not a valid CSS colour code."));
    }

    private static Optional<AcceptedByCssColourCode> colourCodeFrom(final String cssColourCode) {
        for(Class<? extends AcceptedByCssColourCode> clazz : acceptedByCssColourCodeClasses()) {
            try {
                return Optional.of(clazz.getConstructor(String.class).newInstance(cssColourCode));
            } catch (Exception ex) {
                // If exception comes from invocation, and it is an IllegalArgumentException, it means that the
                // cssColourCode is not accepted by CSS. Otherwise, class won't be correctly developed
                if(!(ex instanceof InvocationTargetException invocationException)
                        || !(invocationException.getTargetException() instanceof IllegalArgumentException))
                    throw new RuntimeException(ex);
            }
        }

        return Optional.empty();
    }

    private static Iterable<Class<? extends AcceptedByCssColourCode>> acceptedByCssColourCodeClasses() {
        return List.of(
                RgbCode.class, HexCode.class, HslCode.class
        );
    }
}
