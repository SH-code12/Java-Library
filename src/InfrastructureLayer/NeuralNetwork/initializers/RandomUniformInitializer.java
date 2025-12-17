package InfrastructureLayer.NeuralNetwork.initializers;

import DomainLayer.interfaces.NeuralNetwork.WeightInitializer;

import java.util.Random;

public class RandomUniformInitializer implements WeightInitializer {

    private final double min;
    private final double max;

    // Default range [-0.5, 0.5]
    public RandomUniformInitializer() {
        this.min = -0.5;
        this.max = 0.5;
    }

    public RandomUniformInitializer(double min, double max) {
        this.min = min;
        this.max = max;
    }
    @Override
    public double[][] initialize(int in, int out) {
        Random r = new Random();
        double[][] w = new double[in][out];
        for (int i = 0; i < in; i++)
            for (int j = 0; j < out; j++)
                w[i][j] = min + r.nextDouble() * (max - min);
        return w;
    }
}
