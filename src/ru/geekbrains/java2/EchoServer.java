package ru.geekbrains.java2;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
    public static final int PORT = 4004;

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("Сервер запущен, ожидаем подключения...");
            Socket socket = server.accept();
            System.out.println("Клиент подключился");

            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            new Thread(() -> {
                while (true) {
                    try {
                        String message = in.readUTF();
                        if (message.equalsIgnoreCase("exit")) {
                            System.out.println("Клиент завершил сеанс");
                            System.exit(0);
                        }
                        System.out.println("Клиент: " + message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            while (true) {
                String message = reader.readLine();
                out.writeUTF(message);
            }

        } catch (IOException e) {
            System.err.println("Ошибка подключения");
            e.printStackTrace();
        }

    }
}