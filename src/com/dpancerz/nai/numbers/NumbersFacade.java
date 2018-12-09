package com.dpancerz.nai.numbers;

import com.dpancerz.nai.base.NeuralNetworkTeacher;
import com.dpancerz.nai.base.config.NeuralNetworkConfig;
import com.dpancerz.nai.base.math.SigmoidFunction;

import java.util.Arrays;

import static com.dpancerz.nai.numbers.NumbersTrainingSet.NUMBER_OF_INPUTS;

public class NumbersFacade {
    static final int SECOND_LAYER_SIZE = 5;
    static final int FIRST_LAYER_SIZE = ((SECOND_LAYER_SIZE + NUMBER_OF_INPUTS) / 2) + 2;

    public static void teachNumbersNeuralNetwork() {
        NumbersToTrainingConverter converter = new NumbersToTrainingConverter();

        NeuralNetworkTeacher teacher = new NeuralNetworkTeacher(
                new NumbersTrainingSet(converter),
                new NeuralNetworkConfig(NUMBER_OF_INPUTS,
                        Arrays.asList(FIRST_LAYER_SIZE, SECOND_LAYER_SIZE),
                        0.5,
                        new SigmoidFunction(1),
                        100,
                        true
                ),
                converter,
                System.out::println
        );
        teacher.run();
    }
}
