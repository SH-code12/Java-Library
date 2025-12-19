package DomainLayer.interfaces.NeuralNetwork;

import DomainLayer.entities.NeuralNetwork.RegressionMetrics;
import InfrastructureLayer.NeuralNetwork.layers.DebugLogger;

import java.util.List;

public interface NeuralNetworkAPI {
    void fit(double[][] X, double[][] y, int epochs, int batchSize, double learningRate);
    double[] getLastLosses();
    double[][] predict(double[][] X);           // returns regression outputs
    void saveModel(String path) throws Exception;
    void loadModel(String path) throws Exception;
    RegressionMetrics evaluate(double[][] X, double[][] y);
    double predictSingle(double[] x) ;
    void setLogger(DebugLogger logger);

    void enableDebug(boolean flag);

    }
