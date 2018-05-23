package com.sayit.data;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class ContactDao {

    private Contact userProfile;
    private List<Contact> contactList;
    private Map<Integer, MessageHistory> messageMap;
    private List<Message> messageList;


    public ContactDao() {
        contactList = new ArrayList<>();
        messageMap = new LinkedHashMap<>();
    }

    /**
     * Adciona o contato na lista de contatos.
     *
     * @param contact
     */
    public void addContact(Contact contact) {
        contactList.add(contact);
    }

    /**
     * Adiciona a mensagem no hostórico de mensagens.
     *
     * @param id Identificador do contato
     * @param message Mensagen
     */
    public void addMessage(int id, Message message) {
        //TODO Djan addMessage
        for(int i = 0; i < contactList.size(); i++){
            if(id == contactList.get(i).getId()){
                messageList.add(message);
            }
        }
    }

    /**
     * Busca um contato pelo ID.
     *
     * @param id Identificador do contato
     * @return
     */
    public Contact getContact(int id) {

        for (int i = 0; i < contactList.size(); i++) {
            if (id == contactList.get(i).getId());
            {
                return contactList.get(i);
            }

        }
        System.out.println("Contato inexistente!");
        return null;
    }

    /**
     * Retorna a lista de mensagens do contato.
     *
     * @param id Id do contato.
     * @return
     */
    public List<Message> getMessageList(int id) {
        //TODO Djan getMessageList
        for(int i = 0; i < contactList.size();i++){
            if(id == contactList.get(i).getId()){
                return messageList;
            }
        }
        System.out.println("Não há mensagens no seu historico de mensagens");
        return null;
    }

    /**
     * Retorna a lista de históricos de mensagem.
     *
     * @return
     */
    public List<MessageHistory> getHistoryList() {
        //TODO Djan getHistoryList
        return new ArrayList<>();
    }

    /**
     * Edita o nome de um determinado contato.
     *
     * @param id Identificador do contato
     * @param newName Novo nome
     */
    public void editContact(int id, String newName) {

        for (int i = 0; i < contactList.size(); i++) {
            if (id == contactList.get(i).getId());
            {
                contactList.get(i).setName(newName);
            }
        }
    }

    /**
     * Edita o perfil do usuário.
     *
     * @param name Novo nome
     * @param image Nova imagem
     */
    public void editProfile(String name, Image image){

        userProfile.setName(name);
        userProfile.setPhoto(image);
    }

    public Contact getUserProfile() {
        return userProfile;
    }

    public List<Contact> getContactList() {
        return contactList;
    }

    public void setUserProfile(Contact userProfile) {
        this.userProfile = userProfile;
    }


    public List<String> getMessagesData(int id) {
        return null;
    }

    public List<String> getContactsData() {
        return null;
    }

    public List<String> getHistoryData() {
        return null;
    }

    public void loadContactList() {

    }

    public void loadHistoryList() {

    }
}
