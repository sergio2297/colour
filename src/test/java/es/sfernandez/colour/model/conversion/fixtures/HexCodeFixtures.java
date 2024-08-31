package es.sfernandez.colour.model.conversion.fixtures;

import es.sfernandez.colour.model.codifications.HexCode;

public final class HexCodeFixtures implements ColourCodeFixtures<HexCode> {

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
}
