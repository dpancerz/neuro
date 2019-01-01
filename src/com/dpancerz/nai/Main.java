package com.dpancerz.nai;

import com.dpancerz.nai.movement.MovementDataFacade;
import com.dpancerz.nai.movement.MovementNeuralNetworkConfig;

public class Main {

    // usage: java -asdfasdf ?jar???  [learningCoefficient] [numberOfEpochs] [hiddenLayerSize]
    // e.g. java -asdfas ???  0.01 2000 250
    public static void main(String[] args) {
        MovementNeuralNetworkConfig config = readArguments(args);

        new MovementDataFacade().teachNeuralNetwork(config);
    }

    private static MovementNeuralNetworkConfig readArguments(String[] args) {
        return MovementNeuralNetworkConfig.builder()
                .learningCoefficient(Double.parseDouble(args[0]))
                .numberOfEpochs(Integer.parseInt(args[1]))
                .hiddenLayerSize(Integer.parseInt(args[2]))
                .build();
    }
}
