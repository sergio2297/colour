package es.sfernandez.colour.codifications;

/**
 * <p>Indicates that a {@link ColourCode} is accepted as value in CSS properties. For example, in could be used as
 * background or font color.</p>
 * <p>Every AcceptedByCssColourCode implements at the same time {@link HasOpacity}.</p>
 *
 * @author Sergio Fernández
 */
public interface AcceptedByCssColourCode
        extends ColourCode, HasOpacity {

    /**
     * @return the CSS representation of the {@link ColourCode}
     */
    String toCssCode();
    
}
