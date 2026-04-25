/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chess.client;

/**
 *
 * @author beyzamacbook
 */
 

import java.io.*;
import java.net.*;

public class ClientConnection {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public ClientConnection(String host, int port) throws Exception {
        socket = new Socket(host, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    public void send(String msg) {
        out.println(msg);
    }

    public String receive() throws Exception {
        return in.readLine();
    }
}
