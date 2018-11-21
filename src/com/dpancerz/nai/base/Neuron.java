package com.dpancerz.nai.base;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

class Neuron {
    private final Axon axon;
    private final List<Dendrite> dendrites;
    private final Dendrite bias;
    private final ActivationFunction activationFunction;
    private final double learningCoefficient;
    private double signal;
    private double error;

    Neuron(ActivationFunction activationFunction,
           List<Dendrite> dendrites, Dendrite bias,
           double learningCoefficient) {
        this.axon = new Axon();
        this.dendrites = dendrites;
        this.activationFunction = activationFunction;
        this.learningCoefficient = learningCoefficient;
        this.bias = bias;
    }

    Neuron(ActivationFunction activationFunction,
           double learningCoefficient) {
        this.axon = new Axon();
        this.dendrites = new ArrayList<>();
        this.activationFunction = activationFunction;
        this.learningCoefficient = learningCoefficient;
        this.bias = new Dendrite();
        bias.setValue(1.0);
    }

    int inputSize() {
        return visibleDendrites().size();
    }

    List<Dendrite> visibleDendrites() {
        return dendrites;
    }

    void addListener(Neuron listener) {
        addListener(
                listener.newDendrite());
    }

    void addListener(ValueHolder listener) {
        this.axon.addOutput(listener);
    }

    void setInput(double[] input) { //I really don't like this method- it's against regular flow
        IntStream.range(0, input.length)
                .forEach(i ->
                        visibleDendrites().get(i).setValue(input[i])
                );
    }

    void propagate() {
        this.signal = calculateInputSum();
        double calculated = activationFunction.applyAsDouble(
                this.signal
        );
        axon.propagate(calculated);
    }

    void calculateError() {
        error = activationFunction.derivative(signal) * axon.sumWeightedErrors();
        myDendrites().forEach(d -> d.setError(error));
    }

    void learn() {
        myDendrites().forEach(d -> d.learnWith(learningCoefficient));
    }

    Dendrite newDendrite() {
        Dendrite newDendrite = new Dendrite();
        this.dendrites.add(newDendrite);
        return newDendrite;
    }

    double getSignal() {
        return signal;
    }

    double getError() {
        return error;
    }

    private double calculateInputSum() {
        return myDendrites().stream()
                .mapToDouble(Dendrite::value)
                .reduce(0.0, (a, b) -> a + b);
    }

    private List<Dendrite> myDendrites() {
        ArrayList<Dendrite> myDendrites = new ArrayList<>(visibleDendrites());
        myDendrites.add(bias);
        return myDendrites;
    }

    @Override
    public String toString() {
        return "Neuron{" +
                "dendrites=" + dendrites +
                ", bias=" + bias +
                '}';
    }

    Dendrite bias() {
        return bias;
    }
}
