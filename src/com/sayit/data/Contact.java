package com.sayit.data;

import com.sayit.data.image.ImageBuilder;
import com.sayit.message.Rebuildable;
import com.sayit.message.Request;
import com.sayit.network.MessageProtocol;
import javafx.scene.image.Image;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Contact implements Rebuildable<Contact> {

    private Image photo;
    private String ipAddress;
    private long id;
    private String name;

    public Contact() {
    }

    public Contact(String name, Image photo, String ipAddress) {
        this.name = name;
        this.photo = photo;
        this.ipAddress = ipAddress;
        id = ContactDao.parseAddress(ipAddress);

    }

    public void setId(long id) {
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

    public long getId() {
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


    @Override
    public String toString() {

        return name + "#" + photo + "#" + "#" + ipAddress;
    }

    @Override
    public Contact fromRequest(Request request) {
        return this;
    }

    @Override
    public Request toRequest() {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteStream);

        try {
            dataOutputStream.writeUTF(getName());
            dataOutputStream.write(ImageBuilder.readImageBytes(getPhoto()));
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Request(
                byteStream.toByteArray(),
                getIpAddress()
        );
    }
}