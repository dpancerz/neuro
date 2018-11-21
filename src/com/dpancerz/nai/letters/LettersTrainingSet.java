package com.dpancerz.nai.letters;

import com.dpancerz.nai.base.TrainingSet;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static com.dpancerz.nai.letters.Letters.*;

class LettersTrainingSet implements TrainingSet {
    private final LettersToTrainingConverter converter;

    static final int NUMBER_OF_INPUTS = A.length();

    private static final Map<Integer, String> LEARNING_SET = new HashMap<>() {{
        put(1, A);
        put(2, B);
        put(3, C);
        put(4, D);
        put(5, E);
        put(6, F);
    }};

    private static final Map<String, Integer> TEST_SET = new HashMap<>() {{
        put(A_MODIFIED, 1);
        put(B_MODIFIED, 2);
        put(C_MODIFIED, 3);
        put(D_MODIFIED, 4);
        put(E_MODIFIED, 5);
        put(F_MODIFIED, 6);
    }};

    LettersTrainingSet(LettersToTrainingConverter converter) {
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
