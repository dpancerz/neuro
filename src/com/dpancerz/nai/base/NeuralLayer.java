package com.dpancerz.nai.base;

import java.util.List;

class NeuralLayer {
    private final List<Neuron> neurons;

    public NeuralLayer(List<Neuron> neurons) {
        this.neurons = neurons;
    }

    public int size() {
        return neurons.size();
    }

    public int inputSize() {
        return neurons.iterator().next().inputSize();
    }

    void propagate() {
        neurons.forEach(Neuron::propagate);
    }

    void calculateErrors() {
        neurons.forEach(Neuron::calculateError);
    }

    void learn() {
        neurons.forEach(Neuron::learn);
    }

    List<Neuron> neurons() {
        return neurons;
    }

    @Override
    public String toString() {
        return "NeuralLayer{" +
                "neurons=" + neurons +
                '}';
    }
}
