package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerMain extends Application {

    static ServerController serverController;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("server.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Server");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

        serverController = loader.getController();

        Thread thread = new Thread(new ConnectionListener());
        thread.start();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public class ConnectionListener implements Runnable {

        private ServerSocket serverSocket;
        private ExecutorService pool = Executors.newFixedThreadPool(50);

        public ConnectionListener() {
            try {
                serverSocket = new ServerSocket(8080);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    pool.execute(new MessageThread(socket));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}