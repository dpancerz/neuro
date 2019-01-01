package com.dpancerz.nai.movement;

import com.dpancerz.nai.base.config.TrainingSet;

import java.util.Map;
import java.util.stream.Stream;

class MovementTrainingSet implements TrainingSet {
    private final Map<double[], double[]> trainingSet;
    private final Map<double[], double[]> testSet;

    MovementTrainingSet(Map<double[], double[]> trainingSet,
                        Map<double[], double[]> testSet) {
        this.trainingSet = trainingSet;
        this.testSet = testSet;
    }

    @Override
    public Stream<Map.Entry<double[], double[]>> trainingSet() {
        return trainingSet.entrySet().stream();
    }

    @Override
    public Stream<Map.Entry<double[], double[]>> verification() {
        return testSet.entrySet().stream();
    }
}
