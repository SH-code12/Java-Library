package DomainLayer.interfaces.NeuralNetwork;

public interface Layer {


    double[][] forward(double[][] input);
    double[][] backward(double[][] gradOutput, double learningRate);
    double[][] getWeights();
    double[] getBias();
    void setWeights(double[][] W);
    void setBias(double[] b);
}
