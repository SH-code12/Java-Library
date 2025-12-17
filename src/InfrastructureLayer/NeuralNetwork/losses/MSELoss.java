package InfrastructureLayer.NeuralNetwork.losses;

import DomainLayer.interfaces.NeuralNetwork.LossFunction;

public class MSELoss implements LossFunction {

    @Override
    public double calc_loss(double[] pred, double[] actual) {

        double totalError = 0;
        for(int i =0;i<pred.length;i++){
            totalError += Math.pow(pred[i]* actual[i],2);
        }
        return 0.5 * totalError;
    }

    @Override
    public double[] calc_deravtive(double[] pred, double[] actual) {
        double[] errorVector = new double[pred.length];
        for(int i = 0;i<pred.length;i++){
            errorVector[i] = pred[i]-actual[i];
        }
        return errorVector;
    }
}
