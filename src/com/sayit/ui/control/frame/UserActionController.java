package com.sayit.ui.control.frame;

import com.sayit.data.Contact;
import com.sayit.ui.navigator.Navigator;
import javafx.animation.Interpolator;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.List;

public class UserActionController {

//
//    private void loadContactView(List<Contact> contactList) {
//
//        Node node = Navigator.buildNamed(
//                "/addContact",
//                contactList,
//                o -> {
//                    if (o != null) {
//                        setReceiverProfile((Contact) o);
//                    }
//                    closeSearchContact();
//                }
//        );
//
//        findRoot = (Pane) node;
//        findPane.getChildren().add(node);
//        findPane.heightProperty().addListener(e -> findRoot.setPrefHeight(findPane.getHeight()));
//        configSlideAnimation();
//    }
//
//    public void showSearchContact() {
//
//        loadContactView(contactDao.getContactList());
//
//        findPane.setManaged(true);
//        findPane.setVisible(true);
//        findRoot.setPrefWidth(findPane.getWidth());
//
//        translateTransition.setFromX(findPane.getWidth());
//        translateTransition.setToX(0);
//        translateTransition.setOnFinished(null);
//        translateTransition.playFromStart();
//    }
//
//
//    public void showAddContact() {
//
//        Navigator.of(stage)
//                .pushNamedModal("/addContact",
//                        400,
//                        300,
//                        res -> {
//                            if (res != null){
//                                mediator.sendContactAddRequest(((Contact)res).toRequest());
//                            }
//                        });
//    }
//
//    public void showEditProfile() {
//        Navigator.of(stage).pushNamedModal("/editProfile",
//                300,
//                400,
//                contactDao.getUserProfile(),
//                c -> contactDao.setUserProfile((Contact)c));
//    }
//
//    private void closeSearchContact() {
//        translateTransition.setFromX(0);
//        translateTransition.setToX(findPane.getWidth());
//        translateTransition.setInterpolator(Interpolator.EASE_IN);
//        translateTransition.setOnFinished(e -> {
//            findPane.setManaged(false);
//            findPane.setVisible(false);
//        });
//        translateTransition.playFromStart();
//    }
}
