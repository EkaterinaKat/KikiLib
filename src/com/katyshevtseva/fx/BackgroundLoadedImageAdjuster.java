package com.katyshevtseva.fx;

import com.katyshevtseva.general.NoArgsKnob;
import javafx.scene.image.Image;

public class BackgroundLoadedImageAdjuster extends Thread {
    private final NoArgsKnob adjustImageKnob;
    private final Image image;

    public BackgroundLoadedImageAdjuster(Image image, NoArgsKnob adjustImageKnob) {
        this.adjustImageKnob = adjustImageKnob;
        this.image = image;
    }

    @Override
    public void run() {
        while (image.getProgress() < 1) {
            try {
                sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        adjustImageKnob.execute();
    }
}
