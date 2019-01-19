package com.dpancerz.nai.base;

import java.util.LinkedList;

public class NeuralNetworkSnapshot {
    private double learningCoefficient;
    private String activationFunction;

    /**
     * assuming everything's connected- unlike 'sharded' CNN;
     * LinkedList symbolizes Layers
     * each matrix is a weight's matrix- neurons are laid horizontally with [0] being bias
     *      and the rest are weights for neurons from previous layer (or input)
     */
    private LinkedList<double[][]> weights;
    
}
