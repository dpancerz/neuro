package com.dpancerz.nai;

import static com.dpancerz.nai.Numbers.ONE_CHAR;
import static com.dpancerz.nai.Numbers.ZERO_CHAR;
import static java.lang.Math.round;

class NumbersToTrainingConverter {
    double[] toInput(String trainingObject) { //TODO cache?
        int length = trainingObject.length();
        double[] inputVector = new double[length];
        char[] asChars = trainingObject.toCharArray();

        for (int i = 0; i < length; i++) {
            char pixel = asChars[i];
            if (ONE_CHAR == pixel) { //might add more characters in the future with assigned 'opacity'
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
        int len = 3;
        double[] expectedOutput = new double[len];
        for (int i = len - 1; i >= 0; i--) {// (100)_2 = (4)_10
            expectedOutput[i] = expectedResult % 2;
            expectedResult /= 2;
        }
        return expectedOutput;
    }

    int fromOutputToCategory(double[] expectedResult) {
        int len = expectedResult.length;
        int resultAsInt = 0;
        for (int i = 0; i < len; i++) {
            resultAsInt *= 2;
            resultAsInt += round(expectedResult[i]);
        }
        return resultAsInt;
    }
}
