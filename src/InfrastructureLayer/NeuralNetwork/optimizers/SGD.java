package InfrastructureLayer.NeuralNetwork.optimizers;

import DomainLayer.interfaces.NeuralNetwork.Optimizer;

public class SGD implements Optimizer {


    @Override
    public double update(double currentW, double grad, double lr) {
        return currentW -(lr* grad) ;
    }
}
