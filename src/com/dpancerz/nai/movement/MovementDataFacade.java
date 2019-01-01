package com.dpancerz.nai.movement;

import com.dpancerz.nai.base.NeuralNetworkTeacher;
import com.dpancerz.nai.base.config.NeuralNetworkConfig;
import com.dpancerz.nai.base.math.SigmoidFunction;

import java.util.*;
import java.util.stream.Stream;

import static com.dpancerz.nai.movement.Path.*;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

public class MovementDataFacade {
    FileStreamer streamer;

    static final int SECOND_LAYER_SIZE = 6;
    public static final String SPACE = " ";

    public void teachNeuralNetwork(MovementNeuralNetworkConfig config) {
        int inputVectorSize = getInputVectorSize();
        Set<MovementType> categoryLabels = getCategoryLabels();
        int outputVectorSize = categoryLabels.size();

        MovementDataConverter converter = new MovementDataConverter(outputVectorSize);

        Map<double[], double[]> trainingSet = converter.extractTrainingSet();
        Map<double[], double[]> testSet = converter.extractTestSet();

        NeuralNetworkTeacher teacher = new NeuralNetworkTeacher(
                new MovementTrainingSet(
                        trainingSet,
                        testSet
                ),
                new NeuralNetworkConfig(inputVectorSize,
                        Arrays.asList(config.getHiddenLayerSize(),
                                SECOND_LAYER_SIZE),
                        config.getLearningCoefficient(),
                        new SigmoidFunction(1),
                        config.getNumberOfEpochs(),
                        true
                ),
                converter,
                System.out::println
        );
        teacher.run();
    }

    private int getInputVectorSize() {
        return (int) streamer.streamFromFile(FEATURES)
                .count();
    }

    private Set<MovementType> getCategoryLabels() {
        return streamer.streamFromFile(CATEGORIES_LABELS)
                .map(String::trim)
                .map(line -> line.split(SPACE))
                .map(arr -> new MovementType(
                        Integer.parseInt(arr[0]),
                        arr[1]
                ))
                .collect(toSet());
    }
}
