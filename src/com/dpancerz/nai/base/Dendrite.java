package com.dpancerz.nai.base;

class Dendrite implements ValueHolder {
    private double signal;
    private double weight;
    private double error;

    Dendrite() {
        this(Math.random());
    }

    Dendrite(double weight) {
        this.weight = weight;
    }

    @Override
    public double value() {
        return weight * signal;
    }

    @Override
    public void setValue(double value) {
        this.signal = value;
    }

    @Override
    public double weightedError() {
        return weight * error;
    }

    double getWeight() {
        return weight;
    }

    void learnWith(double coefficient) {
        this.weight += (coefficient * error * signal);
    }

    void setError(double error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "Dendrite{" +
                "signal=" + signal +
                ", weight=" + weight +
                ", error=" + error +
                '}';
    }
}
