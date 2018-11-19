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

    }

    void backPropagate() {

    }

    List<Neuron> neurons() {
        return neurons;
    }
}
