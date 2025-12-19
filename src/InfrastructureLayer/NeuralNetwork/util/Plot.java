package InfrastructureLayer.NeuralNetwork.util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Plot {

    /**
     * Draws the loss curve and saves it as a PNG file
     *
     * @param losses array of loss values
     * @param filename path to save PNG
     * @throws Exception if saving fails
     */
    public static void saveLossCurve(double[] losses, String filename) throws Exception {
        if (losses == null || losses.length == 0)
            throw new IllegalArgumentException("Loss array is empty");

        int width = 800;
        int height = 600;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();

        // Enable anti-aliasing for smooth lines
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Background
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, width, height);

        // Draw axes
        g2.setColor(Color.BLACK);
        g2.drawLine(50, height - 50, width - 50, height - 50); // X axis
        g2.drawLine(50, 50, 50, height - 50); // Y axis

        // Find min and max loss for scaling
        double minLoss = Double.MAX_VALUE;
        double maxLoss = Double.MIN_VALUE;
        for (double l : losses) {
            if (l < minLoss) minLoss = l;
            if (l > maxLoss) maxLoss = l;
        }
        double range = maxLoss - minLoss;
        if (range == 0) range = 1; // avoid division by zero

        // Draw loss curve
        g2.setColor(Color.RED);
        int n = losses.length;
        for (int i = 1; i < n; i++) {
            int x1 = 50 + (int) ((i - 1) * (width - 100.0) / (n - 1));
            int y1 = height - 50 - (int) ((losses[i - 1] - minLoss) * (height - 100) / range);
            int x2 = 50 + (int) (i * (width - 100.0) / (n - 1));
            int y2 = height - 50 - (int) ((losses[i] - minLoss) * (height - 100) / range);
            g2.drawLine(x1, y1, x2, y2);
        }

        // Draw labels
        g2.setColor(Color.BLACK);
        g2.drawString("Epochs", width / 2, height - 20);
        g2.drawString("Loss", 10, height / 2);

        g2.dispose();

        // Save to PNG
        ImageIO.write(image, "PNG", new File(filename));
        System.out.println("Loss curve saved as " + filename);
    }
}
