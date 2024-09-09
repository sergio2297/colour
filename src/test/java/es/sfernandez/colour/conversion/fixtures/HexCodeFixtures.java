package es.sfernandez.colour.conversion.fixtures;

import es.sfernandez.colour.codifications.HexCode;

public final class HexCodeFixtures implements ColourCodeFixtures<HexCode>, ColourCodeFixtures.HasOpacityFixtures<HexCode> {

    @Override
    public Class<HexCode> colorCodeClass() {
        return HexCode.class;
    }

    @Override
    public HexCode white() {
        return new HexCode("#FFFFFF");
    }

    @Override
    public HexCode black() {
        return new HexCode("#000000");
    }

    @Override
    public HexCode grey() {
        return new HexCode("#808080");
    }

    @Override
    public HexCode red() {
        return new HexCode("#F00");
    }

    @Override
    public HexCode green() {
        return new HexCode("#0F0");
    }

    @Override
    public HexCode blue() {
        return new HexCode("#00F");
    }

    @Override
    public HexCode cyan() {
        return new HexCode("#00FFFF");
    }

    @Override
    public HexCode magenta() {
        return new HexCode("#FF00FF");
    }

    @Override
    public HexCode yellow() {
        return new HexCode("#FFFF00");
    }

    @Override
    public HexCode grey25pctOpacity() {
        return new HexCode("#80808040");
    }

    @Override
    public HexCode grey50pctOpacity() {
        return new HexCode("#80808080");
    }

    @Override
    public HexCode grey75pctOpacity() {
        return new HexCode("#808080BF");
    }
}
