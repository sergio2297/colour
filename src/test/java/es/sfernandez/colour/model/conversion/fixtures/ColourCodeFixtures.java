package es.sfernandez.colour.model.conversion.fixtures;

import es.sfernandez.colour.model.codifications.ColourCode;

public interface ColourCodeFixtures<CODE extends ColourCode> {

    Class<CODE> colorCodeClass();
    CODE white();
    CODE black();
    CODE grey();

    CODE red();
    CODE green();
    CODE blue();

    CODE cyan();
    CODE magenta();
    CODE yellow();

}
