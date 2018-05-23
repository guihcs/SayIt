package com.sayit.data;

import javafx.scene.image.Image;

public class Contact {

    private int id;
    private String name;
    private Image photo;
    private String path;
    private String ipAddress;

    public Contact(String name, Image photo, String ipAddress) {
        this.name = name;
        this.photo = photo;
        this.ipAddress = ipAddress;
        //TODO Djan idConstructor
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

    public String getPath() {
        return path;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * Retorna a representação string desse contato. Formatada para armazenamento.
     *
     * @return
     */
    @Override
    public String toString() {
        //TODO Djan toString
        return "Contact{}";
    }
}
