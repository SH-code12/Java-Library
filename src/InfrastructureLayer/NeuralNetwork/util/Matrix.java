package InfrastructureLayer.NeuralNetwork.util;

public class Matrix {

    public static  double [] multiply(double [] matrixA, double[] matrixB){
        if(matrixA.length!= matrixB.length) throw new RuntimeException("cannot multiply 2 vectors of different sizes");
        double[] res = new double[matrixA.length];
                for (int i = 0; i < matrixA.length; i++) {
                    res[i] = matrixA[i] * matrixB[i];
                }
                return res;

    }
}
