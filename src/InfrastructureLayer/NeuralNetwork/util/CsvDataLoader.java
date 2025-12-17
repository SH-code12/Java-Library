package InfrastructureLayer.NeuralNetwork.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class CsvDataLoader {

    public static List<String[]> load(
            String path,
            boolean skipHeader,
            int sampleSize,
            long seed
    ) throws Exception {

        List<String[]> rawData = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            boolean headerSkipped = false;

            while ((line = br.readLine()) != null) {
                if (skipHeader && !headerSkipped) {
                    headerSkipped = true;
                    continue;
                }
                if (line.trim().isEmpty()) continue;
                rawData.add(line.split(","));
            }
        }

        // Sampling (optional)
        if (sampleSize > 0 && sampleSize < rawData.size()) {
            Random rnd = new Random(seed);
            List<String[]> sampled = new ArrayList<>(sampleSize);
            for (int i = 0; i < sampleSize; i++) {
                sampled.add(rawData.get(rnd.nextInt(rawData.size())));
            }
            rawData = sampled;
        }

        return rawData;
    }
}
