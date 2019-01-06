package com.dpancerz.nai.movement;

import com.dpancerz.nai.base.NeuralNetworkTeacher;
import com.dpancerz.nai.base.config.NeuralNetworkConfig;
import com.dpancerz.nai.base.math.SigmoidFunction;

import java.util.*;

import static com.dpancerz.nai.movement.Path.*;
import static java.util.stream.Collectors.toSet;

public class MovementDataFacade {
    private final FileStreamer streamer;

    static final int SECOND_LAYER_SIZE = 6;
    public static final String SPACE = " ";

    public MovementDataFacade() {
        this.streamer = new FileStreamer();
    }

    public void teachNeuralNetwork(MovementNeuralNetworkConfig config) {
        int inputVectorSize = getInputVectorSize();
        Set<MovementType> categoryLabels = getCategoryLabels();

        MovementDataConverter converter = new MovementDataConverter(categoryLabels, streamer);

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
                new MovementNeuralNetworkLogger(LOG_FILE_PATH)
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
