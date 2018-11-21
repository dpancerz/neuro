package com.dpancerz.nai.base.math;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.function.DoubleUnaryOperator;

import static java.lang.String.format;

@RunWith(JUnit4.class)
public class TestSigmoid {

    @Test
    public void check() {
        SigmoidFunction sigmoidFunction = new SigmoidFunction(1);

        log("sigmoid");
        logFor(0.0, sigmoidFunction, 0.5);
        logFor(-1.0*Double.MAX_VALUE, sigmoidFunction, 0.0);
        logFor(Double.MAX_VALUE, sigmoidFunction, 1.0);

        log("derivative");
        DoubleUnaryOperator derivative = sigmoidFunction.derivative();
        logFor(0.0, derivative, 0.25);
        logFor(-400.0, derivative, 0.0);
        logFor(Double.MAX_VALUE, derivative, 0.0);
    }

    private void logFor(double value, DoubleUnaryOperator sigmoidFunction, double expected) {
        double actual = sigmoidFunction.applyAsDouble(value);
        Assert.assertEquals(expected, actual, 0.0000001);
        log(format("f(%s) = %s", value, actual));
    }

    private void log(String format) {
        System.out.println(format);
    }
}
