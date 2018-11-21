package com.dpancerz.nai.numbers;

public class NumbersFacade {
    public static NumbersNeuralNetworkTeacher numbersNeuralNetworkTeacher() {
        NumbersToTrainingConverter converter = new NumbersToTrainingConverter();
        return new NumbersNeuralNetworkTeacher(
                new NumbersTrainingSet(converter),
                converter
        );
    }
}
