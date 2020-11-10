package com.sayit.ui.control.frame;

import com.sayit.control.ChatApplication;
import com.sayit.data.Contact;
import com.sayit.di.Autowired;
import com.sayit.ui.control.ContactManager;
import com.sayit.ui.navigator.Configurable;
import com.sayit.ui.navigator.Navigator;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.nio.file.Path;

public class ProfileEditController implements Configurable {

    private Window ownerWindow;
    private Contact contact;

    @FXML
    private Circle roundImage;
    @FXML
    private TextField nameField;

    @Autowired
    private Stage stage;

    public void initialize() {
        contact = new Contact("", new Image("icons/avatar.png"), "127.0.1.1");
        configure(contact);
    }


    //        editController.setOwnerWindow(window);
////        if(getUserProfile() != null) {
////            editController.setContact(getUserProfile());
////        }
////        editController.setConcludeCallback(contact -> {
////            contactDao.setUserProfile(contact);
////            chatHome.setUserProfile(contact);
////            window.close();
////        });
//        editController.setBackCallback(window::close);

    public void requestEditFocus() {
        nameField.requestFocus();
    }


    public void close() {
        Navigator.of(stage).popResult(null);
    }

    public void confirm() {
        if(!nameField.getText().isEmpty()) {
            contact.setName(nameField.getText());
            Navigator.of(stage).popResult(contact);
        } else if(nameField.getText().length() > ChatApplication.MAX_NAME_LENGTH) {
            showNameAlert("Nome muito grande.");
        }else {
            showNameAlert("Nome inválido.");
        }
    }

    private void showNameAlert(String text) {
        Alert nameAlert = new Alert(Alert.AlertType.WARNING);
        nameAlert.setTitle("Erro no nome.");
        nameAlert.setHeaderText(null);
        nameAlert.setContentText(text);
        nameAlert.showAndWait();
    }

    public void getImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Image");
        var imagePath = fileChooser.showOpenDialog(ownerWindow);
        if(imagePath != null) {
            Path path = imagePath.toPath();
            Image image = new Image(path.toUri().toString());
            roundImage.setFill(new ImagePattern(image));
            contact.setPhoto(image);
        }
    }

    public void setOwnerWindow(Window ownerWindow) {
        this.ownerWindow = ownerWindow;
    }

    @Override
    public void configure(Object param) {
        Contact c = (Contact) param;
        contact.setName(c.getName());
        contact.setPhoto(c.getPhoto());
        roundImage.setFill(new ImagePattern(c.getPhoto()));
        nameField.setText(c.getName());
    }
}
