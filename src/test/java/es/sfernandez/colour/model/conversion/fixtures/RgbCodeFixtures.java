package es.sfernandez.colour.model.conversion.fixtures;

import es.sfernandez.colour.model.codifications.RgbCode;

public final class RgbCodeFixtures implements ColourCodeFixtures<RgbCode> {

    @Override
    public Class<RgbCode> colorCodeClass() {
        return RgbCode.class;
    }

    @Override
    public RgbCode white() {
        return new RgbCode(255, 255, 255);
    }

    @Override
    public RgbCode black() {
        return new RgbCode(0, 0, 0);
    }

    @Override
    public RgbCode grey() {
        return new RgbCode(128, 128, 128);
    }

    @Override
    public RgbCode red() {
        return new RgbCode(255, 0, 0);
    }

    @Override
    public RgbCode green() {
        return new RgbCode(0, 255, 0);
    }

    @Override
    public RgbCode blue() {
        return new RgbCode(0, 0, 255);
    }

    @Override
    public RgbCode cyan() {
        return new RgbCode(0, 255, 255);
    }

    @Override
    public RgbCode magenta() {
        return new RgbCode(255, 0, 255);
    }

    @Override
    public RgbCode yellow() {
        return new RgbCode(255, 255, 0);
    }
}
