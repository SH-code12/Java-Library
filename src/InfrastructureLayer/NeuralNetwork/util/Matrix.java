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
    public static double[][] dot(double[][] A, double[][] B){
        int m=A.length, k=A[0].length, n=B[0].length;
        double[][] C=new double[m][n];
        for(int i=0;i<m;i++){
            for(int p=0;p<k;p++){
                double v=A[i][p];
                for(int j=0;j<n;j++) C[i][j]+=v*B[p][j];
            }
        }
        return C;
    }
    public static double[][] transpose(double[][] A){
        int m=A.length,n=A[0].length;
        double[][] T=new double[n][m];
        for(int i=0;i<m;i++) {
            for(int j=0;j<n;j++) {
                T[j][i]=A[i][j];
            }
        }
        return T;
    }
    public static double[][] addRowVector(double[][] A, double[] b){
        int m=A.length,n=A[0].length;
        double[][] out=new double[m][n];
        for(int i=0;i<m;i++) {
            for(int j=0;j<n;j++) {
                out[i][j]=A[i][j]+b[j];
            }
        }
        return out;
    }
    public static double[][] elementWiseMultiply(double[][] A, double[][] B){
        int m=A.length,n=A[0].length;
        double[][] out=new double[m][n];
        for(int i=0;i<m;i++) {
            for(int j=0;j<n;j++) {
                out[i][j]=A[i][j]*B[i][j];
            }
        }
        return out;
    }
    public static double[] colSum(double[][] A){
        int m=A.length,n=A[0].length;
        double[] s=new double[n];
        for(int i=0;i<m;i++) {
            for(int j=0;j<n;j++) {
                s[j]+=A[i][j];
            }
        }
        return s;
    }

}
