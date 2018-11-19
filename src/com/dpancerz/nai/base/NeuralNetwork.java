package com.dpancerz.nai.base;

import java.util.LinkedList;
import java.util.List;

import static java.lang.String.format;

public class NeuralNetwork {
    private final LinkedList<NeuralLayer> layers;
    private final List<ValueHolder> outputHolders;
    private final int inputSize;
    private final int outputSize;

    public NeuralNetwork(LinkedList<NeuralLayer> layers) {
        this.layers = layers;
        this.inputSize = layers.getFirst().inputSize();
        this.outputSize = layers.getLast().size();
        this.outputHolders = createOutputHolders(layers.getLast());
    }


    private List<ValueHolder> createOutputHolders(NeuralLayer last) {
        LinkedList<ValueHolder> valueHolders = new LinkedList<>();
        for (Neuron neuron: last.neurons()) {
            OutputHolder listener = new OutputHolder();
            neuron.addListener(listener);
            valueHolders.addLast(listener);
        }
        return valueHolders;
    }

    public double[] output() {
        return outputHolders.stream()
                .mapToDouble(ValueHolder::value)
                .toArray();
    }

    public double[] test(double[] input) {
        return null;
    }

    void learn(double[] input, double[] expectedOutput) {
        checkPreconditions(input, expectedOutput);


        layers.forEach(NeuralLayer::propagate);
//        boolean test = layers.getLast().test(expectedOutput);

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

    private void log(String message) {
        System.out.println(message);
    }
}
