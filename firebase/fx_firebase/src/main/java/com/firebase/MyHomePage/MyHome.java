package com.firebase.MyHomePage;

import java.io.File;

import javax.swing.text.StyledEditorKit.BoldAction;

import com.firebase.AdmiSide.FileReceiver;
import com.firebase.AdmiSide.FileServer;
import com.firebase.controller.LoginController;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MyHome extends Application {
    private LoginController loginController;
     public FileServer fileServerobj;
    

   
    @Override
    public void start(Stage primaryStage) throws Exception {
    primaryStage.setTitle("Fly Share");
    // Text send = new Text("SEND");
   // send.setLayoutX(150);
   // send.setLayoutY(250);
  //  send.setFont(new Font(30));
   Text recive = new Text("RECIVE");
     recive.setLayoutX(600);
     recive.setLayoutY(550);
    recive.setFont(new Font(30));
       
        // Circle send_circle = new Circle(50,Color.SEAGREEN);
        // send_circle.setLayoutX(200);
        // send_circle.setLayoutY(300);
    //    send_circle.setAccessibleText("Send");
    Circle recive_circle = new Circle(90,Color.LIGHTGREEN);
       
        TextField browsTextField= new TextField("Browse");
        browsTextField.setPrefWidth(800);
        

        
      
    //    Button send_bt = new Button("SEND");
    //    send_bt.setStyle("-fx-background-Color:LIGHTSKYBLUE");
    //    send_bt.setGraphic(send_circle);
    //    send_bt.setTranslateX(180);
    //    send_bt.setTranslateY(300);


       Button recive_bt = new Button(" ");
       recive_bt.setGraphic(recive_circle);
       recive_bt.setTranslateX(500);
       recive_bt.setTranslateY(300);
       recive_bt.setStyle("-fx-background-Color:BLUEVIOLET");

    //    send_bt.setOnAction(new EventHandler<ActionEvent>() {

    //     @Override
    //     public void handle(ActionEvent event) {
           
    //         System.out.println("Please Enter the files");
    //         FileChooser sendfileChooser= new FileChooser();
    //        // sendfileChooser.getSelectionMode().showOpenMultipleDialog();
    //         FileChooser.ExtensionFilter chooseExtensionFilter = new FileChooser.ExtensionFilter(" files (*.*)", "*.*");
            
    //         sendfileChooser.getExtensionFilters().add(chooseExtensionFilter);
    //         File selectedFile = sendfileChooser.showOpenDialog(primaryStage);

    //         if(selectedFile!=null){
    //             System.out.println("Selected file:" + selectedFile.getAbsolutePath());
    //         }
    //         else{
    //             System.out.println("No selected file");

    //             } 
    //             fileServerobj = new FileServer();    
    //    }

       
    // });

        recive_bt.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                FileReceiver obj = new FileReceiver();
                
            }
            
        });
    
    //  VBox left_vb = new VBox(10,send_circle,send_bt);
    //     BorderStroke borderStroke = new BorderStroke(Color.BISQUE,BorderStrokeStyle.SOLID,null,new BorderWidths(7)  , new javafx.geometry.Insets(0));
    //     Border left_vb_border = new Border(borderStroke);
    //    left_vb.setBorder(left_vb_border);
    //     send_bt.setLayoutX(200);
    //     send_bt.setLayoutY(300);
    //     left_vb.setLayoutY(100);
    //     left_vb.setPrefHeight(600);
    //     left_vb.setPrefWidth(400);
    //     left_vb.setStyle("-fx-background-Color:LIGHTSKYBLUE");

    VBox right_vb = new VBox(recive_bt);
        BorderStroke borderStroke_right  = new BorderStroke(Color.BISQUE,BorderStrokeStyle.SOLID,null,new BorderWidths(7)  , new javafx.geometry.Insets(0));
        Border right_vb_border = new Border(borderStroke_right);
        recive.setLayoutY(500);
        recive_circle.setLayoutX(30);
        recive_circle.setTranslateX(10);
        right_vb.setBorder(right_vb_border);
        recive_bt.setTranslateX(100);
        recive_bt.setTranslateY(270);      
        right_vb.setPrefHeight(600);
        right_vb.setPrefWidth(400);
        right_vb.setLayoutX(200);
        right_vb.setLayoutY(100);
       // right_vb.setStyle("-fx-background-Color:SnadyBrown");

        Group gr = new Group(right_vb,browsTextField);
      
        Scene sc = new Scene( gr,800,800);

       sc.setFill(Color.BLUEVIOLET);
        primaryStage.setScene(sc);
        primaryStage.show();
    }

    private void right_vb_border(Border right_vb_border) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'right_vb_border'");
    }
    
    

    
}
