package DomainLayer.interfaces.NeuralNetwork;

public interface Activation {

    double[] calc(double [] z);

    double [] deravative(double [] activatedOutput);
}
