package InfrastructureLayer.NeuralNetwork.layers;

import DomainLayer.interfaces.NeuralNetwork.Activation;
import DomainLayer.interfaces.NeuralNetwork.Layer;
import DomainLayer.interfaces.NeuralNetwork.Optimizer;
import DomainLayer.interfaces.NeuralNetwork.WeightInitializer;
import InfrastructureLayer.NeuralNetwork.util.Matrix;

import java.util.Arrays;

public class DenseLayer implements Layer {
    private Activation activation;

    private WeightInitializer weightInitializer;
    private Optimizer optimizer;

    private double[][] W;
    private double[] b;

    private double[][] lastInput;
    private double[][] lastZ;

    public DenseLayer(int in, int out,
                      Activation activation,
                      WeightInitializer initializer,
                      Optimizer optimizer) {

        this.activation = activation;
        this.weightInitializer = initializer;
        this.optimizer = optimizer;
        this.W = weightInitializer.initialize(in, out);
        this.b = new double[out];
    }
    private boolean debug;

    @Override
    public double[][] forward(double[][] input) {
        this.lastInput = input;

        double[][] z = Matrix.addRowVector(Matrix.dot(input, W), b);

        if (debug) {
            System.out.println("Z: " + Arrays.deepToString(lastZ));
        }
        this.lastZ = z;
        return activation.activate(z);
    }
    public void setDebug(boolean flag) {
        this.debug = flag;
    }
    @Override
    public double[][] backward(double[][] gradOutput, double learningRate) {

        // dZ = dA ⊙ activation'(Z)
        double[][] dZ = Matrix.elementWiseMultiply(gradOutput, activation.derivative(lastZ));

        // dW = Xᵀ · dZ
        double[][] dW = Matrix.dot(Matrix.transpose(lastInput), dZ);

        // db = sum(dZ)
        double[] db = Matrix.colSum(dZ);

        // dX = dZ · Wᵀ
        optimizer.applyUpdate(W, dW, b, db, learningRate);
        return Matrix.dot(dZ, Matrix.transpose(W));
    }
    @Override
    public double[][] getWeights() {
        return W;
    }

    @Override
    public double[] getBias() {
        return b;
    }

    @Override
    public void setWeights(double[][] W) {
        this.W = W;
    }

    @Override
    public void setBias(double[] b) {
        this.b = b;
    }
}
