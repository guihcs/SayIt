package com.sayit.data.image;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import java.nio.ByteBuffer;

public class ImageBuilder {


    public static Image buildImage(byte[] imgBytes, int width, int height){
        WritableImage writableImage = new WritableImage(width, height);
        ByteBuffer pixelBuffer = ByteBuffer.wrap(imgBytes);
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                pixelWriter.setArgb(w, h, pixelBuffer.getInt());
            }
        }

        return writableImage;
    }


    public static byte[] readImageBytes(Image image) {
        ByteBuffer pixelBuffer = ByteBuffer.allocate((int) (image.getWidth() * image.getHeight()) * 4);
        PixelReader pixelReader = image.getPixelReader();

        for (int h = 0; h < image.getHeight(); h++) {
            for (int w = 0; w < image.getWidth(); w++) {
                pixelBuffer.putInt(pixelReader.getArgb(w, h));
            }
        }

        return pixelBuffer.array();
    }
}
