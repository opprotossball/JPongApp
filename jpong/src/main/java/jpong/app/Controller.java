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
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.scene.text.*;
import javafx.scene.*;


import java.io.IOException;
import java.util.stream.IntStream;

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
    private static double max_weights = 0;
    private static double min_weights = 0;
    private static final Color slider_background = Color.rgb(77,77,77);
    private static Mode mode = Mode.CONNECTED;
    private static Graph newGraph;

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
        max_weights = maxWeight;
        min_weights = minWeight;
    }
    public Color colorize(double value){
        double part = value/(max_weights-min_weights);
        int color = (int)(220 - part * 220);
        Color new_color = Color.rgb(color,color,220);
        return new_color;
    }
    public void initialize() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(0));
        grid.setHgap(0);
        grid.setVgap(0);
        int count = 0;
        int columns = 0;
        int rows = 0;
        if(newGraph != null){
            columns = newGraph.show_columns();
            rows = newGraph.show_rows();
        }
        StackPane[] stack = new StackPane[columns * rows]; /*!!!!!!!!!!!!!!!!!!!!!remember you need to do this manually !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                final Text text = new Text(String.valueOf(count));
                text.setFont(new Font(40));
                if(columns > 99 || rows  > 99){
                    text.setFont(new Font(15));
                    text.setStyle("-fx-font-weight: bold");
                }
                text.setBoundsType(TextBoundsType.VISUAL);
                Circle circle = new Circle(radius);
                circle.setStroke(Color.WHITE);
                circle.setStrokeWidth(5);
                circle.setStrokeType(StrokeType.INSIDE);
                circle.setFill(Color.AZURE);
                circle.relocate(0, 0);

                jpong.app.Node check_nodes = newGraph.show_node(count);
                int [] connected = check_nodes.showallnode();
                double [] weights = check_nodes.showallweights();
                Line l1 = new Line(2 * radius + 2.5, radius, 3 * radius + 0.5, radius);
                l1.setStrokeWidth(0);
                Line l2 = new Line(-2.5, radius, -radius - 0.5, radius);
                l2.setStrokeWidth(0);
                Line l3 = new Line(radius, -2.5, radius, -radius - 0.5);
                l3.setStrokeWidth(0);
                Line l4 = new Line(radius, 2 * radius + 2.5, radius, 3 * radius + 0.5);
                l4.setStrokeWidth(0);
                int weight_counter = 0;
                for(int element:connected) {
                    if(element != -1) {
                        if (element == count+1 && columns != 1) {
                            l1.setStroke(colorize(weights[weight_counter]));
                            l1.setStrokeWidth(5);
                        }
                        if (element == count-1 && columns != 1) {
                            l2.setStroke(colorize(weights[weight_counter]));
                            l2.setStrokeWidth(5);
                        }
                        if (element == count-columns) {
                            l3.setStroke(colorize(weights[weight_counter]));
                            l3.setStrokeWidth(5);
                        }
                        if (element == count+columns) {
                            l4.setStroke(colorize(weights[weight_counter]));
                            l4.setStrokeWidth(5);
                        }
                    }
                    weight_counter++;
                }
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
        if(columns<4 || rows <4){
            VBox grid_container = new VBox();
            grid_container.setMinSize(364,364);
            grid_container.getChildren().addAll(grid);
            VBox.setMargin(grid, new Insets(radius,0,0,radius));
            grid_container.setBackground(new Background(new BackgroundFill(slider_background, CornerRadii.EMPTY, Insets.EMPTY)));
            scrollpane.setContent(grid_container);
        }
        else {
            VBox grid_container = new VBox();
            grid_container.getChildren().addAll(grid);
            VBox.setMargin(grid, new Insets(radius,0,0,radius));
            grid_container.setBackground(new Background(new BackgroundFill(slider_background, CornerRadii.EMPTY, Insets.EMPTY)));
            scrollpane.setContent(grid_container);

        }
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