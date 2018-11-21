package com.dpancerz.nai;

import com.dpancerz.nai.base.TrainingSet;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static com.dpancerz.nai.Numbers.*;

class NumbersTrainingSet implements TrainingSet {
    private final NumbersToTrainingConverter converter;

    static final int NUMBER_OF_INPUTS = ONE.length();

    private static final Map<Integer, String> LEARNING_SET = new HashMap<>() {{
        put(1, ONE);
        put(2, TWO);
        put(3, THREE);
        put(4, FOUR);
        put(5, FIVE);
    }};

    private static final Map<String, Integer> TEST_SET = new HashMap<>() {{
        put(ONE_MODIFIED, 1);
        put(TWO_MODIFIED, 2);
        put(THREE_MODIFIED, 3);
        put(FOUR_MODIFIED, 4);
        put(FIVE_MODIFIED, 5);
        put(SIX_MAYBE_READ_AS_FIVE, 5);
    }};

    NumbersTrainingSet(NumbersToTrainingConverter converter) {
        this.converter = converter;
    }

    @Override
    public Stream<Map.Entry<double[], double[]>> trainingSet() {
        return LEARNING_SET.entrySet().stream()
                .map(e -> new AbstractMap.SimpleImmutableEntry<>(
                                converter.toInput(e.getValue()),
                                converter.toOutput(e.getKey())
                        )
                );
    }

    @Override
    public Stream<Map.Entry<double[], double[]>> verification() {
        return TEST_SET.entrySet().stream()
                .map(e -> new AbstractMap.SimpleImmutableEntry<>(
                                converter.toInput(e.getKey()),
                                converter.toOutput(e.getValue())
                        )
                );
    }
}
