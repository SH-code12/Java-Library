package InfrastructureLayer.NeuralNetwork.util;

import DomainLayer.interfaces.NeuralNetwork.Layer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.List;

public class ModelIO {

    // Save weights: each layer start line: LAYER rows cols
    // then rows lines with cols numbers, then bias line with cols numbers
    public static void save(String path, List<Layer> layers) throws Exception {
        try(PrintWriter pw = new PrintWriter(new File(path))) {
            for(int li=0; li<layers.size(); li++){
                Layer L = layers.get(li);
                double[][] W = L.getWeights();
                double[] b = L.getBias();
                pw.println("LAYER " + W.length + " " + W[0].length);
                for(int i=0;i<W.length;i++){
                    for(int j=0;j<W[0].length;j++){
                        if(j>0) pw.print(",");
                        pw.print(W[i][j]);
                    }
                    pw.println();
                }
                // bias
                for(int j=0;j<b.length;j++){
                    if(j>0) pw.print(",");
                    pw.print(b[j]);
                }
                pw.println();
            }
        }
    }

    public static void load(String path, List<Layer> layers) throws Exception {
        try(BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            int li = 0;
            while((line = br.readLine()) != null && li < layers.size()){
                if(line.startsWith("LAYER")) {
                    String[] tok = line.split(" ");
                    int rows = Integer.parseInt(tok[1]);
                    int cols = Integer.parseInt(tok[2]);
                    double[][] W = new double[rows][cols];
                    for(int i=0;i<rows;i++){
                        String l = br.readLine();
                        String[] parts = l.split(",");
                        for(int j=0;j<cols;j++) W[i][j] = Double.parseDouble(parts[j]);
                    }
                    String bLine = br.readLine();
                    String[] bParts = bLine.split(",");
                    double[] b = new double[bParts.length];
                    for(int j=0;j<bParts.length;j++) b[j] = Double.parseDouble(bParts[j]);
                    layers.get(li).setWeights(W);
                    layers.get(li).setBias(b);
                    li++;
                }
            }
        }
    }
}
