package InfrastructureLayer.NeuralNetwork.util;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DataUtils {

    public static class Norm {
        public double[][] X;   // For features
        public double[] mean;
        public double[] std;
    }

    public static class TargetNorm {
        public double[] y;
        public double mean;
        public double std;
    }

    public static TargetNorm zscore1D(double[][] y) {
        int m = y.length;
        double sum = 0;

        for (int i = 0; i < m; i++) sum += y[i][0];
        double mean = sum / m;

        double var = 0;
        for (int i = 0; i < m; i++) {
            double d = y[i][0] - mean;
            var += d * d;
        }

        double std = Math.sqrt(var / m);
        if (std == 0) std = 1.0;

        double[] yn = new double[m];
        for (int i = 0; i < m; i++) {
            yn[i] = (y[i][0] - mean) / std;
        }

        TargetNorm tn = new TargetNorm();
        tn.y = yn;
        tn.mean = mean;
        tn.std = std;
        return tn;
    }


    // Z-score normalization for features
    public static Norm zscore(double[][] X){
        int m = X.length, n = X[0].length;
        double[] mean = new double[n], std = new double[n];
        for(int j = 0; j < n; j++){
            double s = 0;
            for(int i = 0; i < m; i++) s += X[i][j];
            mean[j] = s / m;
            double ss = 0;
            for(int i = 0; i < m; i++){
                double d = X[i][j] - mean[j];
                ss += d*d;
            }
            std[j] = Math.sqrt(ss/m);
            if(std[j] == 0) std[j] = 1.0;
        }

        double[][] out = new double[m][n];
        for(int i = 0; i < m; i++)
            for(int j = 0; j < n; j++)
                out[i][j] = (X[i][j] - mean[j]) / std[j];

        Norm r = new Norm();
        r.X = out;
        r.mean = mean;
        r.std = std;
        return r;
    }


    // Train-test split (unchanged)
    public static class Split {
        public final double[][] Xtrain, Xtest;
        public final double[][] ytrain, ytest;
        public Split(double[][] Xtrain, double[][] Xtest, double[][] ytrain, double[][] ytest){
            this.Xtrain=Xtrain; this.Xtest=Xtest; this.ytrain=ytrain; this.ytest=ytest;
        }
    }


    public static Split trainTestSplit(double[][] X, double[][] y, double testRatio, long seed, boolean shuffle) {
        int m = X.length; int testSize = (int)Math.round(m*testRatio); int trainSize = m - testSize;
        int[] idx = new int[m]; for(int i=0;i<m;i++) idx[i]=i;
        Random rnd = new Random(seed);
        if(shuffle) for(int i=m-1;i>0;i--){ int j=rnd.nextInt(i+1); int t=idx[i]; idx[i]=idx[j]; idx[j]=t; }
        double[][] Xtrain = new double[trainSize][X[0].length];
        double[][] Xtest = new double[testSize][X[0].length];
        double[][] ytrain = new double[trainSize][y[0].length];
        double[][] ytest = new double[testSize][y[0].length];
        for(int i=0;i<trainSize;i++){
            Xtrain[i]=X[idx[i]];
            ytrain[i]=y[idx[i]];
        }
        for(int i=trainSize;i<m;i++){
            Xtest[i-trainSize]=X[idx[i]];
            ytest[i-trainSize]=y[idx[i]];
        }
        return new Split(Xtrain,Xtest,ytrain,ytest);
    }
    public static double[] oneHot(String value, List<String> vocab) {
        double[] vec = new double[vocab.size()];
        int idx = vocab.indexOf(value);
        if (idx >= 0) vec[idx] = 1.0;
        return vec;
    }


    public static void validate(double[][] X){
        if(X==null) throw new IllegalArgumentException("Input X is null");
        if(X.length==0) throw new IllegalArgumentException("Empty dataset");
        int n = X[0].length;
        for(int i=0;i<X.length;i++){
            if(X[i].length != n) throw new IllegalArgumentException("Row length mismatch at "+i);
            for(int j=0;j<n;j++) {
                if(!Double.isFinite(X[i][j])) throw new IllegalArgumentException("Invalid numeric at "+i+","+j);
            }
        }
    }
}
