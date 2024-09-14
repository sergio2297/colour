package es.sfernandez.colour.codifications;

import es.sfernandez.colour.Colour;

/**
 * <p>A ColourCode is used to represent a colour in a specific codification.</p>
 * <p>Every {@link ColourCode} can be used to create a {@link Colour}, either passing it as argument to Colour class, or
 * calling directly {@link #toColour()}.</p>
 *
 * @author Sergio Fernández
 */
public interface ColourCode {

    /**
     * @return a new {@link Colour} made from this {@link ColourCode}
     */
    default Colour toColour() {
        return new Colour(this);
    }

}
