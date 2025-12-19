package PresentationLayer.NeuralNetwork;

import ApplicationLayer.services.NeuralService;
import DomainLayer.entities.NeuralNetwork.HyperParameters;
import DomainLayer.entities.NeuralNetwork.LayerConfig;
import DomainLayer.entities.NeuralNetwork.RegressionMetrics;
import DomainLayer.interfaces.NeuralNetwork.Activation;
import DomainLayer.interfaces.NeuralNetwork.LossFunction;
import DomainLayer.interfaces.NeuralNetwork.Optimizer;
import DomainLayer.interfaces.NeuralNetwork.WeightInitializer;
import InfrastructureLayer.NeuralNetwork.activations.Linear;
import InfrastructureLayer.NeuralNetwork.activations.ReLU;
import InfrastructureLayer.NeuralNetwork.activations.Sigmoid;
import InfrastructureLayer.NeuralNetwork.activations.Tanh;
import InfrastructureLayer.NeuralNetwork.initializers.HeInitializer;
import InfrastructureLayer.NeuralNetwork.initializers.RandomUniformInitializer;
import InfrastructureLayer.NeuralNetwork.initializers.XavierInitializer;
import InfrastructureLayer.NeuralNetwork.layers.DebugLogger;
import InfrastructureLayer.NeuralNetwork.losses.CrossEntropyLoss;
import InfrastructureLayer.NeuralNetwork.losses.MSELoss;
import InfrastructureLayer.NeuralNetwork.optimizers.Adam;
import InfrastructureLayer.NeuralNetwork.optimizers.SGD;
import InfrastructureLayer.NeuralNetwork.trainer.Trainer;
import InfrastructureLayer.NeuralNetwork.util.CsvDataLoader;
import InfrastructureLayer.NeuralNetwork.util.DataUtils;
import InfrastructureLayer.NeuralNetwork.util.Plot;


import java.util.*;

public class FlightPricePrediction {
    private Scanner sc = new Scanner(System.in);
    private DebugLogger logger ;

