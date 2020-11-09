package com.sayit.ui.control;

import com.sayit.control.ChatApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;

public class FXMLManager {

    public static FXMLLoader getLoader(String path) {
        return new FXMLLoader(ChatApplication.class.getResource(path));
    }

    public static Node loadFromLoader(FXMLLoader loader) {
        try {
            return loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
