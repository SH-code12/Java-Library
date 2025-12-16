package DomainLayer.interfaces.NeuralNetwork;

public interface Optimizer {

    double update(double currentW,double grad,double lr);
}
