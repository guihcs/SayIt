package com.sayit.data;

import javafx.scene.image.Image;

import java.net.InetAddress;
import java.nio.file.Path;

public class Contact {

    private int id;
    private String name;
    private Image photo;
    private Path path;
    private InetAddress ipAddress;

    public Contact(String name, Image photo, Path path, InetAddress ipAddress) {
        this.name = name;
        this.photo = photo;
        this.path = path;
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

    public Path getPath() {
        return path;
    }

    public InetAddress getIpAddress() {
        return ipAddress;
    }

    @Override
    public String toString() {
        return "Contact{}";
    }
}
