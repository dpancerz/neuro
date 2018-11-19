package com.dpancerz.nai.base.activation;

import com.dpancerz.nai.base.ActivationFunction;

public class SigmoidFunction implements ActivationFunction {
    private final double param;

    public SigmoidFunction(double steepness) {
        this.param = -1 * steepness;
    }

    @Override
    public double applyAsDouble(double operand) {
        return 1.0 / (1 + Math.pow(Math.E, param * operand));
    }
}
