package com.dpancerz.nai.base;

class Dendrite implements ValueHolder {
    private double value;
    private double weight;

    Dendrite() {
        this.weight = Math.random();
    }

    @Override
    public double value() {
        return weight * value;
    }

    @Override
    public void setValue(double value) {
        this.value = value;
    }

    public void setWeight(double weight) { //TODO likely to be replaced with "learn"
        this.weight = weight;
    }
}
