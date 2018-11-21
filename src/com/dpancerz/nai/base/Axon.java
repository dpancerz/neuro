package com.dpancerz.nai.base;

import java.util.HashSet;
import java.util.Set;

class Axon {
    private final Set<ValueHolder> outputs;

    Axon() {
        this.outputs = new HashSet<>();
    }

    void propagate(double signal) {
        outputs.forEach(
                dendrite -> dendrite.setValue(signal)
        );
    }

    void addOutput(ValueHolder listener) {
        outputs.add(listener);
    }

    double sumWeightedErrors() {
        return outputs.stream()
                .mapToDouble(ValueHolder::weightedError)
                .sum();
    }

    @Override
    public String toString() {
        return "Axon{" +
                "outputs=" + outputs +
                '}';
    }
}
