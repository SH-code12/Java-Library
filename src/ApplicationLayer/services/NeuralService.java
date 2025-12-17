package ApplicationLayer.services;

import DomainLayer.entities.NeuralNetwork.HyperParameters;
import DomainLayer.entities.NeuralNetwork.RegressionMetrics;
import InfrastructureLayer.NeuralNetwork.trainer.NNAPI;
import InfrastructureLayer.NeuralNetwork.util.DataUtils;

public class NeuralService {
    private NNAPI nnAPI;
    private DataUtils.TargetNorm targetNorm;


    public NeuralService(HyperParameters hyperParams) {
        this.nnAPI = new NNAPI(hyperParams);
    }

    public void enableDebug(boolean flag) {
        nnAPI.enableDebug(flag);
    }

    public void train(double[][] X, double[][] y, int epochs, int batchSize, double learningRate) {
        // Normalize target
//        targetNorm = DataUtils.zscore1D(y);
//
//        // Convert normalized y to 2D array
//        double[][] yNorm = new double[y.length][1];
//        for (int i = 0; i < y.length; i++) {
//            yNorm[i][0] = targetNorm.y[i];
//        }

        // Pass normalized target to trainer
        nnAPI.fit(X, y, epochs, batchSize, learningRate);
    }

    public double[][] predict(double[][] X) {

        return nnAPI.predict(X);
    }

    public RegressionMetrics evaluate(double[][] X, double[][] y) {
        return nnAPI.evaluate(X, y);
    }

    public void saveModel(String path) throws Exception {
        nnAPI.saveModel(path);
    }

    public void loadModel(String path) throws Exception {
        nnAPI.loadModel(path);
    }

    public DataUtils.TargetNorm getTargetMeanStd() {
        return targetNorm;
    }
    public void setTargetNorm(DataUtils.TargetNorm targetNorm){
        this.targetNorm = targetNorm;

    }


}
