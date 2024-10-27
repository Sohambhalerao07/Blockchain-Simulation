package com.firebase;


import com.firebase.AdmiSide.FileReceiver;
import com.firebase.MyHomePage.MyHome;
import com.firebase.MyHomePage.MyHome1;
import com.firebase.controller.LoginController;

import javafx.application.Application;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        Application.launch(FileReceiver.class,args);
    }
}