package es.sfernandez.colour.conversion.fixtures;

import es.sfernandez.colour.codifications.HsvCode;

public final class HsbCodeFixtures implements ColourCodeFixtures<HsvCode>, ColourCodeFixtures.HasOpacityFixtures<HsvCode> {

    @Override
    public Class<HsvCode> colorCodeClass() {
        return HsvCode.class;
    }

    @Override
    public HsvCode white() {
        return new HsvCode(0f, 0f, 1f);
    }

    @Override
    public HsvCode black() {
        return new HsvCode(0f, 0f, 0f);
    }

    @Override
    public HsvCode grey() {
        return new HsvCode(0f, 0f, 0.5f);
    }

    @Override
    public HsvCode red() {
        return new HsvCode(0f, 1f, 1f);
    }

    @Override
    public HsvCode green() {
        return new HsvCode(120, 100, 100);
    }

    @Override
    public HsvCode blue() {
        return new HsvCode(240, 100, 100);
    }

    @Override
    public HsvCode cyan() {
        return new HsvCode(180, 100, 100);
    }

    @Override
    public HsvCode magenta() {
        return new HsvCode(300, 100, 100);
    }

    @Override
    public HsvCode yellow() {
        return new HsvCode(60, 100, 100);
    }

    @Override
    public HsvCode grey25pctOpacity() {
        return new HsvCode(0f, 0f, 0.5f, 0.25f);
    }

    @Override
    public HsvCode grey50pctOpacity() {
        return new HsvCode(0f, 0f, 0.5f, 0.5f);
    }

    @Override
    public HsvCode grey75pctOpacity() {
        return new HsvCode(0f, 0f, 0.5f, 0.75f);
    }
}
