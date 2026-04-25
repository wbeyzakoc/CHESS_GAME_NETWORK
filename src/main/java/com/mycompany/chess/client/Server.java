package com.mycompany.chess.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static final int PORT = 5003;

    public static void main(String[] args) throws Exception {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : PORT;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Chess server started on port " + port);

            while (true) {
                Socket p1 = serverSocket.accept();
                PrintWriter out1 = new PrintWriter(p1.getOutputStream(), true);
                out1.println("INFO,Rakip bekleniyor...");

                Socket p2 = serverSocket.accept();
                PrintWriter out2 = new PrintWriter(p2.getOutputStream(), true);

                out1.println("COLOR,beyaz");
                out2.println("COLOR,siyah");
                out1.println("INFO,TURK'S TURN");
                out2.println("INFO,TURK'S TURN");

                startRelay(p1, p2);
                startRelay(p2, p1);
                System.out.println("New chess match started.");
            }
        }
    }

    private static void startRelay(Socket from, Socket to) {
        new Thread(() -> {
            try (BufferedReader input = new BufferedReader(new InputStreamReader(from.getInputStream()));
                    PrintWriter output = new PrintWriter(to.getOutputStream(), true)) {
                String msg;
                while ((msg = input.readLine()) != null) {
                    output.println(msg);
                }
            } catch (Exception ignored) {
                closeQuietly(to);
            } finally {
                closeQuietly(from);
            }
        }, "chess-relay").start();
    }

    private static void closeQuietly(Socket socket) {
        try {
            socket.close();
        } catch (Exception ignored) {
        }
    }
}
