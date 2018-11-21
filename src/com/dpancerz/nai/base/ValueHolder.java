package com.dpancerz.nai.base;

interface ValueHolder {//ValueHolder might be a Dendrite, Synapse, etc.
    double value();

    void setValue(double value);

    double weightedError();
}
