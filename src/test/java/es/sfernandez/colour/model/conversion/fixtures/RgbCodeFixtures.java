package es.sfernandez.colour.model.conversion.fixtures;

import es.sfernandez.colour.model.codifications.RgbCode;

public final class RgbCodeFixtures implements ColourCodeFixtures<RgbCode> {

    @Override
    public Class<RgbCode> colorCodeClass() {
        return RgbCode.class;
    }

    @Override
    public RgbCode white() {
        return new RgbCode(1.0f, 1.0f, 1.0f);
    }

    @Override
    public RgbCode black() {
        return new RgbCode(0.0f, 0.0f, 0.0f);
    }

    @Override
    public RgbCode grey() {
        return new RgbCode(0.5f, 0.5f, 0.5f);
    }

    @Override
    public RgbCode red() {
        return new RgbCode(1.0f, 0.0f, 0.0f);
    }

    @Override
    public RgbCode green() {
        return new RgbCode(0.0f, 1.0f, 0.0f);
    }

    @Override
    public RgbCode blue() {
        return new RgbCode(0.0f, 0.0f, 1.0f);
    }

    @Override
    public RgbCode cyan() {
        return new RgbCode(0.0f, 1.0f, 1.0f);
    }

    @Override
    public RgbCode magenta() {
        return new RgbCode(1.0f, 0.0f, 1.0f);
    }

    @Override
    public RgbCode yellow() {
        return new RgbCode(1.0f, 1.0f, 0.0f);
    }
}
