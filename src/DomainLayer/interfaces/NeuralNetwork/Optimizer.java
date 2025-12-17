package DomainLayer.interfaces.NeuralNetwork;

public interface Optimizer {

void applyUpdate(double[][] W, double[][] dW, double[] b, double[] db, double learningRate);

}