    public void run() throws Exception {
        // Load CSV
        System.out.println("Loading Data...\n");

        String dataPath = "D:/SHaHD/4th_first term/Soft Computing/Assignments/Java_Library/java-library/src/resources/Clean_Dataset.csv";
        List<String[]> rawData = CsvDataLoader.loadAndClean(
                dataPath,
                true,
                50_000,
                42
        );

        FlightPreprocessor.Result prep = FlightPreprocessor.preprocess(rawData);

        System.out.println("Splitting data...");
        DataUtils.Split splitX = DataUtils.trainTestSplit(prep.X, prep.X, 0.2, 42, true);

        System.out.println("Splitting Features...");

        DataUtils.Split splitY = DataUtils.trainTestSplit(prep.yNorm, prep.yNorm, 0.2, 42, true);

        DataUtils.Split splitYRaw = DataUtils.trainTestSplit(prep.yRaw, prep.yRaw, 0.2, 42, true);

        // User inputs
        System.out.println("Enter epochs(100-500):");
        int epochs = sc.nextInt();
        System.out.println("Enter batch size(16 Recommended):");
        int batchSize = sc.nextInt();
        System.out.println("Enter learning rate[0.0005 For Adam](0.01-0.0001):");
        double lr = sc.nextDouble();

        // Choice Optimizer
        System.out.println("Choice Optimizer: \n" +
                "1.Adam Optimizer (Recommended)\n" +
                "2.SGD Optimizer\n");
        int choiceOp = sc.nextInt();
        Optimizer optimizer = switch (choiceOp) {
            case 2 -> new SGD();
            default -> new  Adam();
        };
        // Choice LossFunction
        System.out.println("Choice LossFunction: \n" +
                "1.MSELoss (Must choice (Regression)\n" +
                "2.CrossEntropyLoss \n");
        int lossInput = sc.nextInt();
        LossFunction lossFunction = switch(lossInput) {
            case 2 -> new CrossEntropyLoss();
            default -> new MSELoss();
        };


        System.out.println("Enter number of layers:");
        int L = sc.nextInt();
        List<LayerConfig> layerConfigs = new ArrayList<>();
        for (int i = 0; i < L; i++) {
            System.out.println("Note: :Last Layer should have 1 neuron and choice Linear Activation For it^_^ \n");
            System.out.println("Layer " + (i+1) + " neurons(60/40/20/1):");
            int neurons = sc.nextInt();
            // Choice Activation
            System.out.println("Choice Activation: \n" +
                    "1.Sigmoid \n" +
                    "2.Relu\n" +
                    "3.Tanh\n" +
                    "4.Linear(Must Choice For Last Layer) \n");
            int choiceA = sc.nextInt();
            Activation activation = switch(choiceA) {
                case 4 -> new Linear();
                case 2 -> new ReLU();
                case 3 -> new Tanh();
                default -> new Sigmoid();
            };
            // Choice Weight initializer
            System.out.println("Choice Weight initializer: \n" +
                    "1.RandomUniform(Not Recommended)\n" +
                    "2.Xavier (Recommended)\n" +
                    "3.HeInitializer\n");
            int choiceI = sc.nextInt();
            WeightInitializer initializer  = switch(choiceI) {
                case 1 -> new RandomUniformInitializer();
                case 3 -> new HeInitializer();
                default -> new XavierInitializer();
            };

            int inputSize = (i == 0) ? prep.X[0].length : layerConfigs.get(i-1).outputSize;

            layerConfigs.add(new LayerConfig(inputSize, neurons, activation, initializer,optimizer));

        }

        HyperParameters hp = new HyperParameters(layerConfigs, optimizer, lossFunction, epochs, batchSize, lr);

        // Service
        NeuralService service = new NeuralService(hp);
        /// Debugging
        System.out.println("Enter Status of Debug:\n" +
                "1.ON \n" +
                "2.OFF(Recommended) \n");
        int debugMood = sc.nextInt();
        switch (debugMood){
            case 1:
                System.out.println("Debug Mood: ON");
                logger = new DebugLogger(
                        "D:/SHaHD/4th_first term/Soft Computing/Assignments/Java_Library/java-library/src/resources/debug_logs.txt",
                        true
                );
                service.setLogger(logger);
                service.enableDebug(true);

                break;
            case 2:
                System.out.println("Debug Mood: OFF");
                service.enableDebug(false);
                break;

            default:
                System.out.println("Invalid choice, debug OFF by default");
                service.enableDebug(false);
        }

        // Training
        System.out.println("Training...");
        service.setTargetNorm(prep.targetNorm);
        service.train(splitX.Xtrain, splitY.ytrain, epochs, batchSize, lr);
        /// For Graph
        double[] losses = service.getLastLosses();
        // Save the curve as PNG
        Plot.saveLossCurve(losses, "D:/SHaHD/4th_first term/Soft Computing/Assignments/Java_Library/java-library/src/resources/training_loss.png");
        System.out.println("Graph Saved At resources/training_loss.png \n");
        /// Debugging and Logining

        if(debugMood == 1){
            logger.close();
            System.out.println("Logs Saved Successfully ");
        }else{
            System.out.println("\nLogs Not Saved ,Debugging is OFF \n");

        }

        //// Prediction
        System.out.println("Prediction...");

        double[][] predNorm = service.predict(splitX.Xtest);

        // Denormalize predictions
        double[][] pred = new double[predNorm.length][1];
        for (int i = 0; i < predNorm.length; i++) {
            pred[i][0] = predNorm[i][0] * prep.targetNorm.std + prep.targetNorm.mean;
        }
        System.out.println("Predictions on test set (denormalized):");
        for (int i = 0; i < Math.min(5, pred.length); i++) {
            double predictedPrice = pred[i][0];
            double actualPrice = splitYRaw.ytest[i][0];
            double absError = Math.abs(predictedPrice - actualPrice);

            System.out.println("Predicted price: $" + String.format("%,.0f", predictedPrice));
            System.out.println("Actual price   : $" + String.format("%,.0f", actualPrice));
            System.out.println("Absolute error : $" + String.format("%,.0f", absError));
            System.out.println("----------------------------------");
        }


        // Evaluate
        RegressionMetrics m = service.evaluate(splitX.Xtest, splitY.ytest);
        System.out.println("Model Evaluation:");
        System.out.printf("MAE (average absolute difference) : $%.2f%n", m.mae);
        System.out.printf("RMSE (Root Mean Squared Error): $%.2f%n", m.rmse);
        System.out.printf("R² (How model is good, variance from target) : %.4f%n", m.r2);

        //// Single input
        System.out.println("\nTesting predictSingle()...\n");
    // Take one test sample (already normalized)
        double[] xTest = splitX.Xtest[0];
    // Predict (normalized output)
        double predNormSingle = service.predictSingle(xTest);
    // Denormalize
        double predSingle = predNormSingle * prep.targetNorm.std + prep.targetNorm.mean;
    // Actual price (raw, not normalized)
        double actual = splitYRaw.ytest[0][0];

        System.out.println("Single prediction test:");
        System.out.println("Predicted price: $" + String.format("%,.0f", predSingle));
        System.out.println("Actual price   : $" + String.format("%,.0f", actual));
        System.out.println("Absolute error : $" + String.format("%,.0f",
                Math.abs(predSingle - actual)));

        // Save/load
        service.saveModel("D:/SHaHD/4th_first term/Soft Computing/Assignments/Java_Library/java-library/src/resources/model.nn");
        service.loadModel("D:/SHaHD/4th_first term/Soft Computing/Assignments/Java_Library/java-library/src/resources/model.nn");
        System.out.println("Model saved and loaded successfully.");


    }

}
