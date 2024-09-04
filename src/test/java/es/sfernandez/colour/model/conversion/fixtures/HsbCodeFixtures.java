package es.sfernandez.colour.model.conversion.fixtures;

import es.sfernandez.colour.model.codifications.HsbCode;

public final class HsbCodeFixtures implements ColourCodeFixtures<HsbCode>, ColourCodeFixtures.HasOpacityFixtures<HsbCode> {

    @Override
    public Class<HsbCode> colorCodeClass() {
        return HsbCode.class;
    }

    @Override
    public HsbCode white() {
        return new HsbCode(0f, 0f, 1f);
    }

    @Override
    public HsbCode black() {
        return new HsbCode(0f, 0f, 0f);
    }

    @Override
    public HsbCode grey() {
        return new HsbCode(0f, 0f, 0.5f);
    }

    @Override
    public HsbCode red() {
        return new HsbCode(0f, 1f, 1f);
    }

    @Override
    public HsbCode green() {
        return new HsbCode(120, 100, 100);
    }

    @Override
    public HsbCode blue() {
        return new HsbCode(240, 100, 100);
    }

    @Override
    public HsbCode cyan() {
        return new HsbCode(180, 100, 100);
    }

    @Override
    public HsbCode magenta() {
        return new HsbCode(300, 100, 100);
    }

    @Override
    public HsbCode yellow() {
        return new HsbCode(60, 100, 100);
    }

    @Override
    public HsbCode grey25pctOpacity() {
        return new HsbCode(0f, 0f, 0.5f, 0.25f);
    }

    @Override
    public HsbCode grey50pctOpacity() {
        return new HsbCode(0f, 0f, 0.5f, 0.5f);
    }

    @Override
    public HsbCode grey75pctOpacity() {
        return new HsbCode(0f, 0f, 0.5f, 0.75f);
    }
}
