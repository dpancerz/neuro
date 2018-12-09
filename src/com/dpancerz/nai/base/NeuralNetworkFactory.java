package com.dpancerz.nai.base;

import com.dpancerz.nai.base.config.NeuralNetworkConfig;
import com.dpancerz.nai.base.math.ActivationFunction;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;

class NeuralNetworkFactory {
    NeuralNetwork createNetwork(NeuralNetworkConfig config) {
        LinkedList<NeuralLayer> layers = createLayers(config.activationFunction(),
                config.learningCoefficient(),
                config.layerSizes());
        createInputDendrites(config.numberOfInputs(), layers.getFirst());
        connect(layers);
        return new NeuralNetwork(layers, config.learningCoefficient());
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
