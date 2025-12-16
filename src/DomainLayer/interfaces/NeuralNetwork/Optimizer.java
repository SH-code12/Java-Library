package DomainLayer.interfaces.NeuralNetwork;

public interface Optimizer {

//    double update(double currentW,double grad,double lr);
void applyUpdate(double[][] W, double[][] dW, double[] b, double[] db, double learningRate);

}
