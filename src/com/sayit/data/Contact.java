package com.sayit.data;

import javafx.scene.image.Image;

public class Contact {

    private Image photo;
    private String ipAddress;
    private int id;
    private String name;

    /* incluido para testes, já que no contrutor paddrão tenho que ter uma imagem*/
    public Contact() {
    }

    public Contact(String name, Image photo, String ipAddress) {
        this.name = name;
        this.photo = photo;
        this.ipAddress = ipAddress;

    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoto(Image photo) {
        this.photo = photo;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Image getPhoto() {
        return photo;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * Retorna a representação string desse contato. Formatada para
     * armazenamento.
     *
     * @return
     */
    @Override
    public String toString() {

        return name + "#" + photo + "#" + "#" + ipAddress;
    }
}