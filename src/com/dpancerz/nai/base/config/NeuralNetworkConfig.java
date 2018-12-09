package com.dpancerz.nai.base.config;

import com.dpancerz.nai.base.math.ActivationFunction;

import java.util.List;
import java.util.Objects;

public class NeuralNetworkConfig {
    private final int numberOfInputs;
    private final int[] layerSizes;
    private final double learningCoefficient;
    private final ActivationFunction activationFunction;
    private final int learningIterations;
    private final boolean trainFixedTimes;

    public NeuralNetworkConfig(int numberOfInputs,
                               List<Integer> layerSizes,
                               double learningCoefficient,
                               ActivationFunction activationFunction,
                               int learningIterations,
                               boolean trainFixedTimes) {
        this.numberOfInputs = numberOfInputs;
        this.layerSizes = toIntArray(layerSizes);
        this.learningCoefficient = learningCoefficient;
        this.activationFunction = activationFunction;
        this.learningIterations = learningIterations;
        this.trainFixedTimes = trainFixedTimes;
    }

    public int numberOfInputs() {
        return numberOfInputs;
    }

    public int[] layerSizes() {
        return layerSizes;
    }

    public double learningCoefficient() {
        return learningCoefficient;
    }

    public ActivationFunction activationFunction() {
        return activationFunction;
    }

    public boolean isTrainingFixedTimes() {
        return trainFixedTimes;
    }

    public int learningIterations() {
        return learningIterations;
    }

    private int[] toIntArray(List<Integer> intList) {
        return intList.stream()
                .filter(Objects::nonNull)
                .mapToInt(i -> i)
                .toArray();
    }
}
