package com.dpancerz.nai.base;

import java.util.function.DoubleUnaryOperator;

public interface ActivationFunction extends DoubleUnaryOperator {
    DoubleUnaryOperator derivative();

    default double derivative(double input) {
        return derivative().applyAsDouble(input);
    }
}
