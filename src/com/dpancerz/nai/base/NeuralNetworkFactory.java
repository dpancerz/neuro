package com.dpancerz.nai.base;

import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;

public class NeuralNetworkFactory {
    NeuralNetwork createNetwork( //TODO prettify
            ActivationFunction activationFunction,
            int... layerSizes) {
        LinkedList<NeuralLayer> layers = Arrays.stream(layerSizes)
                .mapToObj(i -> toLayer(i, activationFunction))
                .collect(toCollection(LinkedList::new));
        connect(layers);
        return new NeuralNetwork(layers);
    }

    private NeuralLayer toLayer(int layerSize, ActivationFunction activationFunction) {
        List<Neuron> neurons = Stream.generate(() -> new Neuron(activationFunction))
                .limit(layerSize)
                .collect(toList());
        return new NeuralLayer(neurons);
    }

    private void connect(LinkedList<NeuralLayer> layers) {
        Iterator<NeuralLayer> layerIterator = layers.iterator();
        NeuralLayer current = layerIterator.next();
        NeuralLayer next;
        while (layerIterator.hasNext()) {
            next = layerIterator.next();
            for (Neuron currentLayerNeuron: current.neurons()) {
                for (Neuron nextLayerNeuron: next.neurons()) {
                    currentLayerNeuron.addListener(nextLayerNeuron);
                }
            }
        }
    }
}