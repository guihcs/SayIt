package com.sayit.control;

import com.sayit.data.*;
import com.sayit.di.Injector;
import com.sayit.ui.navigator.Navigator;
import javafx.application.Application;
import javafx.stage.Stage;

public class ChatApplication extends Application {

    //Layouts
    public static final String START_LAYOUT = "/layout/window/layout_start.fxml";
    public static final String FIND_CONTACT_LAYOUT = "/layout/window/layout_find_contact.fxml";
    public static final String EDIT_PROFILE_LAYOUT = "/layout/window/layout_edit_profile.fxml";
    public static final String CONTACT_VIEW = "/layout/view/view_contact_cell.fxml";
    public static final String MESSAGE_VIEW = "/layout/view/view_message_cell.fxml";
    //Styles
    public static final String FIND_CONTACT_STYLE = "/stylesheet/style_find_contact.css";
    public static final String EDIT_CONTACT_STYLE = "/stylesheet/style_edit_profile.css";
    public static final String CONTACT_STYLE = "/stylesheet/style_contact.css";
    public static final String MESSAGE_STYLE = "/stylesheet/style_message.css";
    public static final String START_STYLE = "/stylesheet/style_start.css";
    //Constants
    public static final int MAX_NAME_LENGTH = 20;

    public static void main(String[] args) {
        launch(args);
    }

    public static String getStyleSheet(String path) {
        return ChatApplication.class.getResource(path).toExternalForm();
    }

    @Override
    public void start(Stage primaryStage) {
        Injector.registerProvider(Stage.class, primaryStage);
        Navigator.loadFrom("routes/routes.json");

//        RequestMediator mediator = new RequestMediator();
        ContactDao contactDao = new ContactDao();
        Injector.registerProvider(ContactDao.class, contactDao);
//
//        setContactDao(contactDao);
//        setRequestable(mediator);
//
//        mediator.start();
        Navigator.of(primaryStage).pushNamed("/startScene");
    }


    @Override
    public void stop() {
//        try {
//            super.stop();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        requestable.stopServices();
    }

}
