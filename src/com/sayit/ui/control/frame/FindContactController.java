package com.sayit.ui.control.frame;

import com.sayit.data.Contact;
import com.sayit.ui.control.ContactManager;
import com.sayit.ui.control.view.ContactCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.List;

public class FindContactController {

    private Runnable closeCallback;
    private ContactManager contactManager;

    @FXML
    private TextField nameField;
    @FXML
    private ListView<Contact> contactListView;
    private ObservableList<Contact> contactObservableList;

    public void initialize() {
        contactObservableList = FXCollections.observableArrayList();
        contactListView.setItems(contactObservableList);
        contactListView.setCellFactory(e -> {
            ContactCell contactCell = new ContactCell();
            //fixme Set click callback contact

            return contactCell;
        });
    }


    public void close() {
        if(closeCallback != null) closeCallback.run();
    }

    public void search() {
        //TODO Guilherme search
    }

    public void setContactList(List<Contact> contactObservableList) {
        this.contactObservableList.clear();
        this.contactObservableList.addAll(contactObservableList);
    }

    public void setCloseCallback(Runnable closeCallback) {
        this.closeCallback = closeCallback;
    }

    private void setContactResult(ContactManager contactManager) {
        this.contactManager = contactManager;
    }

    public void addContact(Contact contact) {
        contactObservableList.add(contact);
    }
}
