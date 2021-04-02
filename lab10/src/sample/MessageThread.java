package sample;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class MessageThread extends Thread {

    private Socket socket;
    private Scanner in;
    private PrintWriter out;

    public MessageThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream());

            while (in.hasNextLine()) {
                String msg = in.nextLine();
                synchronized (ServerMain.serverController.textArea) {
                    ServerMain.serverController.textArea.appendText(msg + '\n');
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
