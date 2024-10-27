package com.firebase.firebase_connection;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import org.json.JSONObject;

import com.firebase.MyHomePage.MyHome1;
import com.firebase.controller.LoginController;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class FirebaseService {

    private TextField emailField;
    private PasswordField passwordField;
    private LoginController loginController;
    private Scene homessScene;
    


    public FirebaseService(LoginController loginController,TextField emailField,PasswordField passwordField) {
        this.loginController = loginController;
        this.emailField = emailField;
        this.passwordField = passwordField;

    }

    public boolean signUp() {
        String email = emailField.getText();
        String password = passwordField.getText();

        try{

            UserRecord.CreateRequest request = new UserRecord.CreateRequest().setEmail(email).setPassword(password).setDisabled(false);

            UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
            System.out.println("Successfully created user: " + userRecord.getUid());
            showAlert("Success","User created successfully.");
            return true;

        } catch(FirebaseAuthException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to create user : " + e.getMessage());
            return false;

        }
    }

    public boolean login() {
        String email = emailField.getText();
        String password = passwordField.getText();
       // System.out.println("user email"+email);
       // System.out.println("user password"+password);

        try{

            String apikey = "AIzaSyB2P5cs2WODx0kGQ0KrVflIbz8hoWjXQMk";

            URL url = new URL("https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=" + apikey);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type","application/json;charset=UTF-8");
            conn.setDoOutput(true);

            JSONObject jsonRequest = new JSONObject();
            jsonRequest.put("email",email);
            jsonRequest.put("password",password);
            jsonRequest.put("returnSecureToken",true);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonRequest.toString().getBytes(StandardCharsets.UTF_8);
                os.write(input,0,input.length);
            }
            System.out.println(conn.getResponseCode());
            if(conn.getResponseCode() == 200) {
               System.out.println("in login response");

               Scene scene = new MyHome1(loginController).getScene();
               System.out.println("123:"+scene);
               LoginController.homescScene=scene;
               
               //new MyHome1(loginController);
                //showAlert(true);
                
                return  true;

            } else {
                showAlert("Invalid Login","Invalid credentials!!!");
                return false;

            }


        } catch (Exception e){
            e.printStackTrace();
            showAlert(false);
            return false;
        }
    }

    private void showAlert(String title,String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();

    }

    private void showAlert(boolean isLoggedIn) {

        Label msg = new Label("Welcome");
        msg.setAlignment(Pos.CENTER);

        Button logoutButton = new Button("Logout");

        VBox vBox = new VBox(100,msg,logoutButton);

        logoutButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                loginController.initializeLoginScene();

            }
        });

        Scene scene = new Scene(vBox,400,200);
        loginController.setPrimaryStageScene(scene);

    }

    
}

