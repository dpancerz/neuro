package com.dpancerz.nai.base.config;

public abstract class BaseZeroOneDataConverter<T> implements TrainingDataConverter<T> {
    private final ZeroAndOneSymbols symbols;
    private final int outputLength;

    protected BaseZeroOneDataConverter(ZeroAndOneSymbols symbols, int outputLength) {
        this.symbols = symbols;
        this.outputLength = outputLength;
    }

    @Override
    public double[] toInput(String trainingObject) {
        int length = trainingObject.length();
        double[] inputVector = new double[length];
        char[] asChars = trainingObject.toCharArray();

        for (int i = 0; i < length; i++) {
            char pixel = asChars[i];
            if (symbols.oneSymbol() == pixel) {
                inputVector[i] = 1.0;
            } else if (symbols.zeroSymbol() == pixel) {
                inputVector[i] = 0.0;
            } else {
                throw new IllegalArgumentException("'" + trainingObject + "'" + " has illegal characters");
            }
        }
        return inputVector;
    }

    @Override
    public double[] toOutput(int expectedResult) {
        double[] expectedOutput = new double[outputLength];
        expectedOutput[expectedResult - 1] = 1.0;
        return expectedOutput;
    }


    @Override
    public int findMax(double[] result) {
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
