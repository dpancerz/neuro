package com.dpancerz.nai.numbers;

import static com.dpancerz.nai.numbers.Numbers.ONE_CHAR;
import static com.dpancerz.nai.numbers.Numbers.ZERO_CHAR;

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
        int len = 5;
        double[] expectedOutput = new double[len];
        expectedOutput[expectedResult - 1] = 1.0;// 00001 -> 5, 00100 -> 3
        return expectedOutput;

//        int len = 3;
//        double[] expectedOutput = new double[len];
//        for (int i = len - 1; i >= 0; i--) {// (100)_2 = (4)_10
//            expectedOutput[i] = expectedResult % 2;
//            expectedResult /= 2;
//        }
//        return expectedOutput;
    }

    int fromOutputToCategory(double[] result) {
        return findMax(result) + 1;
//        int len = expectedResult.length;
//        int resultAsInt = 0;
//        for (int i = 0; i < len; i++) {
//            resultAsInt *= 2;
//            resultAsInt += round(expectedResult[i]); // 100 -> 4; 010 -> 2
//        }
//        return resultAsInt;
    }

    int findMax(double[] result) {
        int indexOfMax = 0;
        double max = result[0];
        for (int i = 0; i < result.length -1; i++) {// 00001 -> 5, 00100 -> 3
            if (max < result[i+1]) {
                max = result[i+1];
                indexOfMax = i+1;
            }
        }
        return indexOfMax;
    }
}
