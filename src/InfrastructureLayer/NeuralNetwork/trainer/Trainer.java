package InfrastructureLayer.NeuralNetwork.trainer;

import DomainLayer.entities.NeuralNetwork.HyperParameters;
import DomainLayer.entities.NeuralNetwork.NeuralNetworkModel;
import DomainLayer.interfaces.NeuralNetwork.Layer;
import DomainLayer.interfaces.NeuralNetwork.LossFunction;

import java.util.List;
import java.util.Arrays;

public class Trainer {
    private final NeuralNetworkModel model;
    private final LossFunction lossFunction;
    private final HyperParameters hyperParams;
    private double[] lastLosses; // store last training losses

    public Trainer(NeuralNetworkModel model, LossFunction lossFunction, HyperParameters hyperParams) {
        this.model = model;
        this.lossFunction = lossFunction;
        this.hyperParams = hyperParams;
    }

    public double[] train(double[][] X, double[][] y) {
        int epochs = hyperParams.epochs;
        int batchSize = hyperParams.batchSize;
        double lr = hyperParams.learningRate;

        int m = X.length;
        int steps = (int) Math.ceil((double) m / batchSize);
        lastLosses = new double[epochs];
        List<Layer> layers = model.getLayers();

        for (int e = 0; e < epochs; e++) {
            double epochLoss = 0;

            for (int s = 0; s < steps; s++) {
                int start = s * batchSize;
                int end = Math.min(start + batchSize, m);

                double[][] Xbatch = Arrays.copyOfRange(X, start, end);
                double[][] ybatch = Arrays.copyOfRange(y, start, end);

                // Forward
                double[][] output = Xbatch;
                for (Layer layer : layers)
                    output = layer.forward(output);

                // Loss
                double batchLoss = 0;
                for (int i = 0; i < output.length; i++)
                    batchLoss += lossFunction.calc_loss(output[i], ybatch[i]);
                epochLoss += batchLoss / output.length;

                // Backward
                double[][] grad = new double[output.length][output[0].length];
                for (int i = 0; i < output.length; i++)
                    grad[i] = lossFunction.calc_deravtive(output[i], ybatch[i]);

                for (int i = layers.size() - 1; i >= 0; i--)
                    grad = layers.get(i).backward(grad, lr);
            }

            lastLosses[e] = epochLoss / steps;
            System.out.println("Epoch " + (e + 1) + " Loss: " + lastLosses[e]);
        }

        return lastLosses;
    }

    public double[] getLastLosses() {
        return lastLosses;
    }
}
