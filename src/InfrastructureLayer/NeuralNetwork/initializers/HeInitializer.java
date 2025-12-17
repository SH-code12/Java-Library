package InfrastructureLayer.NeuralNetwork.initializers;

import DomainLayer.interfaces.NeuralNetwork.WeightInitializer;

import java.util.Random;

public class HeInitializer implements WeightInitializer {

    @Override
    public double[][] initialize(int in, int out) {
        double std = Math.sqrt(2.0 / in);
        Random r = new Random();
        double[][] w = new double[in][out];
        for (int i = 0; i < in; i++)
            for (int j = 0; j < out; j++)
                w[i][j] = r.nextGaussian() * std;
        return w;
    }
}
