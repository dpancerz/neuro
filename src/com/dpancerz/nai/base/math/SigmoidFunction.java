package com.dpancerz.nai.base.math;

import java.util.function.DoubleUnaryOperator;

import static java.lang.Math.E;
import static java.lang.Math.pow;

public class SigmoidFunction implements ActivationFunction {
    private final double lambda;

    public SigmoidFunction(double steepness) {
        this.lambda = steepness;
    }

    @Override
    public double applyAsDouble(double x) {
        return 1.0 /
                (1 + pow(E, -1.0 * lambda * x));
    }

    @Override
    public DoubleUnaryOperator derivative() {
        return x -> {
            double eToMinusLambdaX = pow(E, -1.0 * lambda * x);
            return (lambda * eToMinusLambdaX) /
                    pow(1 + eToMinusLambdaX, 2.0);
        };
    }
}
