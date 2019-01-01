package com.dpancerz.nai.movement;

public class MovementNeuralNetworkConfig {
    private final double learningCoefficient;
    private final int numberOfEpochs;
    private final int hiddenLayerSize;

    private MovementNeuralNetworkConfig(double learningCoefficient,
                                       int numberOfEpochs,
                                       int hiddenLayerSize) {
        this.learningCoefficient = learningCoefficient;
        this.numberOfEpochs = numberOfEpochs;
        this.hiddenLayerSize = hiddenLayerSize;
    }

    public double getLearningCoefficient() {
        return learningCoefficient;
    }

    public int getNumberOfEpochs() {
        return numberOfEpochs;
    }

    public int getHiddenLayerSize() {
        return hiddenLayerSize;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private double learningCoefficient;
        private int numberOfEpochs;
        private int hiddenLayerSize;

        public Builder learningCoefficient(double learningCoefficient) {
            this.learningCoefficient = learningCoefficient;
            return this;
        }

        public Builder numberOfEpochs(int numberOfEpochs) {
            this.numberOfEpochs = numberOfEpochs;
            return this;
        }

        public Builder hiddenLayerSize(int hiddenLayerSize) {
            this.hiddenLayerSize = hiddenLayerSize;
            return this;
        }

        public MovementNeuralNetworkConfig build() {
            return new MovementNeuralNetworkConfig(learningCoefficient,
                    numberOfEpochs, hiddenLayerSize);
        }
    }
}
