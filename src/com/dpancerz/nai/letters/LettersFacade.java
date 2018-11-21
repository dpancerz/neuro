package com.dpancerz.nai.letters;

public class LettersFacade {
    public static LettersNeuralNetworkTeacher lettersNeuralNetworkTeacher() {
        LettersToTrainingConverter converter = new LettersToTrainingConverter();
        return new LettersNeuralNetworkTeacher(
                new LettersTrainingSet(converter),
                converter
        );
    }
}
