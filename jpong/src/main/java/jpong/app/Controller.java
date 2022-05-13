package jpong.app;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
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

public class Controller {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Label welcomeText;
    private static final double BUTTONS_PER_LINE = 50;
    private static final double NUM_BUTTON_LINES = 50;
    private static final double radius = 25;
    private int moves =0;
    private static final Color lineColor = Color.FIREBRICK.deriveColor(0, 1, 1, .6);

    private static double textToDouble(double var, String str){
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e){
            return var;
        }
    }
    private static int textToInt(int var, String str){
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e){
            return var;
        }
    }
    @FXML
    private ScrollPane scrollpane = new ScrollPane();

    @FXML
    private TextField rowsGenerate, columnsGenerate, minWeightGenerate, maxWeightGenerate, outFileGenerate;

    @FXML
    private void generate(ActionEvent event) {
        int rows = 3;
        int columns = 3;
        double minWeight = 0.0;
        double maxWeight = 1.0;
        String outFile;
        rows = textToInt(rows, rowsGenerate.getText());
        columns = textToInt(columns, columnsGenerate.getText());
        minWeight = textToDouble(minWeight, minWeightGenerate.getText());
        maxWeight = textToDouble(maxWeight, maxWeightGenerate.getText());
        outFile = outFileGenerate.getText();
        System.out.println(outFile);
        Graph newGraph = new Graph(rows*columns);
        ConnectedEdges gen = new ConnectedEdges();
        newGraph = gen.generate((int)minWeight, (int)maxWeight, columns, rows);
        try {
            gen.saveGraph(outFile, newGraph);
        } catch (IOException e){
            System.out.println("Something is not yes :( (nazwy pliku nie podales)");
        }
    }
    public void initialize(){
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(0));
        grid.setHgap(0);
        grid.setVgap(0);
        int count =0;
        StackPane[] stack = new StackPane[50*50]; /*!!!!!!!!!!!!!!!!!!!!!remember you need to do this manually !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/
        for (int r = 0; r < NUM_BUTTON_LINES; r++) {
            for (int c = 0; c < BUTTONS_PER_LINE; c++) {
                final Text text = new Text(String.valueOf(count));
                text.setFont(new Font(15));
                text.setBoundsType(TextBoundsType.VISUAL);
                Circle circle=new Circle(radius);
                circle.setStroke(Color.FORESTGREEN);
                circle.setStrokeWidth(5);
                circle.setStrokeType(StrokeType.INSIDE);
                circle.setFill(Color.AZURE);
                circle.relocate(0, 0);
                Line l1 = new Line(2*radius+2.5, radius, 3*radius+0.5, radius);
                l1.setStroke(Color.RED);
                l1.setStrokeWidth(5);
                Line l2 = new Line(-2.5, radius, -radius-0.5, radius);
                l2.setStroke(Color.YELLOW);
                l2.setStrokeWidth(5);
                Line l3 = new Line(radius, -2.5, radius, -radius-0.5);
                l3.setStroke(Color.BLUE);
                l3.setStrokeWidth(5);
                Line l4 = new Line(radius, 2*radius+2.5, radius, 3*radius+0.5);
                l4.setStroke(Color.BLACK);
                l4.setStrokeWidth(5);
                stack[count] = new StackPane();
                final int counter = count;
                stack[count].getChildren().addAll(circle, text);
                stack[count].setOnMouseClicked(new EventHandler<MouseEvent>(){
                    @Override
                    public void handle(MouseEvent t){
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
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void showGenerate(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("generatorView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}