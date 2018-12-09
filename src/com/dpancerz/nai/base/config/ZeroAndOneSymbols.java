package com.dpancerz.nai.base.config;

public class ZeroAndOneSymbols {
    private final char zeroSymbol;
    private final char oneSymbol;

    public ZeroAndOneSymbols(char zeroSymbol, char oneSymbol) {
        this.zeroSymbol = zeroSymbol;
        this.oneSymbol = oneSymbol;
    }

    public char zeroSymbol() {
        return zeroSymbol;
    }

    public char oneSymbol() {
        return oneSymbol;
    }
}
