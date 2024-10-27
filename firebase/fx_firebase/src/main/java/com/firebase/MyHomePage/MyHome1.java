package com.firebase.MyHomePage;



import java.io.IOException;
import java.net.Socket;

import com.firebase.AdmiSide.FileReceiver;
import com.firebase.AdmiSide.FileServer;
import com.firebase.controller.LoginController;
import com.google.protobuf.DescriptorProtos.FieldDescriptorProto.Label;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class MyHome1 {
    public LoginController loginController;
    public GridPane view;
    private Scene homescScene;
    public Button send_bt;
    public FileServer fileServer;
    int PORT=8082;

   
    
   
    public MyHome1(LoginController loginController) {
        this.loginController = loginController;
        initialize();
    }
    public GridPane getView(){
        return view;
    }
    public Scene getScene(){
        System.out.println(homescScene);
        return homescScene;
    }
    public Button getSendButton(){
        return send_bt;
    }
    

    public Scene initialize() {
        
        view = new GridPane();

      

        Circle send_circle = new Circle(50, Color.SEAGREEN);
        
        
        // send_circle.setAccessibleText("Send");
        Circle recive_circle = new Circle(50, Color.SEAGREEN);
        

        TextField browsTextField = new TextField("Browse");
        browsTextField.setPrefWidth(800);

        send_bt = new Button("SEND");
      send_bt.setStyle("-fx-background-Color:LIGHTSKYBLUE");
        send_bt.setGraphic(send_circle);
       
        send_bt.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
               System.out.println("888899999");
                loginController.sendFile(loginController.getPrimaryStage());
            }
            
          });
            

        Button recive_bt = new Button("RECEIVE");
        recive_bt.setGraphic(recive_circle);
        recive_bt.setTranslateX(300);
       recive_bt.setTranslateY(150);
        recive_bt.setStyle("-fx-background-Color:SandyBrown");
        recive_bt.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            FileReceiver obj = new FileReceiver();
          
        }
             
     });
                  
                



         VBox left_vb = new VBox(10,send_bt);
        BorderStroke borderStroke = new BorderStroke(Color.BISQUE,BorderStrokeStyle.SOLID,null,new BorderWidths(7)  , new javafx.geometry.Insets(0));
        Border left_vb_border = new Border(borderStroke);
       left_vb.setBorder(left_vb_border);
       send_bt.setTranslateX(300);
       send_bt.setTranslateY(150);
        send_bt.setLayoutX(200);
        send_bt.setLayoutY(300);
        left_vb.setLayoutY(100);
        left_vb.setPrefHeight(600);
        left_vb.setPrefWidth(1000);
        left_vb.setStyle("-fx-background-Color:Orange");

        VBox right_vb = new VBox(recive_bt);
        BorderStroke borderStroke_right  = new BorderStroke(Color.BISQUE,BorderStrokeStyle.SOLID,null,new BorderWidths(7)  , new javafx.geometry.Insets(0));
        Border right_vb_border = new Border(borderStroke_right);
        right_vb.setBorder(right_vb_border);
        recive_bt.setTranslateX(300);
        recive_bt.setTranslateY(150);      
        right_vb.setPrefHeight(600);
        right_vb.setPrefWidth(1000);
        right_vb.setLayoutX(400);
        right_vb.setLayoutY(100);
        right_vb.setStyle("-fx-background-Color:DarkBlue");
        
        
        view.add(right_vb, 1, 0);
        view.add(left_vb, 1, 1);
        view.add(send_circle, 1, 1);
        view.add(recive_circle, 1, 0);
      //  view.add(send, 1, 1);
      //  view.add(recive, 1, 0);

        homescScene = new Scene(view,800,800);
       
        return homescScene;
    }
}
