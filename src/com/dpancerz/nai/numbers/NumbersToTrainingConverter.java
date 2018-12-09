package com.dpancerz.nai.numbers;

import com.dpancerz.nai.base.config.BaseZeroOneDataConverter;
import com.dpancerz.nai.base.config.ZeroAndOneSymbols;

import static com.dpancerz.nai.numbers.Numbers.ONE_CHAR;
import static com.dpancerz.nai.numbers.Numbers.ZERO_CHAR;

class NumbersToTrainingConverter extends BaseZeroOneDataConverter<Integer> {
    protected NumbersToTrainingConverter() {
        super(new ZeroAndOneSymbols(ZERO_CHAR, ONE_CHAR), 6);
    }

    @Override
    public Integer fromOutputToCategory(double[] result) {
        return findMax(result) + 1;
    }
}
