package jpong.app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LoadController {
    @FXML
    private TextField outFileLoad;
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

    public void loadGraph(ActionEvent event) throws IOException {
        String fileName = outFileLoad.getText();
        Graph newGraph = Reader.readGraph(fileName);
        MainController.setGraph(newGraph);
    }
}
