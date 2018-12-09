package com.dpancerz.nai.base;

import com.dpancerz.nai.base.math.ActivationFunction;
import com.dpancerz.nai.base.math.LinearFunction;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

@RunWith(JUnit4.class)
public class ErrorPropagationTest {
    @Test
    public void shouldPropagateErrorCorrectly() {
        double coefficient = 0.5;
        ActivationFunction function = new LinearFunction(1.0);

        List<Dendrite> aDendrites = new ArrayList<>();
        aDendrites.add(new Dendrite(1.0));
        aDendrites.add(new Dendrite(1.0));
        Dendrite aBias = new Dendrite(0.0);
        aBias.setValue(1);
        Neuron a = new Neuron(function, aDendrites, aBias, coefficient);

        List<Dendrite> bDendrites = new ArrayList<>();
        bDendrites.add(new Dendrite(1.0));
        bDendrites.add(new Dendrite(1.0));
        Dendrite bBias = new Dendrite(0.0);
        bBias.setValue(1);
        Neuron b = new Neuron(function, bDendrites, bBias, coefficient);

        Dendrite cBias = new Dendrite(0.0);
        cBias.setValue(1.0);

        ArrayList<Dendrite> cDendrites = new ArrayList<>();
        Neuron c = new Neuron(function, cDendrites, cBias, coefficient);
        Dendrite cDen1 = new Dendrite(1.0);
        cDendrites.add(cDen1);
        Dendrite cDen2 = new Dendrite(1.0);
        cDendrites.add(cDen2);
        a.addListener(cDen1);
        b.addListener(cDen2);

        aDendrites.get(0).setValue(0.0);
        bDendrites.get(0).setValue(0.0);
        aDendrites.get(1).setValue(1.0);
        bDendrites.get(1).setValue(1.0);

        NeuralNetworkOutput output = new NeuralNetworkOutput();
        c.addListener(output);


        a.propagate();
        double aSignal = a.getSignal();
        Assert.assertEquals(1.0, aSignal, 0.0001);
        b.propagate();
        double bSignal = b.getSignal();
        Assert.assertEquals(1.0, bSignal, 0.0001);

        c.propagate();
        double cSignal = c.getSignal();
        Assert.assertEquals(2.0, cSignal, 0.0001);

        double outputVal = output.value();
        Assert.assertEquals(2.0, outputVal, 0.0001);

        output.setExpectedValue(1.0);

        c.calculateError();
        double cError = c.getError();
        Assert.assertEquals(-1.0, cError, 0.0001);

        a.calculateError();
        double aError = a.getError();
        Assert.assertEquals(-1.0, aError, 0.0001);

        b.calculateError();
        double bError = b.getError();
        Assert.assertEquals(-1.0, bError, 0.0001);

        c.learn();
        Assert.assertEquals(0.5, c.visibleDendrites().get(0).getWeight(), 0.0001);
        Assert.assertEquals(0.5, c.visibleDendrites().get(1).getWeight(), 0.0001);
        Assert.assertEquals(-0.5, c.bias().getWeight(), 0.0001);

        a.learn();
        Assert.assertEquals(1.0, a.visibleDendrites().get(0).getWeight(), 0.0001);
        Assert.assertEquals(0.5, a.visibleDendrites().get(1).getWeight(), 0.0001);
        Assert.assertEquals(-0.5, a.bias().getWeight(), 0.0001);

        b.learn();
        Assert.assertEquals(1.0, b.visibleDendrites().get(0).getWeight(), 0.0001);
        Assert.assertEquals(0.5, b.visibleDendrites().get(1).getWeight(), 0.0001);
        Assert.assertEquals(-0.5, b.bias().getWeight(), 0.0001);
    }
}
