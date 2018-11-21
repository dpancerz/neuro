package com.dpancerz.nai;

import com.dpancerz.nai.base.Config;
import com.dpancerz.nai.base.NeuralNetwork;
import com.dpancerz.nai.base.NeuralNetworkFactory;
import com.dpancerz.nai.base.math.SigmoidFunction;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.IntStream;

import static com.dpancerz.nai.NumbersTrainingSet.NUMBER_OF_INPUTS;
import static java.lang.String.format;

class NumbersNeuralNetworkTeacher {
    private final NumbersTrainingSet trainingSet;
    private final NumbersToTrainingConverter converter;

    static final int SECOND_LAYER_SIZE = 3;
    static final int FIRST_LAYER_SIZE = (SECOND_LAYER_SIZE + NUMBER_OF_INPUTS) / 2;

    public NumbersNeuralNetworkTeacher(NumbersTrainingSet trainingSet,
                                       NumbersToTrainingConverter converter) {
        this.trainingSet = trainingSet;
        this.converter = converter;
    }

    public void run() {
        NeuralNetwork network = createNetwork();
        trainTimes(5, network);
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
        boolean passes = Arrays.equals(category, result);

        log(passes ? format("correctly recognized %s",
                        converter.fromOutputToCategory(category))
                : format("incorrectly recognized %s as %s",
                        converter.fromOutputToCategory(category),
                        converter.fromOutputToCategory(result)));
    }

    private void trainTimes(int times, NeuralNetwork network) {
        IntStream.range(0, times)
                .forEach(i -> {
                    train(network);
                    logErrors(network, i);
                    log(network.toString());//TODO remove
                });
    }

    private void train(NeuralNetwork network) {
        trainingSet.trainingSet()
                .forEach(e -> network.train(e.getKey(), e.getValue()));
    }

    private void logErrors(NeuralNetwork network, int learningIterationsPassed) {
        trainingSet.trainingSet()
                .forEach(e -> logError(e, network, learningIterationsPassed));
    }

    private void logError(Map.Entry<double[], double[]> learningEntry, NeuralNetwork network, int learningIterationsPassed) {
        double[] result = network.test(learningEntry.getKey());
        double[] expected = learningEntry.getValue();
        double[] difference = difference(expected, result);
        log(format(
                "Error after %s iterations for entry '%s': %s.",
                learningIterationsPassed,
                converter.fromOutputToCategory(learningEntry.getKey()),
                Arrays.toString(difference))
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
