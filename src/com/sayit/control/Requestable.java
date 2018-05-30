package com.sayit.control;

public interface Requestable {

    void sendMessage(String address, byte[] content, String fileName);

    void sendMessage(String address, String message);

    void sendContactResult(String receiverIp, String name, byte[] image);

    /**
     * Requisita dados do contato em todos os usuários do grupo.
     *
     * @param name
     */
    void requestContact(String name);

    /**
     * Envia solicitação de adição para outro contato.
     *
     * @param ip
     * @param userName
     * @param imageBytes
     */
    void contactAdd(String ip, String userName, byte[] imageBytes);

    void loadMessageList(int id);

    void stopServices();
}
