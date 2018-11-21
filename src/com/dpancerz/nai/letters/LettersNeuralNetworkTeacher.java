package com.dpancerz.nai.letters;

import com.dpancerz.nai.base.Config;
import com.dpancerz.nai.base.NeuralNetwork;
import com.dpancerz.nai.base.NeuralNetworkFactory;
import com.dpancerz.nai.base.math.SigmoidFunction;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static com.dpancerz.nai.letters.LettersTrainingSet.NUMBER_OF_INPUTS;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

public class LettersNeuralNetworkTeacher {
    private final LettersTrainingSet trainingSet;
    private final LettersToTrainingConverter converter;

    static final int SECOND_LAYER_SIZE = 6;
    static final int FIRST_LAYER_SIZE = ((SECOND_LAYER_SIZE + NUMBER_OF_INPUTS) / 2) + 2;

    public LettersNeuralNetworkTeacher(LettersTrainingSet trainingSet,
                                       LettersToTrainingConverter converter) {
        this.trainingSet = trainingSet;
        this.converter = converter;
    }

    public void run() {
        NeuralNetwork network = createNetwork();
        trainTimes(100, network);
        verify(network);
    }

    private NeuralNetwork createNetwork() {
        return new NeuralNetworkFactory().createNetwork(
                new SigmoidFunction(0.5),
                Config.LEARNING_COEFFICIENT,
                NUMBER_OF_INPUTS,
                FIRST_LAYER_SIZE,
                SECOND_LAYER_SIZE
        );
    }

    private void verify(NeuralNetwork network) {
        log("testing training set:");
        trainingSet.trainingSet()
                .forEach(e -> test(network, e.getKey(), e.getValue()));

        log("testing test set:");
        trainingSet.verification()
                .forEach(e -> test(network, e.getKey(), e.getValue()));
    }

    private void test(NeuralNetwork network, double[] input, double[] category) {
        double[] result = network.test(input);
        boolean passes = maxesEqual(category, result);

        log(passes ? format("correctly recognized %s",
                        converter.fromOutputToCategory(category))
                : format("incorrectly recognized %s as %s",
                        converter.fromOutputToCategory(category),
                        converter.fromOutputToCategory(result)));
    }

    private boolean maxesEqual(double[] category, double[] result) {
        int a = findMax(category);
        int b = findMax(result);
        return a == b;
    }

    private int findMax(double[] result) {
        return converter.findMax(result);
    }

    private void trainTimes(int times, NeuralNetwork network) {
        IntStream.range(0, times)
                .forEach(i -> {
                    train(network);
                    logErrors(network, i + 1);
//                    log(network.toString());//TODO remove
                });
    }

    private void train(NeuralNetwork network) {
        List<Map.Entry<double[], double[]>> entries = trainingSet.trainingSet().collect(toList());
        Collections.shuffle(entries);
        entries.forEach(e -> network.train(e.getKey(), e.getValue()));
    }

    private void logErrors(NeuralNetwork network, int learningIterationsPassed) {
        trainingSet.trainingSet()
                .forEach(e -> logError(e, network, learningIterationsPassed));
    }

    private void logError(Map.Entry<double[], double[]> learningEntry, NeuralNetwork network, int learningIterationsPassed) {
        if (learningIterationsPassed % 20 != 0) {
            return;
        }
        double[] result = network.test(learningEntry.getKey());
        double[] expected = learningEntry.getValue();
        double[] difference = difference(expected, result);
        log(format(
                "Error after %s iterations for entry '%s': %s. Result: %s.",
                learningIterationsPassed,
                converter.fromOutputToCategory(learningEntry.getValue()),
                Arrays.toString(difference),
                Arrays.toString(result))
        );
    }

    private double[] difference(double[] expected, double[] actual) {
        double[] diff = new double[expected.length];
        for (int i = 0; i < expected.length ; i++) {
            diff[i] = expected[i] - actual[i];
        }
        return diff;
    }

    private void log(String message) {
        System.out.println(message);
    }
}
