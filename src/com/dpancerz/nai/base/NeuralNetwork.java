package com.dpancerz.nai.base;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

import static java.lang.String.format;

class NeuralNetwork {
    private final LinkedList<NeuralLayer> layers;
    private final List<NeuralNetworkOutput> outputHolders;
    private final int inputSize;
    private final int outputSize;
    private double learningCoefficient;

    NeuralNetwork(LinkedList<NeuralLayer> layers,
                  double learningCoefficient) {
        this.layers = layers;
        this.inputSize = layers.getFirst().inputSize();
        this.outputSize = layers.getLast().size();
        this.outputHolders = createOutputHolders(layers.getLast());
        this.learningCoefficient = learningCoefficient;
    }

    double[] output() {
        return outputHolders.stream()
                .mapToDouble(ValueHolder::value)
                .toArray();
    }

    public double[] test(double[] input) {
        pushThrough(input);
        return output();
    }

    public void train(double[] input, double[] expectedOutput) {
        checkPreconditions(input, expectedOutput);
        pushThrough(input);
        calculateErrors(expectedOutput);
        learn();
    }

    void setLearningCoefficient(double learningCoefficient) {
        this.learningCoefficient = learningCoefficient;
    }

    private void pushThrough(double[] input) {
        setSignals(input);
        layers.forEach(NeuralLayer::propagate);
    }

    private void calculateErrors(double[] expectedOutput) {
        IntStream.range(0, expectedOutput.length)
                .forEach(i -> outputHolders.get(i)
                                .setExpectedValue(expectedOutput[i]));

        Iterator<NeuralLayer> reverseIterator = layers.descendingIterator();
        while (reverseIterator.hasNext()) {
            reverseIterator.next().calculateErrors();
        }
    }

    private void learn() {
        layers.forEach(NeuralLayer::learn);
    }

    private List<NeuralNetworkOutput> createOutputHolders(NeuralLayer last) {
        LinkedList<NeuralNetworkOutput> valueHolders = new LinkedList<>();
        for (Neuron neuron: last.neurons()) {
            NeuralNetworkOutput listener = new NeuralNetworkOutput();
            neuron.addListener(listener);
            valueHolders.addLast(listener);
        }
        return valueHolders;
    }

    private void setSignals(double[] input) {
        layers.getFirst()
                .neurons()
                .forEach(neuron -> neuron.setInput(input));
    }

    private void checkPreconditions(double[] input, double[] expectedOutput) {
        if (failsPreconditions(input, expectedOutput)) {
            throw new RuntimeException(format(
                    "Not fitting sizes: expected input size: %s, actual: %s; expected output size: %s, actual: %s",
                    inputSize, input.length, outputSize, expectedOutput.length));
        }
    }

    private boolean failsPreconditions(double[] input, double[] expectedOutput) {
        return input.length != inputSize
                || expectedOutput.length != outputSize;
    }

    @Override
    public String toString() {
        return "NeuralNetwork{" +
                "layers=" + layers +
                ", outputHolders=" + outputHolders +
                ", inputSize=" + inputSize +
                ", outputSize=" + outputSize +
                ", learningCoefficient=" + learningCoefficient +
                '}';
    }
}
