package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientController {

    private Socket socket;
    private Scanner in;
    private PrintWriter out;

    @FXML TextField usernameField;
    @FXML TextField messageField;

    @FXML
    public void initialize() {
        try {
            socket = new Socket("localhost", 8080);
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void sendMessage() {
        out.println(usernameField.getText() + ": " + messageField.getText());
        out.flush();
    }

    @FXML
    public void exit() {
        try {
            socket.close();
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
