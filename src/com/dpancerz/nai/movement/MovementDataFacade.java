package com.dpancerz.nai.movement;

import com.dpancerz.nai.base.NeuralNetworkTeacher;
import com.dpancerz.nai.base.config.NeuralNetworkConfig;
import com.dpancerz.nai.base.math.SigmoidFunction;

import java.util.*;

import static com.dpancerz.nai.movement.Path.*;
import static java.lang.String.format;
import static java.util.stream.Collectors.toSet;

public class MovementDataFacade {
    private final FileStreamer streamer;

    static final int SECOND_LAYER_SIZE = 6;
    public static final String SPACE = " ";

    public MovementDataFacade() {
        this.streamer = new FileStreamer();
    }

    public NeuralNetworkTeacher buildNeuralNetworkTeacher(MovementNeuralNetworkConfig config) {
        int inputVectorSize = getInputVectorSize();
        Set<MovementType> categoryLabels = getCategoryLabels();

        MovementDataConverter converter = new MovementDataConverter(categoryLabels, streamer);

        Map<double[], double[]> trainingSet = converter.extractTrainingSet();
        Map<double[], double[]> testSet = converter.extractTestSet();

        return getNeuralNetworkTeacher(config, inputVectorSize, converter, trainingSet, testSet);
    }

    private NeuralNetworkTeacher getNeuralNetworkTeacher(MovementNeuralNetworkConfig config,
                                                         int inputVectorSize,
                                                         MovementDataConverter converter,
                                                         Map<double[], double[]> trainingSet,
                                                         Map<double[], double[]> testSet) {
        return new NeuralNetworkTeacher(
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
                new MovementNeuralNetworkLogger(
                        format(LOG_FILE_PATH_TEMPLATE,
                                config.getLearningCoefficient(),
                                config.getNumberOfEpochs(),
                                config.getHiddenLayerSize())
                ),
                new MovementNeuralNetworkLogger(
                        format(RESULT_FILE_PATH,
                                config.getLearningCoefficient(),
                                config.getNumberOfEpochs(),
                                config.getHiddenLayerSize())
                )
        );
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
