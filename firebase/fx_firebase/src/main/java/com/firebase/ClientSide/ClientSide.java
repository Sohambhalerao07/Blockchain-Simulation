package com.firebase.ClientSide;
import java.io.*;
import java.net.Socket;


public class ClientSide {
    private static final String SERVER_ADDRESS = "192.168.88.246";
    private static final int SERVER_PORT = 8082;

    ClientSide(){
        File file = new File("send_file.txt");

        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             FileInputStream fileInputStream = new FileInputStream(file);
             BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
             OutputStream outputStream = socket.getOutputStream()) {
                    System.out.println("in try");
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            System.out.println("File sent");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        new ClientSide();
    }
    
   

    }

    


