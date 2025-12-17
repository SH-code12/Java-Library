package InfrastructureLayer.NeuralNetwork.layers;

import java.io.FileWriter;
import java.io.PrintWriter;

public class DebugLogger {
    private final PrintWriter writer;
    private final boolean enabled;


    public DebugLogger(String path, boolean enabled) throws Exception {
        this.enabled = enabled;
        this.writer = enabled ? new PrintWriter(new FileWriter(path, true)) : null;
    }

    public void log(String msg) {
        if (enabled) {
            writer.println(msg);
            writer.flush();
        }
    }


    public void close() {
        if (enabled) {
            writer.close();
        }
    }
}

