package InfrastructureLayer.NeuralNetwork.initializers;

import DomainLayer.interfaces.NeuralNetwork.WeightInitializer;

public class XavierInitializer implements WeightInitializer {

    @Override
    public double[][] initialize(int in, int out) {
        double limit = Math.sqrt(6.0 / (in + out));
        return new RandomUniformInitializer(-limit, limit)
                .initialize(in, out);
    }
}
