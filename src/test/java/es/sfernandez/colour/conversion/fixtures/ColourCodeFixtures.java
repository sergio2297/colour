package es.sfernandez.colour.conversion.fixtures;

import es.sfernandez.colour.codifications.ColourCode;

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

    interface HasOpacityFixtures<CODE extends ColourCode> {

        CODE grey25pctOpacity();
        CODE grey50pctOpacity();
        CODE grey75pctOpacity();
    }
}
