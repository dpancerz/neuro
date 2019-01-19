package com.dpancerz.nai.base;

import com.dpancerz.nai.base.config.Logger;
import com.dpancerz.nai.base.config.NeuralNetworkConfig;
import com.dpancerz.nai.base.config.TrainingDataConverter;
import com.dpancerz.nai.base.config.TrainingSet;

import java.util.*;
import java.util.stream.IntStream;

import static java.lang.String.format;
import static java.lang.String.join;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

public class NeuralNetworkTeacher {
    private final TrainingSet trainingSet;
    private final NeuralNetworkConfig config;
    private final TrainingDataConverter converter;
    private final Logger dataLogger;
    private final Logger resultLogger;
    private NeuralNetwork network;

    public NeuralNetworkTeacher(TrainingSet trainingSet,
                                NeuralNetworkConfig config,
                                TrainingDataConverter converter,
                                Logger dataLogger,
                                Logger resultLogger) {
        this.trainingSet = trainingSet;
        this.config = config;
        this.converter = converter;
        this.dataLogger = dataLogger;
        this.resultLogger = resultLogger;
    }

    public NeuralNetworkTeacher run() {
        init();
        train();
        logResultAfterRun(0);
        return this;
    }

    public NeuralNetworkTeacher reRunTimes(int times) {
        IntStream.range(2, times + 2)
                .forEach(i -> {
                    train();
                    logResultAfterRun(i);
                });
        return this;
    }

    private void init() {
        network = createNetwork(config);
    }

    private NeuralNetwork createNetwork(NeuralNetworkConfig config) {
        return new NeuralNetworkFactory().createNetwork(config);
    }

    private void train() {
        if (config.isTrainingFixedTimes()) {
            trainTimes(config.learningEpochs(), network);
        }
    }

    private void trainTimes(int times, NeuralNetwork network) {
        IntStream.range(0, times)
                .forEach(i -> {
                    train(network);
                    logErrors(network, i + 1);
                });
    }

    private void train(NeuralNetwork network) {
        List<Map.Entry<double[], double[]>> entries = trainingSet.trainingSet().collect(toList());
        Collections.shuffle(entries);
        entries.forEach(e -> network.train(e.getKey(), e.getValue()));
    }

    private void logResultAfterRun(int rerun) {
        int number = config.learningEpochs() * (rerun + 1);
        logResult(number + ";" +
                Double.toString(correctRecognitionFraction())
        );
    }

    private double correctRecognitionFraction() {
        return trainingSet.verification()
                .map(e -> test(network, e.getKey(), e.getValue()))
                .mapToDouble(recognized -> recognized ? 1.0 : 0.0)
                .average()
                .orElse(0.0);
    }

    private void logErrors(NeuralNetwork network, int learningIterationsPassed) {
        trainingSet.trainingSet()
                .forEach(e -> logError(e, network, learningIterationsPassed));
    }

    private void logError(Map.Entry<double[], double[]> learningEntry, NeuralNetwork network, int learningIterationsPassed) {
        if (learningIterationsPassed % 10 != 0) {
            return;
        }
        double[] result = network.test(learningEntry.getKey());
        double[] expected = learningEntry.getValue();
        double[] difference = difference(expected, result);
        logData(join(";",
                Integer.toString(learningIterationsPassed),
                converter.fromOutputToCategory(learningEntry.getValue()).toString(),
                asCsv(difference),
                asCsv(result))
        );
    }

    private String asCsv(double[] actual) {
        return Arrays.stream(actual)
                .mapToObj(Double::toString)
                .reduce((a, b) -> join(";", a, b))
                .orElse("");
    }

    private double[] difference(double[] expected, double[] actual) {
        double[] diff = new double[expected.length];
        for (int i = 0; i < expected.length ; i++) {
            diff[i] = expected[i] - actual[i];
        }
        return diff;
    }

    private void logData(String message) {
        dataLogger.log(message);
    }

    private boolean test(NeuralNetwork network, double[] input, double[] category) {
        double[] result = network.test(input);
        return maxesEqual(category, result);
    }

    private void logResult(String message) {
        resultLogger.log(message);
    }

    private boolean maxesEqual(double[] category, double[] result) {
        int a = findMax(category);
        int b = findMax(result);
        return a == b;
    }

    private int findMax(double[] result) {
        return converter.findMax(result);
    }
}
