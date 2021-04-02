package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class ServerController {

    @FXML TextArea textArea;

    @FXML
    public void exit() {
        System.exit(0);
    }
}
