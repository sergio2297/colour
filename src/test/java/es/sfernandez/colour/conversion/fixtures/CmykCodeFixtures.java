package es.sfernandez.colour.conversion.fixtures;

import es.sfernandez.colour.codifications.CmykCode;

public final class CmykCodeFixtures implements ColourCodeFixtures<CmykCode> {

    @Override
    public Class<CmykCode> colorCodeClass() {
        return CmykCode.class;
    }

    @Override
    public CmykCode white() {
        return new CmykCode(0, 0, 0, 0);
    }

    @Override
    public CmykCode black() {
        return new CmykCode(0, 0, 0, 100);
    }

    @Override
    public CmykCode grey() {
        return new CmykCode(0, 0, 0, 50);
    }

    @Override
    public CmykCode red() {
        return new CmykCode(0, 100, 100, 0);
    }

    @Override
    public CmykCode green() {
        return new CmykCode(100, 0, 100, 0);
    }

    @Override
    public CmykCode blue() {
        return new CmykCode(100, 100, 0, 0);
    }

    @Override
    public CmykCode cyan() {
        return new CmykCode(100, 0, 0, 0);
    }

    @Override
    public CmykCode magenta() {
        return new CmykCode(0, 100, 0, 0);
    }

    @Override
    public CmykCode yellow() {
        return new CmykCode(0, 0, 100, 0);
    }
}
