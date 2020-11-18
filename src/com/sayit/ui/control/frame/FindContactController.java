package com.sayit.ui.control.frame;

import com.sayit.control.ProtocolManager;
import com.sayit.data.contact.Contact;
import com.sayit.data.contact.ContactManager;
import com.sayit.di.Autowired;
import com.sayit.ui.control.view.ContactCell;
import com.sayit.ui.navigator.Navigator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FindContactController {

    @FXML
    private TextField nameField;
    @FXML
    private ListView<Contact> contactListView;
    private ObservableList<Contact> contactObservableList;

    @Autowired
    private ProtocolManager requestMediator;

    @Autowired
    private ContactManager contactDao;

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

        requestSearchFocus();

    }

    public void close() {
        requestMediator.stopDiscover();
        contactDao.endFindContacts();
        Navigator.of(stage).popResult(null);
    }

    public void search() {
        contactDao.startFindContacts();
        requestMediator.multicastContactNameDiscovery(nameField.getText());
        contactDao.addFindContactChangedListener(c -> contactObservableList.add(c));
    }

    public void addContact(Contact contact) {
        contactObservableList.add(contact);
    }

    public void requestSearchFocus() {
        nameField.requestFocus();
    }

}
