package com.firebase.controller;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import com.firebase.AdmiSide.FileServer;
import com.firebase.MyHomePage.MyHome1;
import com.firebase.firebase_connection.FirebaseService;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.common.io.Files;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import com.firebase.AdmiSide.FileServer;



import javafx.stage.Stage;

public class LoginController extends Application{

    private Stage primaryStage;
    private FirebaseService firebaseService;
    private Scene scene;
    private MyHome1 myHome1;
    public static Scene homescScene;
    public FileServer fileServerobj;
   
    
    public void setPrimaryStageScene(Scene scene) {
        
        primaryStage.setScene(scene);

    }
    public Stage getPrimaryStage(){
        return primaryStage;
    }
    // public void setHomeScene(Scene homScene){
    //     this.homescScene = homScene;
    // }
    public void initializeLoginScene() {
        Scene loginScene = createLoginScene();
        this.scene = loginScene;
        myHome1 = new MyHome1(this);
        primaryStage.setScene(loginScene);


    }
     public void initializeHomeScene(){
        myHome1 = new MyHome1(this);
        scene = myHome1.getScene();
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }
    public void sendFile(Stage stage) {
        System.out.println("in sendfiel");
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter chooseExtensionFilter = new FileChooser.ExtensionFilter(" files (*.*)", "*.*");
        fileChooser.getExtensionFilters().add(chooseExtensionFilter);
        File file = fileChooser.showOpenDialog(stage);
        fileServerobj = new FileServer();

        if (file != null) {
            new Thread(() -> {
                try (Socket socket = new Socket("192.168.88.246", 8084);
                     FileInputStream fileInputStream = new FileInputStream(file);
                     BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
                     OutputStream outputStream = socket.getOutputStream()) {

                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 1, bytesRead);
                    }

                    System.out.println("File sent");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        
    }
    


    
    @SuppressWarnings("deprecation")
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        myHome1 = new MyHome1(this);


        try{
            String serviceAccountJsonPath = "fx_firebase\\src\\main\\resources\\fx-auth-fb.jsons";

            FileInputStream serviceAccount = new FileInputStream("fx_firebase\\src\\main\\resources\\fx-auth-fb.json");

            
            FirebaseOptions options = new FirebaseOptions.Builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            //.setDatabaseUrl("https://fx-auth-fb-default-rtdb.asia-southeast1.firebasedatabase.app")
            .build();

            FirebaseApp.initializeApp(options);

            }catch(IOException e) {
                e.printStackTrace();
            }

            Scene scene = createLoginScene();
            this.scene = scene;
            primaryStage.setTitle("Firebase Auth Example");
            primaryStage.setScene(scene);
            primaryStage.show();
            MyHome1 myHome1 = new MyHome1(this);

            Button sendbutton = myHome1.getSendButton();
           // System.out.println("near send bt onclick");
          //  sendbutton.setOnAction(e-> sendFile(primaryStage));
        //   sendbutton.setOnAction(new EventHandler<ActionEvent>() {

        //     @Override
        //     public void handle(ActionEvent event) {
        //        System.out.println("888899999");
        //         sendFile(primaryStage);
        //     }
            
        //   });
            
        
               

           


    }
   
    private Scene createLoginScene() {
        
        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Button signUpButton = new Button("Sign Up");
        signUpButton.setStyle("-fx-background-color: white; -fx-text-fill:black");
        Button loginButton = new Button("Log In");
        loginButton.setStyle("-fx-background-color: white; -fx-text-fill:black");

        Image backgroundimg = new Image("File:C:\\Users\\hp\\firebase\\fx_firebase\\src\\assets\\images\\Gemini_Generated_Image_jdrtmsjdrtmsjdrt.jpeg");
        ImageView backImageView = new ImageView(backgroundimg);

       Image logo = new Image("File:C:\\Users\\hp\\firebase\\fx_firebase\\src\\assets\\images\\logo.png");
        ImageView logoimageView = new ImageView(logo);
        logoimageView.setFitHeight(200);
        logoimageView.setFitWidth(250);
        logoimageView.setLayoutX(280);
        logoimageView.setLayoutY(150);

        firebaseService = new FirebaseService(this,emailField,passwordField);

        signUpButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                firebaseService.signUp();

            }
        });

        loginButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if(firebaseService.login()){
                    
                    
                    primaryStage.setScene(homescScene);
                    System.out.println(homescScene);
                    primaryStage.show();
                    System.out.println(this);
                  //  fileServerobj = new FileServer();
                    

                }
                
            }
        });
        

        VBox fieldBox = new VBox(20,emailField,passwordField);
        fieldBox.setLayoutX(300);
        fieldBox.setLayoutY(400);
        HBox buttonBox = new HBox(20,loginButton,signUpButton);
        VBox comBox = new VBox(10,fieldBox,buttonBox);
        comBox.setLayoutX(300);
        comBox.setLayoutY(650);
        Pane viewPane = new Pane(backImageView,comBox,logoimageView);
        
        

        return new Scene(viewPane,800,1000);
        
       
        


    }
    
}