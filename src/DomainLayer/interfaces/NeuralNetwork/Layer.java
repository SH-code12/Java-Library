package DomainLayer.interfaces.NeuralNetwork;

public interface Layer {

    void forward();
    double[] backward( double[] errorMatrixOfnext, double lr);
}
