package com.dpancerz.nai.experiment;

import com.dpancerz.nai.movement.MovementDataFacade;

import java.util.Arrays;

import static com.dpancerz.nai.movement.MovementNeuralNetworkConfig.builder;

public class NaiProjectExperiment {
    private static final int[] HIDDEN_LAYER_SIZES = {5, 10, 25, 50, 100, 250};
    private static final double[] TEACHING_COEFFICIENTS = {0.003, 0.01, 0.03, 0.1, 0.2};

    private static final int TOTAL_LEARNING_ITERATIONS = 400;

    private static final int STEP = 10;

    private final MovementDataFacade facade;

    public NaiProjectExperiment(MovementDataFacade facade) {
        this.facade = facade;
    }

    public void runExperiment() {
        Arrays.stream(HIDDEN_LAYER_SIZES)
                .boxed()
                .flatMap(layerSize -> Arrays
                        .stream(TEACHING_COEFFICIENTS)
                        .boxed()
                        .map(learningCoefficient -> new Pair<>(layerSize, learningCoefficient))
                )
                .parallel()
                .forEach(this::run);
    }

    private void run(Pair<Integer, Double> pair) {
        run(pair.key, pair.value);
    }

    private void run(int hiddenLayerSize, double learningCoefficient) {
        facade.buildNeuralNetworkTeacher(builder()
                .learningCoefficient(learningCoefficient)
                .hiddenLayerSize(hiddenLayerSize)
                .numberOfEpochs(STEP)
                .build()
        ).run().reRunTimes(
                        (TOTAL_LEARNING_ITERATIONS / STEP) -
                                1);
    }

    private final static class Pair<K, V> {
        private final K key;
        private final V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
