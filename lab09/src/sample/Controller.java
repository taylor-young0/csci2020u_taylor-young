package sample;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Controller {

    @FXML Canvas canvas;
    private GraphicsContext gc;
    private double max = 0.0;

    @FXML
    public void initialize() {
        gc = canvas.getGraphicsContext2D();
        ArrayList<Double> apple = downloadStockPrices("AAPL");
        ArrayList<Double> google = downloadStockPrices("GOOG");
        drawLinePlot(apple, google);
    }

    private ArrayList<Double> downloadStockPrices(String stockTicker) {
        ArrayList<Double> prices = new ArrayList<>();
        String baseURL = "https://query1.finance.yahoo.com/v7/finance/download/ticker?period1=1262322000&period2=1451538000&interval=1mo&events=history&includeAdjustedClose=true";
        try {
            InputStream file = new URL(baseURL.replace("ticker", stockTicker)).openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(file, StandardCharsets.UTF_8));
            String line;
            // helps to skip the first line
            boolean addLine = false;
            // read the file and add each line
            while ((line = reader.readLine()) != null) {
                if (addLine) {
                    double price = Double.parseDouble(line.split(",")[4]);
                    prices.add(price);

                    // used to help scale the graph
                    if (price > max) {
                        max = price;
                    }
                }
                addLine = !addLine;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // return all the prices
        return prices;
    }

    private void drawLinePlot(ArrayList<Double> firstTickerPrices, ArrayList<Double> secondTickerPrices) {
        // draw axis
        gc.setStroke(Color.BLACK);
        // draw y axis
        gc.strokeLine(50, 50, 50, canvas.getHeight() - 50);
        // draw x axis
        gc.strokeLine(50, canvas.getHeight() - 50, canvas.getWidth() - 50, canvas.getHeight() - 50);

        // draw first ticker prices
        gc.setStroke(Color.BLUE);
        plotLine(firstTickerPrices);
        // draw second ticker prices
        gc.setStroke(Color.RED);
        plotLine(secondTickerPrices);
    }

    private void plotLine(ArrayList<Double> tickerPrices) {
        double xOffset = (canvas.getWidth() - 100) / tickerPrices.size();
        double x1 = 0.0;
        for (int i = 0; i < tickerPrices.size() - 1; i++) {
            // calculate ratios and relative price
            double firstRatio = tickerPrices.get(i) / max;
            double firstRelativePrice = firstRatio * (canvas.getHeight() - 100);
            double secondRatio = tickerPrices.get(i + 1) / max;
            double secondRelativePrice = secondRatio * (canvas.getHeight() - 100);

            // first price point
            double y1 = (canvas.getHeight() - 100) - firstRelativePrice + 50;

            // second price point
            double x2 = x1 + xOffset + 50;
            double y2 = (canvas.getHeight() - 100) - secondRelativePrice + 50;

            // connect the points
            gc.strokeLine(x1 + 50, y1, x2, y2);

            x1+= xOffset;
        }
    }
}
