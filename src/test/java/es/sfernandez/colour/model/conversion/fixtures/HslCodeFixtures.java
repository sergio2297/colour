package es.sfernandez.colour.model.conversion.fixtures;

import es.sfernandez.colour.model.codifications.HslCode;

public final class HslCodeFixtures implements ColourCodeFixtures<HslCode>, ColourCodeFixtures.HasOpacityFixtures<HslCode> {

    @Override
    public Class<HslCode> colorCodeClass() {
        return HslCode.class;
    }

    @Override
    public HslCode white() {
        return new HslCode(0f, 0f, 1f);
    }

    @Override
    public HslCode black() {
        return new HslCode(0f, 0f, 0f);
    }

    @Override
    public HslCode grey() {
        return new HslCode(0f, 0f, 0.5f);
    }

    @Override
    public HslCode red() {
        return new HslCode(0f, 1f, 0.5f);
    }

    @Override
    public HslCode green() {
        return new HslCode(120, 100, 50);
    }

    @Override
    public HslCode blue() {
        return new HslCode(240, 100, 50);
    }

    @Override
    public HslCode cyan() {
        return new HslCode(180, 100, 50);
    }

    @Override
    public HslCode magenta() {
        return new HslCode(300, 100, 50);
    }

    @Override
    public HslCode yellow() {
        return new HslCode(60, 100, 50);
    }

    @Override
    public HslCode grey25pctOpacity() {
        return new HslCode(0f, 0f, 0.5f, 0.25f);
    }

    @Override
    public HslCode grey50pctOpacity() {
        return new HslCode(0f, 0f, 0.5f, 0.5f);
    }

    @Override
    public HslCode grey75pctOpacity() {
        return new HslCode(0f, 0f, 0.5f, 0.75f);
    }
}
