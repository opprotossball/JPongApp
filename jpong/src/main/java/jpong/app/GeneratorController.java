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
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.scene.text.*;
import javafx.scene.*;


import java.io.IOException;
import java.util.Objects;

public class GeneratorController {
    enum Mode {
        ALL,
        RANDOM,
        CONNECTED
    }
    private final static String clickedButtonColor = "#03b1fc";
    private static Mode mode = Mode.CONNECTED;
    @FXML
    private TextField rowsGenerate, columnsGenerate, minWeightGenerate, maxWeightGenerate, outFileGenerate;

    @FXML
    private Button allButton, connectedButton, randomButton;

    public void initialize(){
        allButton.setStyle("-fx-background-color: " + clickedButtonColor);
    }
    @FXML
    private void setAll(ActionEvent event) {
        connectedButton.setStyle("");
        randomButton.setStyle("");
        allButton.setStyle("-fx-background-color: " + clickedButtonColor);
        mode = Mode.ALL;
    }

    @FXML
    private void setRandom(ActionEvent event) {
        allButton.setStyle("");
        connectedButton.setStyle("");
        randomButton.setStyle("-fx-background-color: " + clickedButtonColor);
        mode = Mode.RANDOM;
    }

    @FXML
    private void setConnected(ActionEvent event) {
        allButton.setStyle("");
        randomButton.setStyle("");
        connectedButton.setStyle("-fx-background-color: " + clickedButtonColor);
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
            Alert successMessage = new Alert(Alert.AlertType.INFORMATION);
            successMessage.setHeaderText("Success!");
            successMessage.setContentText("Graph was generated and saved in file " + outFile);
            successMessage.showAndWait();
            MainController.setGraph(newGraph);
        } catch (IOException e) {
            showInputError("File name not specified");
        }
    }

    public void showMain(ActionEvent event) throws IOException {
        Parent root;
        Scene scene;
        Stage stage;
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("mainView.fxml")));
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