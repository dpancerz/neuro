package com.dpancerz.nai.base;

class NeuralNetworkOutput implements ValueHolder {
    private double value;
    private double expected;

    @Override
    public double value() {
        return value;
    }

    @Override
    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public double weightedError() {
        return expected - value;
    }

    void setExpectedValue(double expected) {
        this.expected = expected;
    }

    @Override
    public String toString() {
        return "NeuralNetworkOutput{" +
                "value=" + value +
                ", expected=" + expected +
                '}';
    }
}
