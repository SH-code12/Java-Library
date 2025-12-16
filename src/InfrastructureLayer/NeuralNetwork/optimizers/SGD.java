package InfrastructureLayer.NeuralNetwork.optimizers;

import DomainLayer.interfaces.NeuralNetwork.Optimizer;

public class SGD implements Optimizer {

    @Override
    public void applyUpdate(double[][] W, double[][] dW, double[] b, double[] db, double learningRate) {
        for (int i = 0; i < W.length; i++) {
            for (int j = 0; j < W[0].length; j++) {
                W[i][j] -= learningRate * dW[i][j];
            }
        }
        for (int j = 0; j < b.length; j++) {
            b[j] -= learningRate * db[j];
        }
    }
}

