package DomainLayer.interfaces.NeuralNetwork;

public interface Layer {

    double[] forward(double[] input);
    double[] backward( double[] errorMatrixOfnext, double lr);
}
