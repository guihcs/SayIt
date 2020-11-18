package com.sayit.ui.control.frame;

import com.sayit.data.Contact;
import com.sayit.di.Autowired;
import com.sayit.ui.navigator.Configurable;
import com.sayit.ui.navigator.Navigator;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class AddResponseController implements Configurable {

    @FXML
    private Circle imageView;
    @FXML
    private Label nameLabel;

    @Autowired
    private Stage stage;

    private Contact contact;


    public void accept() {
        Navigator.of(stage).popResult(contact);
    }

    public void cancel() {
        Navigator.of(stage).popResult(null);
    }

    @Override
    public void configure(Object param) {
        contact = (Contact) param;
        nameLabel.setText(contact.getName());
        imageView.setFill(new ImagePattern(contact.getPhoto()));
    }
}
