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
import InfrastructureLayer.NeuralNetwork.losses.CrossEntropyLoss;
import InfrastructureLayer.NeuralNetwork.losses.MSELoss;
import InfrastructureLayer.NeuralNetwork.optimizers.Adam;
import InfrastructureLayer.NeuralNetwork.optimizers.SGD;
import InfrastructureLayer.NeuralNetwork.util.DataUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class FlightPricePrediction {
    private Scanner sc = new Scanner(System.in);

    public void run() throws Exception {
        // Load CSV
        System.out.println("Loading Data...\n");
        List<String[]> rawData = new ArrayList<>();

        String dataPath = "D:/SHaHD/4th_first term/Soft Computing/Assignments/Java_Library/java-library/src/resources/Clean_Dataset.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(dataPath))) {
            String line;
            boolean headerSkipped = false;
            while ((line = br.readLine()) != null) {
                if (!headerSkipped) { headerSkipped = true; continue; }
                if (line.trim().isEmpty()) continue;
                rawData.add(line.split(","));
            }
        }

        System.out.println("Data Loaded. Total rows: " + rawData.size());
        // Sample data for faster runtime
        int sampleSize = Math.min(50000, rawData.size());
        Random rnd = new Random(42);
        List<String[]> sampledData = new ArrayList<>(sampleSize);
        for (int i = 0; i < sampleSize; i++) {
            sampledData.add(rawData.get(rnd.nextInt(rawData.size())));
        }
        rawData = sampledData;
        System.out.println("Sampled rows: " + rawData.size());

        // Build vocabularies for categorical features
        Set<String> airlines = new HashSet<>();
        Set<String> sourceCities = new HashSet<>();
        Set<String> destCities = new HashSet<>();
        Set<String> classes = new HashSet<>();
        Set<String> stops = new HashSet<>();

        for (String[] row : rawData) {
            airlines.add(row[1]);
            sourceCities.add(row[3]);
            destCities.add(row[7]);
            classes.add(row[8]);
            stops.add(row[5]);
        }

        List<String> airlineVocab = new ArrayList<>(airlines);
        List<String> sourceVocab = new ArrayList<>(sourceCities);
        List<String> destVocab = new ArrayList<>(destCities);
        List<String> classVocab = new ArrayList<>(classes);
        List<String> stopsVocab = new ArrayList<>(stops);

        // Prepare features and targets
        List<double[]> featuresList = new ArrayList<>();
        List<Double> targetsList = new ArrayList<>();

        for (String[] row : rawData) {
            List<Double> featureRow = new ArrayList<>();

            // One-hot encode categorical features
            double[] airlineOH = DataUtils.oneHot(row[1], airlineVocab);
            double[] sourceOH = DataUtils.oneHot(row[3], sourceVocab);
            double[] destOH = DataUtils.oneHot(row[7], destVocab);
            double[] classOH = DataUtils.oneHot(row[8], classVocab);
            double[] stopsOH = DataUtils.oneHot(row[5], stopsVocab);

            for (double v : airlineOH) featureRow.add(v);
            for (double v : sourceOH) featureRow.add(v);
            for (double v : destOH) featureRow.add(v);
            for (double v : classOH) featureRow.add(v);
            for (double v : stopsOH) featureRow.add(v);

            // Numeric features: duration, days_left
            featureRow.add(Double.parseDouble(row[9]));
            featureRow.add(Double.parseDouble(row[10]));

            featuresList.add(featureRow.stream().mapToDouble(d -> d).toArray());
            targetsList.add(Double.parseDouble(row[11])); // price
        }

        double[][] X = featuresList.toArray(new double[0][]);
        double[][] yRaw = new double[targetsList.size()][1];
        for (int i = 0; i < targetsList.size(); i++) yRaw[i][0] = targetsList.get(i);

        System.out.println("Validate Data..\n");
        // Validate features
        DataUtils.validate(X);


        // Normalize features
        System.out.println("Normalizing features...");
        DataUtils.Norm norm = DataUtils.zscore(X);
        X = norm.X;
        // Normalize targets
        System.out.println("Normalizing target...");
        DataUtils.TargetNorm targetNorm = DataUtils.zscore1D(yRaw);
        double[][] yNorm = new double[yRaw.length][1];
        for (int i = 0; i < yRaw.length; i++) yNorm[i][0] = targetNorm.y[i];


        // Split
        System.out.println("Splitting data...");
        DataUtils.Split splitX = DataUtils.trainTestSplit(X, X, 0.2, 42, true);
        DataUtils.Split splitY = DataUtils.trainTestSplit(yNorm, yNorm, 0.2, 42, true);
        DataUtils.Split splitYRaw = DataUtils.trainTestSplit(yRaw, yRaw, 0.2, 42, true);

//        // Normalize
//        System.out.println("Start Normalization...\n");
//        DataUtils.TargetNorm tn = DataUtils.zscore1D(y);
//        double[][] yNorm = new double[y.length][1];
//        for (int i = 0; i < y.length; i++) yNorm[i][0] = tn.y[i];
//
//// Split normalized targets
//        DataUtils.Split split = DataUtils.trainTestSplit(X, yNorm, 0.2, 42, true);
//
//        DataUtils.Norm norm = DataUtils.zscore(X);
//        X = norm.X;
//        System.out.println("End Normalization\n");
//
//        // Split
//        System.out.println("Start Split...\n");
//        DataUtils.Split split = DataUtils.trainTestSplit(X, y, 0.2, 42, true);
//        System.out.println("End Split \n");

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

            int inputSize = (i == 0) ? X[0].length : layerConfigs.get(i-1).outputSize;

            layerConfigs.add(new LayerConfig(inputSize, neurons, activation, initializer,optimizer));

        }

        HyperParameters hp = new HyperParameters(layerConfigs, optimizer, lossFunction, epochs, batchSize, lr);

        // Service
        System.out.println("Start Debugging ...\n");

        NeuralService service = new NeuralService(hp);
        service.enableDebug(false);


        // Train
        System.out.println("Training...");
        service.setTargetNorm(targetNorm); // You can add a setter in NeuralService

        service.train(splitX.Xtrain, splitY.ytrain, epochs, batchSize, lr);

        // Predict
        double[][] predNorm = service.predict(splitX.Xtest);

        // Denormalize predictions
        double[][] pred = new double[predNorm.length][1];
        for (int i = 0; i < predNorm.length; i++) {
            pred[i][0] = predNorm[i][0] * targetNorm.std + targetNorm.mean;
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
        System.out.printf("R² (how model is good, variance from target) : %.4f%n", m.r2);

        // Save/load

        service.saveModel("D:/SHaHD/4th_first term/Soft Computing/Assignments/Java_Library/java-library/src/resources/model.nn");
        service.loadModel("D:/SHaHD/4th_first term/Soft Computing/Assignments/Java_Library/java-library/src/resources/model.nn");
        System.out.println("Model saved and loaded successfully.");


    }

}
