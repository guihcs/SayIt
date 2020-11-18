package com.sayit.ui.control.frame;

import com.sayit.data.Contact;
import com.sayit.di.Autowired;
import com.sayit.ui.control.view.ContactCell;
import com.sayit.ui.navigator.Configurable;
import com.sayit.ui.navigator.Navigator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;

public class SearchContactController implements Configurable {

    @FXML
    private TextField nameField;
    @FXML
    private ListView<Contact> contactListView;
    private ObservableList<Contact> contactObservableList;
    private List<Contact> contacts;

    @Autowired
    private Stage stage;


    public void initialize() {
        contactObservableList = FXCollections.observableArrayList();
        contactListView.setItems(contactObservableList);
        contactListView.setCellFactory(e -> {
            ContactCell contactCell = new ContactCell();
            contactCell.setOnMouseClicked(ev -> Navigator.of(stage).popResult(contactCell.getItem()));
            return contactCell;
        });
    }

    public void search() {
        if (contacts != null) {
            List<Contact> result = contacts.stream()
                    .filter(c -> c.getName().contains(nameField.getText()))
                    .collect(Collectors.toList());
            contactObservableList.clear();
            contactObservableList.addAll(result);
        }
    }

    public void close() {
        Navigator.of(stage).popResult(null);
    }

    @Override
    public void configure(Object param) {
        contacts = (List<Contact>) param;
        contactObservableList.addAll(contacts);
    }
}
