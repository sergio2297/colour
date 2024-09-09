package es.sfernandez.colour.codifications;

import es.sfernandez.colour.Colour;

/**
 * <p>A {@link ColourCode} is used to represent a Colour codification.</p>
 * <p>Every {@link ColourCode} can be used to create a {@link Colour}.</p>
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
