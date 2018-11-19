package com.dpancerz.nai.base;

class OutputHolder implements ValueHolder {
    private double value;

    @Override
    public double value() {
        return value;
    }

    @Override
    public void setValue(double value) {
        this.value = value;
    }
}
