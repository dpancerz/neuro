package com.dpancerz.nai;

import com.dpancerz.nai.experiment.NaiProjectExperiment;
import com.dpancerz.nai.movement.MovementDataFacade;
import com.dpancerz.nai.movement.MovementNeuralNetworkConfig;

public class Main {

    public static void main(String[] args) {
        new NaiProjectExperiment(
                new MovementDataFacade()
        ).runExperiment();
    }

    // arguments order: [learningCoefficient] [numberOfEpochs] [hiddenLayerSize]
    public static void oldmain(String[] args) {
        MovementNeuralNetworkConfig config = readArguments(args);

        new MovementDataFacade()
                .buildNeuralNetworkTeacher(config)
                .run();
    }

    private static MovementNeuralNetworkConfig readArguments(String[] args) {
        return MovementNeuralNetworkConfig.builder()
                .learningCoefficient(Double.parseDouble(args[0]))
                .numberOfEpochs(Integer.parseInt(args[1]))
                .hiddenLayerSize(Integer.parseInt(args[2]))
                .build();
    }
}
