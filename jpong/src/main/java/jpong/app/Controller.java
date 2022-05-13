package jpong.app;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.scene.text.*;
import javafx.scene.*;


import java.io.IOException;
import java.util.Random;

public class Controller {
    enum Mode {
        ALL,
        RANDOM,
        CONNECTED
    }

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Label welcomeText;
    private static final double BUTTONS_PER_LINE = 50;
    private static final double NUM_BUTTON_LINES = 50;
    private static final double radius = 25;
    private static final Color lineColor = Color.FIREBRICK.deriveColor(0, 1, 1, .6);
    private static Mode mode = Mode.CONNECTED;

    private static double textToDouble(double var, String str) {
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return var;
        }
    }

    private static int textToInt(int var, String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return var;
        }
    }

    @FXML
    private ScrollPane scrollpane = new ScrollPane();

    @FXML
    private TextField rowsGenerate, columnsGenerate, minWeightGenerate, maxWeightGenerate, outFileGenerate;

    @FXML
    private void setAll(ActionEvent event) {
        mode = Mode.ALL;
    }

    @FXML
    private void setRandom(ActionEvent event) {
        mode = Mode.RANDOM;
    }

    @FXML
    private void setConnected(ActionEvent event) {
        mode = Mode.CONNECTED;
    }

    @FXML
    private void generate(ActionEvent event) throws IOException {
        int rows, columns;
        double minWeight, maxWeight;
        String outFile;
        try {
            rows = Integer.parseInt(rowsGenerate.getText());
            if (rows <= 0) {
                showInputError("Rows number must be positive");
                return;
            }
        } catch (NumberFormatException e) {
            showInputError("Incorrect rows number");
            return;
        }
        try {
            columns = Integer.parseInt(columnsGenerate.getText());
            if (columns <= 0) {
                showInputError("Columns number must be positive");
                return;
            }
        } catch (NumberFormatException e) {
            showInputError("Incorrect columns number");
            return;
        }
        try {
            minWeight = Double.parseDouble(minWeightGenerate.getText());
            if (minWeight < 0.0) {
                showInputError("Minimal weight must be positive");
                return;
            }
        } catch (NumberFormatException e) {
            showInputError("Incorrect minimal weight");
            return;
        }
        try {
            maxWeight = Double.parseDouble(maxWeightGenerate.getText());
            if (maxWeight <= 0.0) {
                showInputError("Maximal weight must be positive");
                return;
            }
        } catch (NumberFormatException e) {
            showInputError("Incorrect maximal weight");
            return;
        }
        if (minWeight >= maxWeight) {
            showInputError("Maximal weight must be greater than minimal");
            return;
        }
        outFile = outFileGenerate.getText();
        Graph newGraph = new Graph(rows * columns);
        Generator generator = null;
        switch (mode) {
            case ALL:
                generator = new AllEdges();
                break;
            case RANDOM:
                generator = new RandomEdges();
                break;
            case CONNECTED:
                generator = new ConnectedEdges();
                break;
        }
        newGraph = generator.generate((int) minWeight, (int) maxWeight, columns, rows);
        try {
            generator.saveGraph(outFile, newGraph);
        } catch (IOException e) {
            showInputError("File name not specified");
        }
    }

    public void initialize() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(0));
        grid.setHgap(0);
        grid.setVgap(0);
        int count = 0;
        StackPane[] stack = new StackPane[50 * 50]; /*!!!!!!!!!!!!!!!!!!!!!remember you need to do this manually !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/
        for (int r = 0; r < NUM_BUTTON_LINES; r++) {
            for (int c = 0; c < BUTTONS_PER_LINE; c++) {
                final Text text = new Text(String.valueOf(count));
                text.setFont(new Font(15));
                text.setBoundsType(TextBoundsType.VISUAL);
                Circle circle = new Circle(radius);
                circle.setStroke(Color.FORESTGREEN);
                circle.setStrokeWidth(5);
                circle.setStrokeType(StrokeType.INSIDE);
                circle.setFill(Color.AZURE);
                circle.relocate(0, 0);
                Line l1 = new Line(2 * radius + 2.5, radius, 3 * radius + 0.5, radius);
                l1.setStroke(Color.RED);
                l1.setStrokeWidth(5);
                Line l2 = new Line(-2.5, radius, -radius - 0.5, radius);
                l2.setStroke(Color.YELLOW);
                l2.setStrokeWidth(5);
                Line l3 = new Line(radius, -2.5, radius, -radius - 0.5);
                l3.setStroke(Color.BLUE);
                l3.setStrokeWidth(5);
                Line l4 = new Line(radius, 2 * radius + 2.5, radius, 3 * radius + 0.5);
                l4.setStroke(Color.BLACK);
                l4.setStrokeWidth(5);
                stack[count] = new StackPane();
                final int counter = count;
                stack[count].getChildren().addAll(circle, text);
                stack[count].setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent t) {
                        System.out.println(counter);
                    }
                });
                Group group = new Group(stack[count], l1, l2, l3, l4);
                Pane container = new Pane();
                container.getChildren().addAll(group);
                grid.add(container, c, r);
                count++;
            }
        }
        scrollpane.setContent(grid);
    }

    public void showMain(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("mainView.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void showGenerate(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("generatorView.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void showInputError(String content) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText("Invalid input");
        errorAlert.setContentText(content);
        errorAlert.showAndWait();
    }
}