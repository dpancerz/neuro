package com.dpancerz.nai;

import com.dpancerz.nai.base.NeuralNetworkFactory;
import com.dpancerz.nai.base.activation.SigmoidFunction;

import java.util.HashMap;
import java.util.Map;

public class Numbers {
    private static final char OPTICAL_REPRESENTATION_OF_ONE = '@';
    private static final char OPTICAL_REPRESENTATION_OF_ZERO = ' ';

    // @formatter:off
    private static final String ONE =
                "  @ "
              + "  @ "
              + "  @ "
              + "  @ "
              + "  @ "
              + "  @ ";
    private static final String TWO =
                "@@@ "
              + "   @"
              + "  @ "
              + " @  "
              + "@   "
              + "@@@@";
    private static final String THREE =
                "@@@ "
              + "   @"
              + " @@@"
              + "   @"
              + "   @"
              + "@@@@";
    private static final String FOUR =
                "@   "
              + "@   "
              + "@ @ "
              + "@@@@"
              + "  @ "
              + "  @ ";
    private static final String FIVE =
                "@@@@"
              + "@   "
              + "@@@ "
              + "   @"
              + "   @"
              + "@@@ ";
    // @formatter:on

    private static final Map<Integer, String> LEARNING_SET = new HashMap<>(){{
        put(1, ONE);
        put(2, TWO);
        put(3, THREE);
        put(4, FOUR);
        put(5, FIVE);
    }};

    private static final int NUMBER_OF_INPUTS = 24;
    private static final int SECOND_LAYER_SIZE = 3;
    private static final int FIRST_LAYER_SIZE = (SECOND_LAYER_SIZE + NUMBER_OF_INPUTS) / 2;

    public static void main(String[] args) {
        Numbers numbers = new Numbers();
        numbers.train();
    }

    private void train() {
        new NeuralNetworkFactory().createNetwork(
                new SigmoidFunction(0.5),
                FIRST_LAYER_SIZE,
                SECOND_LAYER_SIZE
        );

    }

    private void train(int expectedResult, String trainingObject) {

    }
}
