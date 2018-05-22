package com.sayit.data;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ContactDao {

    private Contact userProfile;
    private List<Contact> contactList;
    private Map<Integer, MessageHistory> messageMap;

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
        //TODO Djan addContact
    }

    /**
     * Adiciona a mensagem no hostórico de mensagens.
     *
     * @param id      Identificador do contato
     * @param message Mensagen
     */
    public void addMessage(int id, Message message) {
        //TODO Djan addMessage
    }

    /**
     * Busca um contato pelo ID.
     *
     * @param id Identificador do contato
     * @return
     */
    public Contact getContact(int id) {
        //TODO Djan getContact
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
        return null;
    }

    /**
     * Retorna a lista de históricos de mensagem.
     *
     * @return
     */
    public List<MessageHistory> getHistoryList() {
        //TODO Djan getHistoryList
        return null;
    }

    /**
     * Edita o nome de um determinado contato.
     *
     * @param id      Identificador do contato
     * @param newName Novo nome
     */
    public void editContact(int id, String newName) {
        //TODO Djan editContact
    }

    /**
     * Edita o perfil do usuário.
     *
     * @param name  Novo nome
     * @param image Nova imagem
     */
    public void editProfile(String name, Image image) {
        //TODO Djan editProfile
    }

    public Contact getUserProfile() {
        return userProfile;
    }

    public List<Contact> getContactList() {
        return contactList;
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
