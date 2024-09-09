package es.sfernandez.colour.codifications;

/**
 * <p>Indicates that a {@link ColourCode} is accepted by CSS properties. For example, for background or font color.</p>
 * @author Sergio Fernández
 */
public interface AcceptedByCssColourCode
        extends ColourCode, HasOpacity {

    /**
     * @return the CSS representation of the {@link ColourCode}
     */
    String toCssCode();
    
}
