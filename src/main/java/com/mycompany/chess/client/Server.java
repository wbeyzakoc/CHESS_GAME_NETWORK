package com.mycompany.chess.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {

    private static final int PORT = 5003;
    private static final Map<String, Room> ROOMS = new HashMap<>();

    public static void main(String[] args) throws Exception {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : PORT;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Chess server started on port " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(() -> handleClient(socket), "chess-client").start();
            }
        }
    }

    private static void handleClient(Socket socket) {
        try {
            ClientSession client = new ClientSession(socket);
            String firstMessage = client.input.readLine();
            String roomCode = parseRoomCode(firstMessage);
            Room room;
            ClientSession opponentToStart = null;

            synchronized (ROOMS) {
                room = getAvailableRoom(roomCode);
                opponentToStart = room.add(client);
            }

            if (opponentToStart == null) {
                client.output.println("INFO,Rakip bekleniyor... Oda: " + room.code);
                System.out.println("Player waiting in room " + room.code);
                return;
            }

            startMatch(room, opponentToStart, client);
        } catch (Exception e) {
            closeQuietly(socket);
        }
    }

    private static String parseRoomCode(String message) {
        if (message == null || message.trim().isEmpty()) {
            return "default";
        }

        String[] parts = message.split(",", 2);
        if (parts.length == 2 && "JOIN".equals(parts[0])) {
            String code = parts[1].trim();
            if (!code.isEmpty()) {
                return code;
            }
        }

        return "default";
    }

    private static Room getAvailableRoom(String requestedRoomCode) {
        if (isAutomaticRoom(requestedRoomCode)) {
            return findAutomaticRoom();
        }

        Room requestedRoom = ROOMS.computeIfAbsent(requestedRoomCode, Room::new);
        if (!requestedRoom.isFull()) {
            return requestedRoom;
        }

        return findAutomaticRoom();
    }

    private static boolean isAutomaticRoom(String roomCode) {
        return "default".equalsIgnoreCase(roomCode)
                || "auto".equalsIgnoreCase(roomCode)
                || "otomatik".equalsIgnoreCase(roomCode);
    }

    private static Room findAutomaticRoom() {
        int index = 1;
        while (true) {
            String code = "oda" + index;
            Room room = ROOMS.computeIfAbsent(code, Room::new);
            if (!room.isFull()) {
                return room;
            }
            index++;
        }
    }

    private static void startMatch(Room room, ClientSession p1, ClientSession p2) {
        p1.output.println("COLOR,beyaz");
        p2.output.println("COLOR,siyah");
        p1.output.println("INFO,TURK'S TURN");
        p2.output.println("INFO,TURK'S TURN");

        startRelay(room, p1, p2);
        startRelay(room, p2, p1);
        System.out.println("New chess match started in room " + room.code);
    }

    private static void startRelay(Room room, ClientSession from, ClientSession to) {
        new Thread(() -> {
            try {
                String msg;
                while ((msg = from.input.readLine()) != null) {
                    to.output.println(msg);
                }
            } catch (Exception ignored) {
            } finally {
                to.output.println("INFO,Rakip oyundan ayrıldı. Bilgisayar devralıyor.");
                closeQuietly(from.socket);
                closeQuietly(to.socket);
                synchronized (ROOMS) {
                    room.remove(from);
                    room.remove(to);
                    if (room.isEmpty()) {
                        ROOMS.remove(room.code);
                    }
                }
            }
        }, "chess-relay").start();
    }

    private static void closeQuietly(Socket socket) {
        try {
            socket.close();
        } catch (Exception ignored) {
        }
    }

    private static class ClientSession {

        private final Socket socket;
        private final BufferedReader input;
        private final PrintWriter output;

        ClientSession(Socket socket) throws Exception {
            this.socket = socket;
            this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.output = new PrintWriter(socket.getOutputStream(), true);
        }
    }

    private static class Room {

        private final String code;
        private ClientSession player1;
        private ClientSession player2;

        Room(String code) {
            this.code = code;
        }

        ClientSession add(ClientSession client) {
            if (player1 == null) {
                player1 = client;
                return null;
            }
            if (player2 == null) {
                player2 = client;
                return player1;
            }

            client.output.println("INFO,Bu oda dolu. Farklı oda kodu gir.");
            closeQuietly(client.socket);
            return null;
        }

        void remove(ClientSession client) {
            if (player1 == client) {
                player1 = null;
            }
            if (player2 == client) {
                player2 = null;
            }
        }

        boolean isEmpty() {
            return player1 == null && player2 == null;
        }

        boolean isFull() {
            return player1 != null && player2 != null;
        }
    }
}
