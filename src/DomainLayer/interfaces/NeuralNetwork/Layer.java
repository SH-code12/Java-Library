package DomainLayer.interfaces.NeuralNetwork;

import InfrastructureLayer.NeuralNetwork.layers.DebugLogger;

public interface Layer {


    double[][] forward(double[][] input);
    double[][] backward(double[][] gradOutput, double learningRate);
    double[][] getWeights();
    double[] getBias();
    void setWeights(double[][] W);
    void setBias(double[] b);
    void setLogger(DebugLogger logger) ;
    void setDebug(boolean debug) ;


    }
