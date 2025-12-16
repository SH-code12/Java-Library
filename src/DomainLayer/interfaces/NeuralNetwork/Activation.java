package DomainLayer.interfaces.NeuralNetwork;

public interface Activation {

    double[][] activate(double[][] x);

    double[][] derivative(double[][] x);

}
