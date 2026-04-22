package com.mycompany.chess.client;

import java.net.Socket;
import java.io.*;
import java.util.Scanner;

public class ClientMain {

    public static void main(String[] args) {
        String ip = "localhost";
        int port = 5001;

        try {
            Socket socket = new Socket(ip, port);
            System.out.println("Server'a bağlanıldı!");

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );

            Scanner scanner = new Scanner(System.in);

            // serverdan ilk mesaj
            System.out.println(in.readLine());

            while (true) {

                // kullanıcıdan hamle al
                if (scanner.hasNextLine()) {
                    String msg = scanner.nextLine();
                    out.println(msg);
                }

                // serverdan geleni oku
                if (in.ready()) {
                    String response = in.readLine();
                    System.out.println(response);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}