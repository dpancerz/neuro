package com.dpancerz.nai.base.math;

import java.util.function.DoubleUnaryOperator;

public class LinearFunction implements ActivationFunction {
    private final double steepness;
    public LinearFunction(double steepness) {
        this.steepness = steepness;
    }

    @Override
    public DoubleUnaryOperator derivative() {
        return e -> steepness;
    }

    @Override
    public double applyAsDouble(double operand) {
        return operand * steepness;
    }
}
