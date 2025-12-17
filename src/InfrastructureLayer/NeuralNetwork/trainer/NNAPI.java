package InfrastructureLayer.NeuralNetwork.trainer;

import DomainLayer.entities.NeuralNetwork.HyperParameters;
import DomainLayer.entities.NeuralNetwork.NeuralNetworkModel;
import DomainLayer.entities.NeuralNetwork.RegressionMetrics;
import DomainLayer.interfaces.NeuralNetwork.Layer;
import DomainLayer.interfaces.NeuralNetwork.NeuralNetworkAPI;
import InfrastructureLayer.NeuralNetwork.layers.DebugLogger;
import InfrastructureLayer.NeuralNetwork.layers.DenseLayer;
import InfrastructureLayer.NeuralNetwork.layers.LayerFactory;

import InfrastructureLayer.NeuralNetwork.util.ModelIO;


import java.util.List;

public class NNAPI implements NeuralNetworkAPI {
    private NeuralNetworkModel model;
    private HyperParameters hyperParams;
    private boolean debug ;
    private Trainer trainer;
    private DebugLogger logger;


    public NNAPI(HyperParameters hyperParams) {
        this.hyperParams = hyperParams;
        model = new NeuralNetworkModel();
        List<Layer> layers = LayerFactory.createLayers(hyperParams.layerConfigs);
        layers.forEach(model::addLayer);
        trainer = new Trainer(model, hyperParams.lossFunction, hyperParams);
    }

    @Override
    public void fit(double[][] X, double[][] y, int epochs, int batchSize, double learningRate) {
        hyperParams.epochs = epochs;
        hyperParams.batchSize = batchSize;
        hyperParams.learningRate = learningRate;
        trainer.train(X, y);
    }
    /// Predict batch
    @Override
    public double[][] predict(double[][] X) {
        double[][] output = X;
        for (Layer layer : model.getLayers()) {
            output = layer.forward(output);
        }
        return output;
    }
    @Override
    public double predictSingle(double[] x) {
        double[][] input = new double[][] { x };
        double[][] output = predict(input);
        return output[0][0];
    }


    @Override
    public void saveModel(String path) throws Exception {
        ModelIO.save(path, model.getLayers());
    }

    @Override
    public void loadModel(String path) throws Exception {
        ModelIO.load(path, model.getLayers());
    }

    @Override
    public RegressionMetrics evaluate(double[][] X, double[][] y) {

        double[][] pred = predict(X);
        int n = y.length;

        double mae = 0;
        double mse = 0;

        double yMean = 0;
        for (int i = 0; i < n; i++) {
            yMean += y[i][0];
        }
        yMean /= n;

        double ssTot = 0;
        double ssRes = 0;

        for (int i = 0; i < n; i++) {
            double actual = y[i][0];
            double predicted = pred[i][0];

            double error = predicted - actual;

            mae += Math.abs(error);
            mse += error * error;

            ssRes += error * error;
            ssTot += (actual - yMean) * (actual - yMean);
        }

        RegressionMetrics m = new RegressionMetrics();
        m.mae = mae / n;
        m.rmse = Math.sqrt(mse / n);
        m.r2 = 1.0 - (ssRes / ssTot);

        return m;
    }
    @Override
    public void setLogger(DebugLogger logger) {
        this.logger = logger;
        model.getLayers().forEach(layer -> {
            if (layer instanceof DenseLayer dl) {
                dl.setLogger(logger);
            }
        });
    }


    @Override
    public void enableDebug(boolean flag) {
        this.debug = flag;
        model.getLayers().forEach(layer -> {
            try {
                var m = layer.getClass().getMethod("setDebug", boolean.class);
                m.invoke(layer, flag);
            } catch (Exception ignored) {}
        });
    }
}
