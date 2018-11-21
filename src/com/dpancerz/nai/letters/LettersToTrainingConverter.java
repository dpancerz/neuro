package com.dpancerz.nai.letters;

import static com.dpancerz.nai.letters.Letters.ONE_CHAR;
import static com.dpancerz.nai.letters.Letters.ZERO_CHAR;

class LettersToTrainingConverter {
    double[] toInput(String trainingObject) { //TODO cache?
        int length = trainingObject.length();
        double[] inputVector = new double[length];
        char[] asChars = trainingObject.toCharArray();

        for (int i = 0; i < length; i++) {
            char pixel = asChars[i];
            if (ONE_CHAR == pixel) {
                inputVector[i] = 1.0;
            } else if (ZERO_CHAR == pixel) {
                inputVector[i] = 0.0;
            } else {
                throw new IllegalArgumentException("'" + trainingObject + "'" + " has illegal characters");
            }
        }
        return inputVector;
    }

    double[] toOutput(int expectedResult) {
        int len = 6;
        double[] expectedOutput = new double[len];
        expectedOutput[expectedResult - 1] = 1.0;// 000010 -> 5, 001000 -> 3
        return expectedOutput;
    }

    char fromOutputToCategory(double[] result) {
        return (char) ('a' + findMax(result));
    }

    int findMax(double[] result) {
        int indexOfMax = 0;
        double max = result[0];
        for (int i = 0; i < result.length -1; i++) {// 000010 -> 5, 001000 -> 3
            if (max < result[i+1]) {
                max = result[i+1];
                indexOfMax = i+1;
            }
        }
        return indexOfMax;
    }
}
