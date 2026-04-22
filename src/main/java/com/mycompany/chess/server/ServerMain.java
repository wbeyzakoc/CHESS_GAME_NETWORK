package com.mycompany.chess.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class ServerMain {

    public static void main(String[] args) {
        int port = 5001;

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server çalışıyor... Port: " + port);

            Socket client1 = serverSocket.accept();
            System.out.println("Client 1 bağlandı!");

            Socket client2 = serverSocket.accept();
            System.out.println("Client 2 bağlandı!");

            BufferedReader in1 = new BufferedReader(
                    new InputStreamReader(client1.getInputStream()));
            PrintWriter out1 = new PrintWriter(client1.getOutputStream(), true);

            BufferedReader in2 = new BufferedReader(
                    new InputStreamReader(client2.getInputStream()));
            PrintWriter out2 = new PrintWriter(client2.getOutputStream(), true);

            // oyun başlasın
            out1.println("Oyun başladı! Sen beyazsın");
            out2.println("Oyun başladı! Sen siyahsın");

            while (true) {

                if (in1.ready()) {
                    String msg1 = in1.readLine();
                    System.out.println("Client1: " + msg1);
                    out2.println("HAMLE:" + msg1);
                }

                if (in2.ready()) {
                    String msg2 = in2.readLine();
                    System.out.println("Client2: " + msg2);
                    out1.println("HAMLE:" + msg2);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}