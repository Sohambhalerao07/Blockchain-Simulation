package com.firebase.AdmiSide;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FileReceiver extends Application {

    private static File saveLocation = new File("received_file"); // Default save location
    private TextField numbTextField;
    private int port;

    public FileReceiver() {
        // Constructor can be empty if we are handling the server start in a separate method
    }

    private void startFileServer(int port) {
        System.out.println("Starting FileServer on port " + port + "...");
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                System.out.println("Server started, waiting for connections...");

                while (true) {
                    Socket socket = serverSocket.accept();
                    System.out.println("Client connected: " + socket.getInetAddress());
                    new Thread(new FileHandler(socket)).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static class FileHandler implements Runnable {
        private Socket socket;

        public FileHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            System.out.println("Handling client...");
            try (InputStream inputStream = socket.getInputStream();
                 BufferedOutputStream fileOut = new BufferedOutputStream(new FileOutputStream(saveLocation))) {

                // Handshaking
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

                String handshakeMessage = reader.readLine();
                if ("HELLO SERVER".equals(handshakeMessage)) {
                    writer.println("HELLO CLIENT");
                } else {
                    System.out.println("Invalid handshake message.");
                    return;
                }

                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    fileOut.write(buffer, 0, bytesRead);
                }

                System.out.println("File received successfully.");
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

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Flyshare");
        
        Label portLb = new Label("Enter Port Number Here");
        Font font = Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC, 22);
        portLb.setFont(font);
        portLb.setTextFill(Color.YELLOW);
        portLb.setLayoutY(150);
        portLb.setLayoutX(265);

       

        Text receiveText = new Text("RECEIVE");
        numbTextField = new TextField();
        numbTextField.setPromptText("Enter port number");
        numbTextField.setMaxWidth(200);
        numbTextField.setLayoutY(200);

        // Button for receiving
        Button receiveButton = new Button("Receive");
        receiveButton.setStyle("-fx-background-color: BLUEVIOLET");
        receiveButton.setMaxHeight(200);
        receiveButton.setMaxWidth(200);
        receiveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    port = Integer.parseInt(numbTextField.getText());
                    // Open file chooser to select save location
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Save File");

                    // Set extension filters
                    FileChooser.ExtensionFilter txtFilter = new FileChooser.ExtensionFilter("Text Files (*.txt)", "*.txt");
                    FileChooser.ExtensionFilter pdfFilter = new FileChooser.ExtensionFilter("PDF Files (*.pdf)", "*.pdf");
                    FileChooser.ExtensionFilter allFilesFilter = new FileChooser.ExtensionFilter("All Files", "*.*");
                    fileChooser.getExtensionFilters().addAll(txtFilter, pdfFilter, allFilesFilter);

                    saveLocation = fileChooser.showSaveDialog(primaryStage);

                    if (saveLocation != null) {
                        startFileServer(port);
                    } else {
                        System.out.println("File save location not selected.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid port number. Please enter a valid number.");
                }
            }
        });

        // Image for receiving
        Image receiveImage = new Image("File:C:/Users/hp/firebase/fx_firebase/src/assets/images/inbox.png");
        ImageView receiveImageView = new ImageView(receiveImage);
        receiveImageView.setFitHeight(300);
        receiveImageView.setFitWidth(300);
        receiveImageView.setOnMouseClicked(event -> {
            try {
                port = Integer.parseInt(numbTextField.getText());
                // Open file chooser to select save location
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save File");

                // Set extension filters
                FileChooser.ExtensionFilter txtFilter = new FileChooser.ExtensionFilter("Text Files (*.txt)", "*.txt");
                FileChooser.ExtensionFilter pdfFilter = new FileChooser.ExtensionFilter("PDF Files (*.pdf)", "*.pdf");
                FileChooser.ExtensionFilter allFilesFilter = new FileChooser.ExtensionFilter("All Files", "*.*");
                fileChooser.getExtensionFilters().addAll(txtFilter, pdfFilter, allFilesFilter);

                saveLocation = fileChooser.showSaveDialog(primaryStage);

                if (saveLocation != null) {
                    startFileServer(port);
                } else {
                    System.out.println("File save location not selected.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid port number. Please enter a valid number.");
            }
        });
      
       Label receivelb = new Label("Click here to receive");
       receivelb.setLayoutY(600);
       receivelb.setLayoutX(300);
       receivelb.setTextFill(Color.YELLOW);
       Font font1 = Font.font("Arial", FontPosture.ITALIC, 15);
        receivelb.setFont(font);
        // StackPane to overlay image on button
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(receiveButton, receiveImageView);

        VBox rightVBox = new VBox(numbTextField,  stackPane);
        rightVBox.setSpacing(10);
        rightVBox.setAlignment(Pos.CENTER);  // Aligns children in the center of VBox

        BorderStroke borderStrokeRight = new BorderStroke(Color.BISQUE, BorderStrokeStyle.SOLID, null, new BorderWidths(7), new javafx.geometry.Insets(0));
        Border rightVBoxBorder = new Border(borderStrokeRight);

        rightVBox.setBorder(rightVBoxBorder);
        rightVBox.setPrefHeight(600);
        rightVBox.setPrefWidth(400);
        rightVBox.setLayoutX(200);
        rightVBox.setLayoutY(100);
        Group root = new Group(portLb,rightVBox,receivelb);

        Scene scene = new Scene(root, 800, 800);
        scene.setFill(Color.BLUEVIOLET);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
