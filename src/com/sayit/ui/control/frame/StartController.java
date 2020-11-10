package com.sayit.ui.control.frame;

import com.sayit.di.Autowired;
import com.sayit.ui.navigator.Navigator;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class StartController {

    @Autowired
    private Stage stage;

    public void go(){
        Navigator.of(stage).popResult(null);
    }
}
