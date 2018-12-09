package com.dpancerz.nai.letters;

import com.dpancerz.nai.base.config.BaseZeroOneDataConverter;
import com.dpancerz.nai.base.config.ZeroAndOneSymbols;

import static com.dpancerz.nai.letters.Letters.ONE_CHAR;
import static com.dpancerz.nai.letters.Letters.ZERO_CHAR;

class LettersToTrainingConverter extends BaseZeroOneDataConverter<Character> {
    protected LettersToTrainingConverter() {
        super(new ZeroAndOneSymbols(ZERO_CHAR, ONE_CHAR), 6);
    }

    @Override
    public Character fromOutputToCategory(double[] result) {
        return (char) ('a' + findMax(result));
    }
}
