package jpong.app;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Label welcomeText;
    private static final double BUTTONS_PER_LINE = 50;
    private static final double NUM_BUTTON_LINES = 50;
    private static final double radius = 25;
    private static final Color lineColor = Color.FIREBRICK.deriveColor(0, 1, 1, .6);
    @FXML
    private ScrollPane scrollpane = new ScrollPane();

    private static Graph graph;

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

    public static void setGraph(Graph newGraph) {
        graph = newGraph;
    }

    public static Graph getGraph() {
        return graph;
    }

    public void showGenerate(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("generatorView.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void showLoad(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("loadView.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void checkConnection() {
        CheckConnection checker = new CheckConnection();
        Alert connectionMessage = new Alert(Alert.AlertType.INFORMATION);
        connectionMessage.setHeaderText("Connection checked!");
        int result = checker.checkConnection(graph);
        if (result == 0) {
            connectionMessage.setContentText("Graph is connected");
        } else if (result == 1) {
            connectionMessage.setContentText("Graph is not connected");
        }
        connectionMessage.showAndWait();
    }

    public void showPath(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("pathView.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
