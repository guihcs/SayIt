package com.sayit.ui.control.frame;

import com.sayit.data.Contact;
import com.sayit.data.ContactDao;
import com.sayit.di.Autowired;
import com.sayit.ui.navigator.Navigator;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class StartFrameController {

    @FXML
    private VBox rootContainer;

    @Autowired
    private Stage stage;

    @Autowired
    private ContactDao contactDao;

    public void initialize() {

        loadStart();
    }

    public void loadStart() {
        if(rootContainer.getChildren().size() > 0) rootContainer.getChildren().clear();
        Node node = Navigator.buildNamed("/startLayout", r -> {
            loadProfile();
        });
        rootContainer.getChildren().add(node);
        VBox.setVgrow(node, Priority.ALWAYS);
    }

    public void loadProfile() {
        if(rootContainer.getChildren().size() > 0) rootContainer.getChildren().clear();
        Node node = Navigator.buildNamed("/editProfile", c -> {
            if (c == null){
                loadStart();
            }else{
                Navigator.clearStack();
                contactDao.setUserProfile((Contact) c);
                Navigator.of(stage).pushNamed("/homeScene");
            }

        });
        rootContainer.getChildren().add(node);
        VBox.setVgrow(node, Priority.ALWAYS);
    }

}
