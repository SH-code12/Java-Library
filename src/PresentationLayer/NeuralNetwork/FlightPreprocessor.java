package PresentationLayer.NeuralNetwork;


import InfrastructureLayer.NeuralNetwork.util.DataUtils;

import java.util.*;

public class FlightPreprocessor {

    public static class Result {
        public double[][] X;
        public double[][] yRaw;
        public double[][] yNorm;
        public DataUtils.Norm featureNorm;
        public DataUtils.TargetNorm targetNorm;
    }

    public static Result preprocess(List<String[]> data) {

        // --- Build vocabularies ---
        Set<String> airlines = new HashSet<>();
        Set<String> sources = new HashSet<>();
        Set<String> dests = new HashSet<>();
        Set<String> classes = new HashSet<>();
        Set<String> stops = new HashSet<>();

        for (String[] row : data) {
            airlines.add(row[1]);
            sources.add(row[3]);
            dests.add(row[7]);
            classes.add(row[8]);
            stops.add(row[5]);
        }

        List<String> airlineV = new ArrayList<>(airlines);
        List<String> sourceV = new ArrayList<>(sources);
        List<String> destV = new ArrayList<>(dests);
        List<String> classV = new ArrayList<>(classes);
        List<String> stopV = new ArrayList<>(stops);

        // --- Build feature matrix ---
        List<double[]> features = new ArrayList<>();
        List<Double> targets = new ArrayList<>();

        for (String[] row : data) {
            List<Double> f = new ArrayList<>();

            for (double v : DataUtils.oneHot(row[1], airlineV)) f.add(v);
            for (double v : DataUtils.oneHot(row[3], sourceV)) f.add(v);
            for (double v : DataUtils.oneHot(row[7], destV)) f.add(v);
            for (double v : DataUtils.oneHot(row[8], classV)) f.add(v);
            for (double v : DataUtils.oneHot(row[5], stopV)) f.add(v);

            f.add(Double.parseDouble(row[9]));   // duration
            f.add(Double.parseDouble(row[10]));  // days_left

            features.add(f.stream().mapToDouble(d -> d).toArray());
            targets.add(Double.parseDouble(row[11]));
        }

        double[][] X = features.toArray(new double[0][]);
        double[][] yRaw = new double[targets.size()][1];
        for (int i = 0; i < targets.size(); i++) yRaw[i][0] = targets.get(i);

        // --- Normalize ---
        System.out.println("Validate Data..\n");
        DataUtils.validate(X);
        System.out.println("Normalizing features...");

        DataUtils.Norm xNorm = DataUtils.zscore(X);
        System.out.println("Normalizing target...");
        DataUtils.TargetNorm yNorm = DataUtils.zscore1D(yRaw);

        double[][] yNormArr = new double[yNorm.y.length][1];
        for (int i = 0; i < yNorm.y.length; i++) {
            yNormArr[i][0] = yNorm.y[i];
        }

        Result r = new Result();
        r.X = xNorm.X;
        r.yRaw = yRaw;
        r.yNorm = yNormArr;
        r.featureNorm = xNorm;
        r.targetNorm = yNorm;
        return r;
    }
}
