package DomainLayer.interfaces.NeuralNetwork;

public interface Activation {

    double calc();

    double [] deravative(double [] activatedOutput);
}
