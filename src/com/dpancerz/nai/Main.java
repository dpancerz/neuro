package com.dpancerz.nai;

public class Main {
    public static void main(String[] args) {
        NumbersToTrainingConverter converter = new NumbersToTrainingConverter();
        new NumbersNeuralNetworkTeacher(
                new NumbersTrainingSet(converter),
                converter
        ).run();
    }
}
