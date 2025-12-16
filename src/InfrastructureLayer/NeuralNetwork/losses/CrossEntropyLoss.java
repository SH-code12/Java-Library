package InfrastructureLayer.NeuralNetwork.losses;

import DomainLayer.interfaces.NeuralNetwork.LossFunction;

public class CrossEntropyLoss implements LossFunction {

    @Override
    public double calc_loss(double[] pred, double[] actual) {
        double eps = 1e-15;
        double totalError = 0;
        for(int i =0;i<pred.length;i++){
            totalError += - (actual[i] * Math.log(pred[i]+eps) ) + (1-actual[i] )*Math.log((1-pred[i])+eps);

        }
        return totalError;
    }

    @Override
    public double[] calc_deravtive(double[] pred, double[] actual) {
        double [] errorVector = new double[pred.length];
        for(int i = 0;i<pred.length;i++){
            errorVector[i] = (pred[i]-actual[i])/(pred[i]*(1-pred[i]));
        }

        return errorVector;
    }
}
