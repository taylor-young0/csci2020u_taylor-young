package sample;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

public class Controller {
    // Bar chart sample data
    private static double[] avgHousingPricesByYear = {
        247381.0,264171.4,287715.3,294736.1, 308431.4,322635.9,340253.0,363153.7
    };
    private static double[] avgCommercialPricesByYear = {
        1121585.3,1219479.5,1246354.2,1295364.8,1335932.6,1472362.0,1583521.9,1613246.3
    };

    // Pie chart sample data
    private static String[] ageGroups = {
        "18-25", "26-35", "36-45", "46-55", "56-65", "65+"
    };
    private static int[] purchasesByAgeGroup = {
        648, 1021, 2453, 3173, 1868, 2247
    };
    private static Color[] pieColours = {
        Color.AQUA, Color.GOLD, Color.DARKORANGE, Color.DARKSALMON, Color.LAWNGREEN, Color.PLUM
    };

    @FXML Canvas mainCanvas;
    @FXML GraphicsContext gc;

    @FXML
    public void initialize() {
        gc = mainCanvas.getGraphicsContext2D();
        drawBarChart(Controller.avgHousingPricesByYear, Controller.avgCommercialPricesByYear, 800, 300);
        drawPieChart(Controller.purchasesByAgeGroup, Controller.pieColours, 800);
    }

    public void drawBarChart(double[] housingData, double[] commercialData, int w, int h) {
        // Offset between sets of bar chart data
        double dataPointOffset = 5.0;
        double xInc = (w / (housingData.length + commercialData.length + dataPointOffset)) / 2;

        // Find the min and max
        double maxVal = housingData[0];
        double minVal = housingData[0];
        for (int i = 1; i < housingData.length; i++) {
            if (housingData[i] < minVal) {
                minVal = housingData[i];
            }
            if (commercialData[i] < minVal) {
                minVal = commercialData[i];
            }
            if (housingData[i] > maxVal) {
                maxVal = housingData[i];
            }
            if (commercialData[i] > maxVal) {
                maxVal = commercialData[i];
            }
        }

        // Draw the bar chart
        double offset = 0.0;
        for (int i = 0; i < housingData.length; i++) {
            double height = (housingData[i] / maxVal) * h;
            double x = (xInc * 2)*i;
            gc.setFill(Color.RED);
            gc.fillRect(x + offset, (h - height), xInc, height);

            height = (commercialData[i] / maxVal) * h;
            gc.setFill(Color.BLUE);
            gc.fillRect(x + xInc + offset, (h - height), xInc, height);

            // Offset between sets of bar data
            offset += dataPointOffset;
        }
    }

    public void drawPieChart(int[] purchases, Color[] colors, int w) {
        // stores angular arc extent
        double[] angle = new double[purchases.length];

        // Get the total
        int total = 0;
        for (int i = 0; i < purchases.length; i++) {
            total += purchases[i];
        }

        // Get the fractions for each purchase
        for (int i = 0; i < purchases.length; i++) {
            angle[i] = ((double) purchases[i] / (double) total) * 360;
        }

        // Draw the pie chart
        double x = w/2;
        double y = 0.0;
        double startAngle = 0.0;
        for (int i = 0; i < purchases.length; i++) {
            gc.setFill(colors[i]);
            gc.fillArc(x,y,250,250,startAngle,angle[i],ArcType.ROUND);
            startAngle += angle[i];
        }
    }

}
