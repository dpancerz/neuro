package com.dpancerz.nai.base;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;

public class NeuralNetworkFactory {
    public NeuralNetwork createNetwork(ActivationFunction activationFunction,
                                       double learningCoefficient,
                                       int inputSize,
                                       int... layerSizes) {
        LinkedList<NeuralLayer> layers = createLayers(activationFunction,
                learningCoefficient, layerSizes);
        createInputDendrites(inputSize, layers.getFirst());
        connect(layers);
        return new NeuralNetwork(layers, learningCoefficient);
    }

    private LinkedList<NeuralLayer> createLayers(ActivationFunction activationFunction,
                                                 double learningCoefficient,
                                                 int[] layerSizes) {
        return Arrays.stream(layerSizes)
                .mapToObj(i -> toLayer(i, learningCoefficient, activationFunction))
                .collect(toCollection(LinkedList::new));
    }

    private NeuralLayer toLayer(int layerSize,
                                double learningCoefficient,
                                ActivationFunction activationFunction) {
        List<Neuron> neurons = Stream
                .generate(() -> new Neuron(activationFunction, learningCoefficient))
                .limit(layerSize)
                .collect(toList());
        return new NeuralLayer(neurons);
    }

    private void createInputDendrites(int inputSize, NeuralLayer firstLayer) {
        firstLayer.neurons()
                .forEach(neuron ->
                        IntStream.range(0, inputSize)
                                .forEach(i -> neuron.newDendrite())
                );
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
