package com.dpancerz.nai.base.config;

public interface TrainingDataConverter<T> {
    double[] toInput(String trainingObject);
    double[] toOutput(int expectedResult);
    T fromOutputToCategory(double[] result);
    int findMax(double[] result);
}
