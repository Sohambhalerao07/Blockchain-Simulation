package com.firebase.AdmiSide;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class FileServer {
    private static final int PORT = 8087;

 public FileServer(){
    System.out.println("in FileServer");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started, waiting for connections...");

            while (true) {
                System.out.println(PORT);          
                Socket socket = serverSocket.accept();
                System.out.println("Client connected: " + socket.getInetAddress());

                new Thread(new FileHandler(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   public static class FileHandler implements Runnable {
        private Socket socket;
        public FileHandler(Socket socket) {
            this.socket = socket;
            run();
        }

        @Override
        public void run() {
            System.out.println("in run");
            try (InputStream inputStream = socket.getInputStream();
                BufferedOutputStream fileOut = new BufferedOutputStream(new FileOutputStream("received_file.txt"))) {

                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    fileOut.write(buffer, 0, bytesRead);
                }

                System.out.println("File received");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
}



