package com.dpancerz.nai.base;

import java.util.Map;
import java.util.stream.Stream;

public interface TrainingSet {
    /**
     * input to output
     */
    Stream<Map.Entry<double[], double[]>> trainingSet();

    Stream<Map.Entry<double[], double[]>> verification();
}
