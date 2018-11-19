package com.dpancerz.nai.base;

import java.util.HashSet;
import java.util.Set;

class Neuron {
    private final Axon axon;
    private final Set<Dendrite> dendrites;
    private final ActivationFunction activationFunction;

    Neuron(ActivationFunction activationFunction) {
        this.axon = new Axon();
        this.dendrites = new HashSet<>();
        this.activationFunction = activationFunction;
        addBias();
    }

    int inputSize() {
        return dendrites.size() - 1;
    }

    void addListener(Neuron listener) {
        addListener(
                listener.newDendrite());
    }

    void addListener(ValueHolder listener) {
        this.axon.addOutput(listener);
    }

    void propagate() {
        double calculated = activationFunction.applyAsDouble(
                calculateInputSum()
        );
        axon.propagate(calculated);
    }

    private Dendrite newDendrite() {
        Dendrite newDendrite = new Dendrite();
        this.dendrites.add(newDendrite);
        return newDendrite;
    }

    private double calculateInputSum() {
        return dendrites.stream()
                .reduce(0.0,
                        (value, d) -> value + d.value(),
                        (a, b) -> a + b);
    }

    private void addBias() {
        newDendrite().setValue(1.0);
    }
}
