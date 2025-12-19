package InfrastructureLayer.NeuralNetwork.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class CsvDataLoader {

    public static List<String[]> loadAndClean(
            String path,
            boolean skipHeader,
            int sampleSize,
            long seed
    ) throws Exception {

        List<String[]> rawData = new ArrayList<>();
        int expectedColumns = -1;

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            boolean headerSkipped = false;

            while ((line = br.readLine()) != null) {
                if (skipHeader && !headerSkipped) {
                    headerSkipped = true;
                    continue;
                }

                line = line.trim();
                if (line.isEmpty()) continue;

                String[] row = line.split(",");

                // Set expected number of columns from first row
                if (expectedColumns < 0) expectedColumns = row.length;

                // Skip row if length mismatch
                if (row.length != expectedColumns) continue;

                // Skip row if any value is empty (missing)
                boolean hasMissing = false;
                for (String val : row) {
                    if (val == null || val.trim().isEmpty()) {
                        hasMissing = true;
                        break;
                    }
                }
                if (hasMissing) continue;

                rawData.add(row);
            }
        }

        // Optional: Sampling
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
