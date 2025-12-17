package InfrastructureLayer.NeuralNetwork.optimizers;

import DomainLayer.interfaces.NeuralNetwork.Optimizer;

public class Adam implements Optimizer {

    private final double beta1 = 0.9, beta2 = 0.999, eps = 1e-8;
    private double t = 0;
    private double[][] mW = null, vW = null;
    private double[] mb = null, vb = null;

    @Override
    public void applyUpdate(double[][] W, double[][] dW, double[] b, double[] db, double learningRate) {
        // Re-initialize if uninitialized or shapes mismatch
        if (mW == null || mW.length != W.length || mW[0].length != W[0].length) {
            mW = new double[W.length][W[0].length];
            vW = new double[W.length][W[0].length];
        }
        if (mb == null || mb.length != b.length) {
            mb = new double[b.length];
            vb = new double[b.length];
        }

        t++;

        // Update weights
        for (int i = 0; i < W.length; i++) {
            for (int j = 0; j < W[0].length; j++) {
                double grad = dW[i][j];
                mW[i][j] = beta1 * mW[i][j] + (1 - beta1) * grad;
                vW[i][j] = beta2 * vW[i][j] + (1 - beta2) * grad * grad;

                double mHat = mW[i][j] / (1 - Math.pow(beta1, t));
                double vHat = vW[i][j] / (1 - Math.pow(beta2, t));

                W[i][j] -= learningRate * mHat / (Math.sqrt(vHat) + eps);
            }
        }

        // Update biases
        for (int j = 0; j < b.length; j++) {
            double grad = db[j];
            mb[j] = beta1 * mb[j] + (1 - beta1) * grad;
            vb[j] = beta2 * vb[j] + (1 - beta2) * grad * grad;

            double mHat = mb[j] / (1 - Math.pow(beta1, t));
            double vHat = vb[j] / (1 - Math.pow(beta2, t));

            b[j] -= learningRate * mHat / (Math.sqrt(vHat) + eps);
        }
    }
}
